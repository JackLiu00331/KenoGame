package Controller;

import Component.ControlButton;
import Component.NumberButton;
import Component.PrizeItemBox;
import Model.GameHistory;
import Model.GameMode;
import Model.PrizeTable;
import Utils.ThemeStyles;
import View.InfoWindow;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AnimationController {
    private PopOver modePopOver;
    private PopOver drawingsPopOver;
    private Integer currentMatchCount = 0;
    private Integer currentRounds = 0;
    private Integer totalRounds = 0;
    private Integer totalMatchCount = 0;
    private Integer totalWinnings = 0;
    private List<PrizeItemBox> prizeItemBoxes;
    private AudioController audioController;
    private GameController gameController;
    private VBox prizeMatchPanel;
    private HBox controlArea;
    private MenuButton modeSelector;
    private MenuButton drawingsSelector;
    private Label statusLabel;
    private ImageView slotIconView;
    private ImageView slotIconView2;
    private NumberButton[] numberButtons;
    private boolean cheatMode;

    public AnimationController(AudioController audioController, GameController gameController,
                               VBox prizeMatchPanel, HBox controlArea,
                               MenuButton modeSelector, MenuButton drawingsSelector,
                               Label statusLabel, ImageView slotIconView,
                               ImageView slotIconView2, NumberButton[] numberButtons,
                               boolean cheatMode) {
        this.cheatMode = cheatMode;
        this.audioController = audioController;
        this.gameController = gameController;
        this.prizeMatchPanel = prizeMatchPanel;
        this.controlArea = controlArea;
        this.modeSelector = modeSelector;
        this.drawingsSelector = drawingsSelector;
        this.statusLabel = statusLabel;
        this.slotIconView = slotIconView;
        this.slotIconView2 = slotIconView2;
        this.numberButtons =  numberButtons;
        prizeItemBoxes = new ArrayList<>();
    }

    public void popUpGameRules() {
        PauseTransition slowPause = new PauseTransition(Duration.seconds(0.4));
        slowPause.setOnFinished(e -> {
            InfoWindow.showRules(true);
        });
        slowPause.play();
    }

    public void showNotification(MenuButton selector) {
        PauseTransition delay = new PauseTransition(Duration.seconds(0.6));
        if(selector.getText().equals("Select Mode") ){
            delay.setOnFinished(e -> showModeSelectorPopOver(selector));
        } else if (selector.getText().equals("Select Draws")) {
            delay.setOnFinished(e -> showDrawingsSelectorPopOver(selector));
        }
        delay.play();
    }

    private void showDrawingsSelectorPopOver(MenuButton drawingsSelector) {
        hideDrawingsNotification();
        Label tip = new Label("Select drawings here !!!");
        tip.setStyle("-fx-padding: 10; -fx-font-size: 16px;");
        drawingsPopOver = new PopOver(tip);
        drawingsPopOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        drawingsPopOver.setDetachable(false);
        drawingsPopOver.setAutoHide(false);
        drawingsPopOver.show(drawingsSelector);
    }

    private void showModeSelectorPopOver(MenuButton modeSelector) {
        if (modePopOver != null && modePopOver.isShowing()) {
            modePopOver.hide();
        }
        Label tip = new Label("Select game mode here !!!");
        tip.setStyle("-fx-padding: 10; -fx-font-size: 16px;");
        modePopOver = new PopOver(tip);
        modePopOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        modePopOver.setDetachable(false);
        modePopOver.setAutoHide(false);
        modePopOver.show(modeSelector);
    }

    public void hideNotification() {
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
        totalRounds = gameController.getMaxDrawings();
        totalWinnings = 0;
        currentRounds++;
        audioController.playSound(AudioController.START_SOUND);
        resetForNewRound();
        resetPrizeHighlights();
        controlArea.setDisable(true);
        setAllNumberSelectable(false);
        modeSelector.setDisable(true);
        drawingsSelector.setDisable(true);
        slotIconView.setVisible(true);
        slotIconView2.setVisible(true);
        updatePrizeHighlights();
        List<Integer> systemSelections = gameController.randomSelectNumbersForSystem(cheatMode);
        if (cheatMode) {
            statusLabel.setText("Cheat mode active! Jackpot incoming...");
            statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_SURPRISE);
        } else {
            statusLabel.setText("System is selecting numbers...");
            statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
        }

        Collections.sort(systemSelections);

        for (int num : systemSelections) {
            PauseTransition pause = new PauseTransition(Duration.seconds(0.2 * systemSelections.indexOf(num)));
            pause.setOnFinished(e -> {
                numberButtons[num - 1].systemSelect();
                if (gameController.getSelectedNumbers().contains(num)) {
                    currentMatchCount++;
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
            slotIconView.setVisible(false);
            slotIconView2.setVisible(false);
            int matchedCount = currentMatchCount;
            int roundPrize = PrizeTable.getPrizeForHits(gameController.getMaxSelections(), matchedCount);
            totalWinnings += roundPrize;
            if(PrizeTable.isValidPrize(gameController.getMaxSelections(),currentMatchCount)) {
                audioController.playSound(AudioController.JACKPOT_SOUND);
                statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_SURPRISE);
            }else {
                audioController.playSound(AudioController.FINISH_SOUND);
                statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
            }
            statusLabel.setText("Round over! You matched " + currentMatchCount + " number(s).");

            List<Integer> matchedNumbers = new ArrayList<>();
            for (int num : systemSelections) {
                if (gameController.getSelectedNumbers().contains(num)) {
                    matchedNumbers.add(num);
                }
            }

            totalMatchCount += currentMatchCount;
            currentMatchCount = 0;
            PauseTransition showResultDelay = new PauseTransition(Duration.seconds(1));
            showResultDelay.setOnFinished(e -> {
                Platform.runLater( () -> {
                    InfoWindow.showResult(currentRounds, totalRounds, matchedCount, roundPrize, matchedNumbers,this::startNextRound, this::onAllRoundsCompleted);
                });
            });
            showResultDelay.play();
        });
        return processPause;
    }

    private void resetGameState() {
        gameController.addGameHistory(new GameHistory(totalRounds, totalMatchCount,totalWinnings));
        currentRounds = 0;
        totalWinnings = 0;
        totalMatchCount = 0;
        cheatMode = false;
        controlArea.setDisable(false);
        drawingsSelector.setDisable(false);
        setAllNumberSelectable(true);
        modeSelector.setDisable(false);
        resetForNewRound();
        resetPrizeHighlights();
    }

    private void onAllRoundsCompleted() {
        resetGameState();
        statusLabel.setText("All rounds complete! Total prize: $" + totalWinnings);
        statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_SURPRISE);
    }

    private void setAllNumberSelectable(boolean selectable) {
        for (NumberButton btn : numberButtons) {
            btn.setSelectable(selectable);
        }
    }

    public void updatePrizeHighlights() {
        for (PrizeItemBox item : prizeItemBoxes) {
            if (item.getRequiredMatches() < currentMatchCount) {
                item.reset();
                continue;
            }
            if (item.getRequiredMatches() == currentMatchCount) {
                item.highlightPrize();
                audioController.playSound(AudioController.MATCH_SOUND);
                break;
            }
        }
    }

    public void updatePrizeMatchPanel(GameMode mode) {
        prizeMatchPanel.getChildren().clear();
        prizeItemBoxes.clear();
        Map<Integer, Integer> prizes = PrizeTable.getPrizeTableForSpots(mode.getMaxSpots());
        prizes.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(entry -> {
            PrizeItemBox item = new PrizeItemBox(entry.getKey(), entry.getValue());
            prizeItemBoxes.add(item);
        });

        List<PrizeItemBox> temp = new ArrayList<>(prizeItemBoxes);
        Collections.reverse(temp);
        prizeMatchPanel.getChildren().setAll(temp);
    }

    public void resetNumberButtons() {
        for (NumberButton btn : numberButtons) {
            btn.reset();
        }
    }
    public void resetForNewRound() {
        for (NumberButton btn : numberButtons) {
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
