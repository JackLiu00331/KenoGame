package View;

import Component.ControlButton;
import Component.MenuFactory;
import Controller.AudioController;
import Model.GameDrawings;
import Model.GameHistory;
import Model.PrizeTable;
import javafx.application.Platform;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;
import Component.NumberButton;
import Controller.GameController;
import Model.GameMode;
import Component.PrizeItemBox;
import Utils.ButtonStyles;
import Utils.ThemeStyles;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GameStage {

    private GameController gameController;
    private NumberButton[] numberButtons = new NumberButton[80];
    private boolean allDisabled = false;
    private List<PrizeItemBox> prizeItemBoxes;
    private Integer currentMatchCount = 0;
    private Integer currentRounds = 0;
    private Integer totalRounds = 0;
    private Integer totalMatchCount = 0;
    private Integer totalWinnings = 0;
    private List<GameHistory> gameHistories;
    private boolean cheatMode = false;
    private AudioController audioController;
    private WelcomeStage welcomeStage;
    private Stage stage;
    private VBox root;
    private VBox prizeMatchPanel;
    private HBox controlArea;
    private HBox gameArea;
    private MenuButton modeSelector;
    private MenuButton drawingsSelector;
    private GridPane numberGrid;
    private Label statusLabel;
    private ImageView slotIconView;
    private ImageView slotIconView2;
    private ControlButton playButton;
    private ControlButton randomButton;
    private ControlButton clearButton;
    private ControlButton historyButton;
    private ControlButton restartButton;
    private PopOver modePopOver;
    private PopOver drawingsPopOver;

    public GameStage(WelcomeStage welcomeStage) {
        audioController = new AudioController();
        prizeItemBoxes = new ArrayList<>();
        gameHistories = new ArrayList<>();
        this.welcomeStage = welcomeStage;
        gameController = new GameController();
        initializeStage();
        PauseTransition slowPause = new PauseTransition(Duration.seconds(0.4));
        slowPause.setOnFinished(e -> {
            InfoWindow.showRules(true);
        });
        slowPause.play();
    }

    private void initializeStage() {
        stage = new Stage();
        stage.setTitle("Keno Game");
        root = createLayout();
        root.setStyle(ThemeStyles.LUXURY_BACKGROUND);
        if (gameController.getGameMode() == null && gameController.getGameDrawings() == null) {
            disableAllButtons();
        }
        Scene scene = new Scene(root);
        setUpCheatKeyHandler(scene);
        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(810);
        stage.setResizable(false);

        stage.setOnCloseRequest(e -> {
            e.consume();
            handleBack();
        });

        PauseTransition delay = new PauseTransition(Duration.seconds(1.2));
        delay.setOnFinished(e -> showModeSelectorPopOver());
        delay.play();
    }


    private void showModeSelectorPopOver() {
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

    private void showDrawingsSelectorPopOver() {
        if (drawingsPopOver != null && drawingsPopOver.isShowing()) {
            drawingsPopOver.hide();
        }
        Label tip = new Label("Select drawings here !!!");
        tip.setStyle("-fx-padding: 10; -fx-font-size: 16px;");
        drawingsPopOver = new PopOver(tip);
        drawingsPopOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        drawingsPopOver.setDetachable(false);
        drawingsPopOver.setAutoHide(false);
        drawingsPopOver.show(drawingsSelector);
    }


    private VBox createLayout() {
        VBox layout = new VBox();
        layout.setSpacing(20);
        MenuBar menuBar = createMenu();
        gameArea = createGameArea();
        HBox infoArea = createInfoArea();
        controlArea = createControlArea();

        layout.getChildren().addAll(menuBar, infoArea, gameArea, controlArea);
        return layout;
    }

    private HBox createControlArea() {
        HBox controlArea = new HBox();
        controlArea.setSpacing(50);
        VBox toolButtons = new VBox();
        VBox featureButtons = new VBox();
        toolButtons.setSpacing(10);
        randomButton = new ControlButton("Random", ButtonStyles.ButtonType.PRIMARY, "CONTROL");
        clearButton = new ControlButton("Clear", ButtonStyles.ButtonType.DANGER, "CONTROL");
        randomButton.setPrefWidth(150);
        randomButton.setOnAction(e -> handleRandomSelection());
        clearButton.setPrefWidth(150);
        clearButton.setOnAction(e -> handleClearSelection());
        toolButtons.getChildren().addAll(randomButton, clearButton);

        playButton = new ControlButton("Play", ButtonStyles.ButtonType.SUCCESS, "CONTROL");
        playButton.setPrefWidth(400);
        playButton.setPrefHeight(98);
        playButton.setStyle(playButton.getStyle() + "-fx-font-size: 32px;");
        Image startIcon = new Image(getClass().getResourceAsStream("/icons/start.gif"));
        ImageView startIconView = new ImageView(startIcon);
        playButton.setGraphic(startIconView);
        playButton.setContentDisplay(ContentDisplay.LEFT);
        playButton.setOnAction(e -> handleStartGame());


        featureButtons.setSpacing(10);
        historyButton = new ControlButton("History", ButtonStyles.ButtonType.PRIMARY, "CONTROL");
        restartButton = new ControlButton("Restart", ButtonStyles.ButtonType.DANGER, "CONTROL");
        historyButton.setPrefWidth(150);
        historyButton.setOnAction(e -> {
            String histories = gameHistories.isEmpty() ? "No game history available." : generateHistoryText();
            InfoWindow.showHistory(histories);
        });
        restartButton.setPrefWidth(150);
        featureButtons.getChildren().addAll(historyButton, restartButton);
        controlArea.getChildren().addAll(toolButtons, playButton, featureButtons);
        controlArea.setAlignment(Pos.CENTER);
        controlArea.setPadding(new Insets(0, 50, 0, 50));
        controlArea.setPrefWidth(800);
        return controlArea;
    }

    private String generateHistoryText() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-15s %-15s %-15s\n", "ID","Total Rounds", "Total Matches", "Total Winnings"));
        sb.append("-----------------------------------------------------\n");
        for (int i = 0; i < gameHistories.size(); i++) {
            GameHistory history = gameHistories.get(i);
            sb.append(String.format("%-25d %-25d %-30d $%-14d\n", i + 1, history.getTotalRounds(), history.getTotalMatchedCount(), history.getTotalPrize()));
        }
        return sb.toString();
    }

    private void handleStartGame() {
        currentRounds = 0;
        totalRounds = gameController.getMaxDrawings();
        totalWinnings = 0;
        if(!gameController.isReadyToPlay()){
            statusLabel.setText("Please select " + gameController.getMaxSelections() + " numbers to play.");
            statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_DANGER);
            return;
        }
       startNextRound();
    }

    private void startNextRound() {
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
        gameHistories.add(new GameHistory(totalRounds, totalMatchCount,totalWinnings));
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


    private HBox createInfoArea() {
        HBox infoArea = new HBox();
        infoArea.setAlignment(Pos.CENTER);
        infoArea.setSpacing(10);
        infoArea.setPadding(new Insets(10));

        drawingsSelector = createDrawingsSelector();

        statusLabel = new Label("Please select a game mode to start.");
        statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_NEUTRAL);
        statusLabel.setAlignment(Pos.CENTER);
        statusLabel.setPrefWidth(400);

        modeSelector = createModeButton();

        Image slotIcon = new Image(getClass().getResourceAsStream("/icons/slot.gif"));

        slotIconView = new ImageView(slotIcon);
        slotIconView.setScaleX(-1);
        slotIconView2 = new ImageView(slotIcon);
        slotIconView.setVisible(false);
        slotIconView2.setVisible(false);

        infoArea.getChildren().addAll(modeSelector, slotIconView, statusLabel, slotIconView2, drawingsSelector);
        return infoArea;
    }

    private MenuButton createDrawingsSelector() {
        MenuButton drawingsSelector = new MenuButton("Select Draws");
        drawingsSelector.setStyle(ButtonStyles.MENU_BUTTON_MODE);
        drawingsSelector.setPrefWidth(170);
        drawingsSelector.setAlignment(Pos.CENTER);
        drawingsSelector.setOnMouseClicked(e -> {
            if (drawingsPopOver != null && drawingsPopOver.isShowing()) {
                drawingsPopOver.hide();
            }
        });
        MenuItem oneDrawings = MenuFactory.createMenuItem("1 round", () -> handleDrawingsChange(GameDrawings.ONE_DRAWING));
        MenuItem twoDrawings = MenuFactory.createMenuItem("2 round", () -> handleDrawingsChange(GameDrawings.TWO_DRAWING));
        MenuItem threeDrawings = MenuFactory.createMenuItem("3 round", () -> handleDrawingsChange(GameDrawings.THREE_DRAWING));
        MenuItem fourDrawings = MenuFactory.createMenuItem("4 round", () -> handleDrawingsChange(GameDrawings.FOUR_DRAWING));
        drawingsSelector.getItems().addAll(oneDrawings, twoDrawings, threeDrawings, fourDrawings);
        return drawingsSelector;
    }

    private void handleDrawingsChange(GameDrawings gameDrawings) {
        audioController.playSound(AudioController.MODE_SOUND);
        gameController.setGameDrawings(gameDrawings);
        drawingsSelector.setText("Draws : " + gameDrawings.getMaxDrawings());
        statusLabel.setText("Drawings changed to " + gameDrawings.getDisplayName() + "(s).");
        statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
        if (allDisabled && gameController.getGameMode() != null) {
            enableAllButtons();
        }
    }


    private MenuButton createModeButton() {
        MenuButton modeSelector = new MenuButton("Select Mode");
        modeSelector.setStyle(ButtonStyles.MENU_BUTTON_MODE);
        modeSelector.setPrefWidth(170);
        modeSelector.setAlignment(Pos.CENTER);
        modeSelector.setOnMouseClicked(e -> {
            if (modePopOver != null && modePopOver.isShowing()) {
                modePopOver.hide();
            }
        });
        MenuItem oneSpot = MenuFactory.createMenuItem("1 Spot", () -> handleModeChange(GameMode.ONE_SPOT));
        MenuItem fourSpot = MenuFactory.createMenuItem("4 Spot", () -> handleModeChange(GameMode.FOUR_SPOT));
        MenuItem eightSpot = MenuFactory.createMenuItem("8 Spot", () -> handleModeChange(GameMode.EIGHT_SPOT));
        MenuItem tenSpot = MenuFactory.createMenuItem("10 Spot", () -> handleModeChange(GameMode.TEN_SPOT));
        modeSelector.getItems().addAll(oneSpot, fourSpot, eightSpot, tenSpot);
        return modeSelector;
    }

    private void handleModeChange(GameMode gameMode) {
        audioController.playSound(AudioController.MODE_SOUND);
        gameController.setGameMode(gameMode);
        modeSelector.setText("Spots : " + gameMode.getMaxSpots());
        statusLabel.setText("Mode changed to " + gameMode.getDisplayName() + "(s).");
        statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
        updatePrizeMatchPanel(gameMode);
        InfoWindow.showOdds(gameController.getGameMode(), true);
        drawingsSelector.setDisable(false);
        PauseTransition delay = new PauseTransition(Duration.seconds(0.6));
        delay.setOnFinished(e -> showDrawingsSelectorPopOver());
        if(gameController.getGameDrawings() == null){
            delay.play();
        }else{
            resetNumberButtons();
            resetPrizeHighlights();
            playButton.setDisable(true);
        }
    }

    private void updatePrizeMatchPanel(GameMode mode) {
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

    private HBox createGameArea() {
        HBox gameArea = new HBox();
        gameArea.setAlignment(Pos.CENTER);
        gameArea.setSpacing(20);
        gameArea.setPadding(new Insets(10));

        numberGrid = createNumGrid();
        prizeMatchPanel = createPrizeMatchPanel();
        gameArea.getChildren().addAll(numberGrid, prizeMatchPanel);
        return gameArea;
    }

    private VBox createPrizeMatchPanel() {
        VBox prizePanel = new VBox();
        prizePanel.setSpacing(20);
        prizePanel.setPadding(new Insets(10));
        prizePanel.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-background-radius: 10;");
        prizePanel.setPrefWidth(200);
        prizePanel.setPrefHeight(200);
        prizePanel.setAlignment(Pos.BOTTOM_CENTER);
        return prizePanel;
    }

    private GridPane createNumGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        for (int i = 0; i < 80; i++) {
            int num = i + 1;
            NumberButton btn = new NumberButton(num);
            numberButtons[i] = btn;

            btn.setOnAction(e -> handleButtonClick(btn));
            int row = i / 10;
            int col = i % 10;
            grid.add(btn, col, row);
        }
        return grid;
    }

    private void handleButtonClick(NumberButton btn) {

        if (!btn.isSelectable()) {
            return;
        }
        audioController.playSound(AudioController.CLICK_SOUND);
        resetForNewRound();
        resetPrizeHighlights();
        if (btn.isSelected()) {
            handleDeselection(btn);
        } else {
            handleSelection(btn);
        }
    }

    private void handleDeselection(NumberButton btn) {
        if (!gameController.deselectNumber(btn.getNumber())) {
            return;
        }
        btn.deselect();
        if (gameController.getSelectedCount() == 0) {
            statusLabel.setText("Please select you lucky number .");
            statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_NEUTRAL);
            playButton.setDisable(true);
        }
    }

    private void handleSelection(NumberButton btn) {
        if (!gameController.selectNumber(btn.getNumber())) {
            if (!gameController.canSelectMore()) {
                statusLabel.setText("You can only select up to " + gameController.getMaxSelections() + " numbers.");
                statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_DANGER);
            }
            return;
        }

        btn.select();
        if (gameController.getSelectedCount() > 0) {
            statusLabel.setText("You have selected " + gameController.getSelectedCount() + " number(s).");
            statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_NEUTRAL);
            playButton.setDisable(false);
        }
    }

    private void handleClearSelection() {
        if (playButton.isDisabled()) {
            return;
        }
        resetNumberButtons();
        resetPrizeHighlights();
        audioController.playSound(AudioController.CLEAR_SOUND);
        gameController.getSelectedNumbers().clear();
        statusLabel.setText("Selections cleared.");
        statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
        playButton.setDisable(true);
    }

    private MenuBar createMenu() {
        MenuBar menuBar = new MenuBar();
        Menu help = MenuFactory.createHelpMenu(InfoWindow::showRules,
                () -> InfoWindow.showOdds(gameController.getGameMode() == null ? GameMode.TEN_SPOT : gameController.getGameMode()),
                stage::close);
        Menu settings = MenuFactory.createSettingsMenu(this::handleResetPreferences, this::toggleTheme);
        menuBar.getMenus().addAll(settings, help);
        return menuBar;
    }

    public void handleResetPreferences() {
        InfoWindow.resetAllPreferences();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Settings Reset");
        alert.setContentText("All preferences have been reset to default.");
        alert.showAndWait();
    }

    public void show() {
        stage.show();
    }

    private void toggleTheme() {
        String currentStyle = root.getStyle();
        if (currentStyle.equals(ThemeStyles.LUXURY_BACKGROUND)) {
            root.setStyle(ThemeStyles.GAME_BACKGROUND_SUBTLE);
        } else {
            root.setStyle(ThemeStyles.LUXURY_BACKGROUND);
        }
    }

    private void handleBack() {
        stage.hide();
        if (welcomeStage != null) {
            welcomeStage.show();
        }
    }

    private void handleRandomSelection() {
        resetNumberButtons();
        resetPrizeHighlights();
        audioController.playSound(AudioController.CLICK_SOUND);
        gameController.randomSelectNumbersForUser();
        for (int num : gameController.getSelectedNumbers()) {
            numberButtons[num - 1].select();
        }
        playButton.setDisable(false);
        statusLabel.setText("Randomly selected " + gameController.getSelectedCount() + " number(s).");
    }

    private void resetNumberButtons() {
        for (NumberButton btn : numberButtons) {
            btn.reset();
        }
    }

    private void resetForNewRound() {
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

    private void disableAllButtons() {
        numberGrid.setDisable(true);
        controlArea.setDisable(true);
        drawingsSelector.setDisable(true);
        allDisabled = true;
    }

    private void enableAllButtons() {
        numberGrid.setDisable(false);
        controlArea.setDisable(false);
        allDisabled = false;
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

    public void resetPrizeHighlights() {
        for (PrizeItemBox item : prizeItemBoxes) {
            item.reset();
        }
    }


    private void setUpCheatKeyHandler(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case C:
                    if (event.isControlDown() && event.isShiftDown()) {
                        cheatMode = true;
                    }
                    break;
                case ENTER:
                    if (gameController.getGameMode() != null && !playButton.isDisable()) {
                        handleStartGame();
                    }
                    break;
                case Q:
                    if (gameController.getGameMode() != null && !playButton.isDisable()) {
                        handleClearSelection();
                    }
                    break;
                case R:
                    if (gameController.getGameMode() != null) {
                        handleRandomSelection();
                    }
                    break;
                default:
                    break;
            }
        });
    }
}
