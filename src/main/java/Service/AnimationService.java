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
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AnimationService {
    private List<PrizeItemBox> prizeItemBoxes;
    private GameView gameView;
    private GameService gameService;
    private GameState gameState;

    private PopOver modePopOver;
    private PopOver drawingsPopOver;
    private AudioService audioService;

    public AnimationService(AudioService audioService, GameService gameService, GameView gameView, GameState gameState) {
        this.audioService = audioService;
        this.gameService = gameService;
        this.gameView = gameView;
        this.gameState = gameState;
        prizeItemBoxes = new ArrayList<>();
    }

    public void showGameRules() {
        PauseTransition slowPause = new PauseTransition(Duration.seconds(0.4));
        slowPause.setOnFinished(e -> {
            InfoWindow.showRules(true, gameView.getRoot());
        });
        slowPause.play();
    }

    public void showModeNotification() {
        PauseTransition delay = new PauseTransition(Duration.seconds(0.6));
        delay.setOnFinished(e -> showPopOver(gameView.getModeSelector(), "Select mode here!!!", true));
        delay.play();
    }

    public void showDrawingsNotification() {
        PauseTransition delay = new PauseTransition(Duration.seconds(0.6));
        delay.setOnFinished(e -> showPopOver(gameView.getDrawingsSelector(), "Select drawings here!!!", false));
        delay.play();
    }

    private void showPopOver(MenuButton targetButton, String message, boolean isMode) {
        if(isMode) {
            hideModeNotification();
        } else {
            hideDrawingsNotification();
        }

        Label tip = new Label(message);
        tip.setStyle("-fx-padding: 10; -fx-font-size: 16px;");

        PopOver popOver = new PopOver(tip);
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        popOver.setDetachable(false);
        popOver.setAutoHide(false);
        popOver.show(targetButton);

        if(isMode) {
            modePopOver = popOver;
        } else {
            drawingsPopOver = popOver;
        }
    }


    public void hideModeNotification() {
        if (modePopOver != null && modePopOver.isShowing()) {
            modePopOver.hide();
        }
    }

    public void hideDrawingsNotification() {
        if (drawingsPopOver != null && drawingsPopOver.isShowing()) {
            drawingsPopOver.hide();
        }
    }

    public void startNextRound() {
        gameState.incrementRound();
        gameState.resetTotalMatchCount();

        audioService.playSound(AudioService.START_SOUND);
        resetForNewRound();
        resetPrizeHighlights();
        gameView.getControlArea().setDisable(true);
        setAllNumberSelectable(false);
        gameView.getModeSelector().setDisable(true);
        gameView.getDrawingsSelector().setDisable(true);
        gameView.setSlotIconsVisible(true);
        updatePrizeHighlights();

        List<Integer> systemSelections = gameService.randomSelectNumbersForSystem();
        Collections.sort(systemSelections);

        if (gameState.isCheatMode()) {
            gameView.updateStatusLabel("Cheat mode active! Jackpot incoming...", ThemeStyles.INFO_LABEL_STATUS_SURPRISE);
        } else {
            gameView.updateStatusLabel("System is selecting numbers...", ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
        }


        animateSystemSelections(systemSelections);
    }

    private void animateSystemSelections(List<Integer> systemSelections) {
        for (int num : systemSelections) {
            PauseTransition pause = new PauseTransition(Duration.seconds(0.2 * systemSelections.indexOf(num)));
            pause.setOnFinished(e -> {
                gameView.getNumberButtons()[num - 1].systemSelect();
                if (gameState.getSelectedNumbers().contains(num)) {
                    gameState.incrementCurrentMatchCount();
                    updatePrizeHighlights();
                }
            });
            pause.play();
        }

        PauseTransition processPause = getProcessingTransition(systemSelections);
        processPause.play();
    }

    private PauseTransition getProcessingTransition(List<Integer> systemSelections) {
        PauseTransition processPause = new PauseTransition(Duration.seconds(0.2 * systemSelections.size() + 0.3));
        processPause.setOnFinished(ev -> {
            gameView.setSlotIconsVisible(false);
            int matchedCount = gameState.getCurrentMatchCount();
            int roundPrize = gameService.calculatePrize(matchedCount);
            gameState.resetCurrentMatchCount();
            List<Integer> matchedNumbers = gameService.getMatchedNumbers(systemSelections);
            gameState.addToTotalWinnings(roundPrize);
            gameState.addToTotalMatchCount(matchedCount);

            if(gameService.isJackPot(matchedCount)) {
                audioService.playSound(AudioService.JACKPOT_SOUND);
                gameView.updateStatusLabel("Round over! You matched " + matchedCount + " number(s).", ThemeStyles.INFO_LABEL_STATUS_SURPRISE);
            }else {
                audioService.playSound(AudioService.FINISH_SOUND);
                gameView.updateStatusLabel("Round over! You matched " + matchedCount + " number(s).", ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
            }


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

    private void resetGameState() {
        gameState.addGameHistory(new GameHistory(
                gameService.getMaxDrawings(),
                gameState.getTotalMatchCount(),
                gameState.getTotalWinnings()
        ));
        gameService.resetForNewGame();
        gameState.setCheatMode(false);
        gameView.getControlArea().setDisable(false);
        gameView.getDrawingsSelector().setDisable(false);
        setAllNumberSelectable(true);
        gameView.getModeSelector().setDisable(false);
        resetForNewRound();
        resetPrizeHighlights();
    }

    public void resetGame() {
        gameState.setGameDrawings(null);
        gameState.setGameMode(null);
        gameService.resetForNewGame();

        gameView.getModeSelector().setText("Select Mode");
        gameView.getDrawingsSelector().setText("Select Drawings");
        if (gameState.getGameMode() == null && gameState.getGameDrawings() == null) {
            gameView.disableAllButtons();
        }
        resetNumberButtons();
        resetPrizeHighlights();

        gameView.updateStatusLabel(
                "Game reset. Ready for a new game!",
                ThemeStyles.INFO_LABEL_STATUS_POSITIVE
        );
    }

    private void onAllRoundsCompleted() {
        resetGameState();
        gameView.updateStatusLabel(
                "All rounds complete! Total prize: $" + gameState.getTotalWinnings(),
                ThemeStyles.INFO_LABEL_STATUS_SURPRISE
        );
    }

    private void setAllNumberSelectable(boolean selectable) {
        for (NumberButton btn : gameView.getNumberButtons()) {
            btn.setSelectable(selectable);
        }
    }

    public void updatePrizeHighlights() {
        Integer currentMatchCount = gameState.getCurrentMatchCount();
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

    public void updatePrizeMatchPanel(GameMode mode) {
        gameView.getPrizeMatchPanel().getChildren().clear();
        prizeItemBoxes.clear();
        Map<Integer, Integer> prizes = PrizeTable.getPrizeTableForSpots(mode.getMaxSpots());
        prizes.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            PrizeItemBox item = new PrizeItemBox(entry.getKey(), entry.getValue());
            prizeItemBoxes.add(item);
        });

        List<PrizeItemBox> temp = new ArrayList<>(prizeItemBoxes);
        Collections.reverse(temp);
        gameView.getPrizeMatchPanel().getChildren().setAll(temp);
    }

    public void resetNumberButtons() {
        for (NumberButton btn : gameView.getNumberButtons()) {
            btn.reset();
        }
    }
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

    public void resetPrizeHighlights() {
        for (PrizeItemBox item : prizeItemBoxes) {
            item.reset();
        }
    }
}
