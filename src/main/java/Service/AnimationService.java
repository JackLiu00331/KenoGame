package Service;

import Model.GameState;
import View.Component.NumberButton;
import View.Component.PrizeItemBox;
import Model.GameHistory;
import Model.GameMode;
import Model.PrizeTable;
import Utils.ThemeStyles;
import View.GameView;
import View.InfoWindow;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * AnimationService handles animations and transitions in the game.
 */
public class AnimationService {
    // Fields
    private GameView gameView;
    private GameService gameService;
    private GameState gameState;
    private List<PrizeItemBox> prizeItemBoxes;
    private PopOver modePopOver;
    private PopOver drawingsPopOver;
    private AudioService audioService;

    // Constructor
    public AnimationService(AudioService audioService, GameService gameService, GameView gameView, GameState gameState) {
        this.audioService = audioService;
        this.gameService = gameService;
        this.gameView = gameView;
        this.gameState = gameState;
        prizeItemBoxes = new ArrayList<>();
    }

    /**
     * Shows the game rules with a slight delay.
     */
    public void showGameRules() {
        // An animated delay before showing the rules
        PauseTransition slowPause = new PauseTransition(Duration.seconds(0.4));
        slowPause.setOnFinished(e -> {
            InfoWindow.showRules(true, gameView.getRoot());
        });
        slowPause.play();
    }

    /**
     * Shows a notification pop-over for mode selection.
     */
    public void showModeNotification() {
        // An animated delay before showing the pop-over
        PauseTransition delay = new PauseTransition(Duration.seconds(0.6));
        delay.setOnFinished(e -> showPopOver(gameView.getModeSelector(), "Select mode here!!!", true));
        delay.play();
    }

    /**
     * Shows a notification pop-over for drawings selection.
     */
    public void showDrawingsNotification() {
        // An animated delay before showing the pop-over
        PauseTransition delay = new PauseTransition(Duration.seconds(0.6));
        delay.setOnFinished(e -> showPopOver(gameView.getDrawingsSelector(), "Select drawings here!!!", false));
        delay.play();
    }

    /**
     * Shows a pop-over notification.
     * @param targetButton - the button to attach the pop-over to
     * @param message - the message to display
     * @param isMode - true if for mode selection, false for drawings selection
     */
    private void showPopOver(MenuButton targetButton, String message, boolean isMode) {
        // Hide existing pop-over if any
        if(isMode) {
            hideModeNotification();
        } else {
            hideDrawingsNotification();
        }

        // Create and show new pop-over
        Label tip = new Label(message);
        tip.setStyle("-fx-padding: 10; -fx-font-size: 16px;");

        PopOver popOver = new PopOver(tip);
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        popOver.setDetachable(false);
        popOver.setAutoHide(false);
        popOver.show(targetButton);

        // Store reference to the pop-over
        if(isMode) {
            modePopOver = popOver;
        } else {
            drawingsPopOver = popOver;
        }
    }

    /**
     * Hides the mode selection pop-over if it is showing.
     */
    public void hideModeNotification() {
        if (modePopOver != null && modePopOver.isShowing()) {
            modePopOver.hide();
        }
    }

    /**
     * Hides the drawings selection pop-over if it is showing.
     */
    public void hideDrawingsNotification() {
        if (drawingsPopOver != null && drawingsPopOver.isShowing()) {
            drawingsPopOver.hide();
        }
    }

    /**
     * Starts the next round of the game with animations.
     */
    public void startNextRound() {
        // Increment round and reset match count for new round
        gameState.incrementRound();
        gameState.resetTotalMatchCount();

        // Play start sound and prepare UI for new round
        audioService.playSound(AudioService.START_SOUND);
        resetForNewRound();
        resetPrizeHighlights();
        gameView.getControlArea().setDisable(true);
        setAllNumberSelectable(false);
        gameView.getModeSelector().setDisable(true);
        gameView.getDrawingsSelector().setDisable(true);
        gameView.setSlotIconsVisible(true);
        updatePrizeHighlights();

        // Animate system selections
        List<Integer> systemSelections = gameService.randomSelectNumbersForSystem();
        Collections.sort(systemSelections);

        if (gameState.isCheatMode()) {
            gameView.updateStatusLabel("Cheat mode active! Jackpot incoming...", ThemeStyles.INFO_LABEL_STATUS_SURPRISE);
        } else {
            gameView.updateStatusLabel("System is selecting numbers...", ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
        }

        animateSystemSelections(systemSelections);
    }

    /**
     * Animates the system's number selections.
     * @param systemSelections - list of numbers selected by the system
     */
    private void animateSystemSelections(List<Integer> systemSelections) {
        // Animate each system selection with a delay
        for (int num : systemSelections) {
            PauseTransition pause = new PauseTransition(Duration.seconds(0.2 * systemSelections.indexOf(num)));
            pause.setOnFinished(e -> {
                // Highlight the selected number button
                gameView.getNumberButtons()[num - 1].systemSelect();
                // Check for matches and update highlights
                if (gameState.getSelectedNumbers().contains(num)) {
                    gameState.incrementCurrentMatchCount();
                    updatePrizeHighlights();
                }
            });
            pause.play();
        }

        // After all selections, process the results
        PauseTransition processPause = getProcessingTransition(systemSelections);
        processPause.play();
    }

    /**
     * Creates a transition to process the results after system selections.
     * @param systemSelections - list of numbers selected by the system
     * @return PauseTransition for processing results
     */
    private PauseTransition getProcessingTransition(List<Integer> systemSelections) {
        // Delay based on number of selections
        PauseTransition processPause = new PauseTransition(Duration.seconds(0.2 * systemSelections.size() + 0.3));
        processPause.setOnFinished(ev -> {
            // Process results and show outcome
            gameView.setSlotIconsVisible(false);
            int matchedCount = gameState.getCurrentMatchCount();
            int roundPrize = gameService.calculatePrize(matchedCount);
            gameState.resetCurrentMatchCount();

            // Get matched numbers for history and result later
            List<Integer> matchedNumbers = gameService.getMatchedNumbers(systemSelections);
            gameState.addToTotalWinnings(roundPrize);
            gameState.addToTotalMatchCount(matchedCount);

            // Play appropriate sound and update status label
            if(gameService.isWin(matchedCount)) {
                audioService.playSound(AudioService.JACKPOT_SOUND);
                gameView.updateStatusLabel("Round over! You matched " + matchedCount + " number(s).", ThemeStyles.INFO_LABEL_STATUS_SURPRISE);
            }else {
                audioService.playSound(AudioService.FINISH_SOUND);
                gameView.updateStatusLabel("Round over! You matched " + matchedCount + " number(s).", ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
            }


            // Show result after a short delay
            PauseTransition showResultDelay = new PauseTransition(Duration.seconds(1));
            showResultDelay.setOnFinished(e -> {
                Platform.runLater( () -> {
                    InfoWindow.showResult(gameState.getCurrentRound(), gameService.getMaxDrawings(), matchedCount, roundPrize, matchedNumbers,this::startNextRound, this::onAllRoundsCompleted, gameView.getRoot());
                });
            });
            showResultDelay.play();
        });
        return processPause;
    }

    /**
     * Resets the game state for a new game.
     */
    private void resetGameState() {
        // Add current game to history
        gameState.addGameHistory(new GameHistory(
                gameService.getMaxDrawings(),
                gameState.getTotalMatchCount(),
                gameState.getTotalWinnings()
        ));
        // Reset game state and UI components
        gameService.resetForNewGame();
        gameState.setCheatMode(false);
        gameView.getControlArea().setDisable(false);
        gameView.getDrawingsSelector().setDisable(false);
        setAllNumberSelectable(true);
        gameView.getModeSelector().setDisable(false);
        resetForNewRound();
        resetPrizeHighlights();
    }

    /**
     * Resets the entire game for a new session.
     */
    public void resetGame() {
        // Reset game state and UI components
        gameState.setGameDrawings(null);
        gameState.setGameMode(null);
        gameService.resetForNewGame();

        // Reset UI elements
        gameView.getModeSelector().setText("Select Mode");
        gameView.getDrawingsSelector().setText("Select Draws");
        if (gameState.getGameMode() == null && gameState.getGameDrawings() == null) {
            gameView.disableAllButtons();
        }

        // Reset buttons and highlights
        resetNumberButtons();
        resetPrizeHighlights();

        // Update status label
        gameView.updateStatusLabel(
                "Game reset. Ready for a new game!",
                ThemeStyles.INFO_LABEL_STATUS_POSITIVE
        );
    }

    /**
     * Handles the completion of all rounds in the game.
     */
    private void onAllRoundsCompleted() {
        // Reset game state and show final status
        resetGameState();
        gameView.updateStatusLabel(
                "All rounds complete! Total prize: $" + gameState.getTotalWinnings(),
                ThemeStyles.INFO_LABEL_STATUS_SURPRISE
        );
    }

    /**
     * Sets all number buttons to be selectable or not.
     * @param selectable - true to make selectable, false otherwise
     */
    private void setAllNumberSelectable(boolean selectable) {
        for (NumberButton btn : gameView.getNumberButtons()) {
            btn.setSelectable(selectable);
        }
    }

    /**
     * Updates the prize highlights based on current match count.
     */
    public void updatePrizeHighlights() {
        Integer currentMatchCount = gameState.getCurrentMatchCount();
        // Update prize highlights based on current match count
        for (PrizeItemBox item : prizeItemBoxes) {
            if (item.getRequiredMatches() < currentMatchCount) {
                item.reset();
                continue;
            }
            if (item.getRequiredMatches() == currentMatchCount) {
                item.highlightPrize();
                audioService.playSound(AudioService.MATCH_SOUND);
                break;
            }
        }
    }

    /**
     * Updates the prize match panel based on the selected game mode.
     * @param mode - the selected game mode
     */
    public void updatePrizeMatchPanel(GameMode mode) {
        // Clear existing prizes and populate based on mode
        gameView.getPrizeMatchPanel().getChildren().clear();
        prizeItemBoxes.clear();

        // Get prize table for the selected mode
        Map<Integer, Integer> prizes = PrizeTable.getPrizeTableForSpots(mode.getMaxSpots());
        prizes.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            PrizeItemBox item = new PrizeItemBox(entry.getKey(), entry.getValue());
            prizeItemBoxes.add(item);
        });

        // Reverse the list to show highest matches first
        List<PrizeItemBox> temp = new ArrayList<>(prizeItemBoxes);
        Collections.reverse(temp);
        gameView.getPrizeMatchPanel().getChildren().setAll(temp);
    }

    /**
     * Resets all number buttons to their default state.
     */
    public void resetNumberButtons() {
        for (NumberButton btn : gameView.getNumberButtons()) {
            btn.reset();
        }
    }

    /**
     * Resets the game state for a new round.
     */
    public void resetForNewRound() {
        for (NumberButton btn : gameView.getNumberButtons()) {
            if (btn.isSelected()) {
                continue;
            } else if (btn.bothSelected()) {
                btn.setState(NumberButton.ButtonState.USER_SELECTED);
            } else {
                btn.reset();
            }
        }
    }

    /**
     * Resets all prize highlights to their default state.
     */
    public void resetPrizeHighlights() {
        for (PrizeItemBox item : prizeItemBoxes) {
            item.reset();
        }
    }
}
