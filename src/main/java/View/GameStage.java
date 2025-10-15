package View;

import Component.ControlButton;
import Component.MenuFactory;
import Model.GameDrawings;
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
    private Integer totalWinnings = 0;
    private boolean cheatMode = false;
    private AudioClip clickSound;
    private AudioClip clearSound;
    private AudioClip matchSound;
    private AudioClip modeChangeSound;
    private AudioClip startGameSound;
    private AudioClip finishRoundSound;
    private AudioClip prizeMatchSound;

    private WelcomeStage welcomeStage;
    private Stage stage;
    private VBox root;
    private VBox prizeMatchPanel;
    private HBox controlArea;
    private HBox gameArea;
    private MenuButton modeSelector;
    private MenuButton drawingsSelecter;
    private GridPane numberGrid;
    private Label statusLabel;
    private ImageView slotIconView;
    private ImageView slotIconView2;
    private ControlButton playButton;
    private ControlButton randomButton;
    private ControlButton clearButton;
    private ControlButton autoPlayButton;
    private ControlButton betButton;
    private PopOver popOver;

    public GameStage(WelcomeStage welcomeStage) {
        clickSound = new AudioClip(getClass().getResource("/sound/button.wav").toExternalForm());
        clearSound = new AudioClip(getClass().getResource("/sound/clear.wav").toExternalForm());
        matchSound = new AudioClip(getClass().getResource("/sound/match.wav").toExternalForm());
        modeChangeSound = new AudioClip(getClass().getResource("/sound/mode.wav").toExternalForm());
        startGameSound = new AudioClip(getClass().getResource("/sound/start.wav").toExternalForm());
        finishRoundSound = new AudioClip(getClass().getResource("/sound/finish.wav").toExternalForm());
        prizeMatchSound = new AudioClip(getClass().getResource("/sound/jackpot.wav").toExternalForm());
        prizeItemBoxes = new ArrayList<>();
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
        stage.setWidth(1200);
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
        if (popOver != null && popOver.isShowing()) {
            popOver.hide();
        }
        Label tip = new Label("Select game mode here !!!");
        tip.setStyle("-fx-padding: 10; -fx-font-size: 16px;");
        popOver = new PopOver(tip);
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        popOver.setDetachable(false);
        popOver.setAutoHide(false);
        popOver.show(modeSelector);
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
        autoPlayButton = new ControlButton("Auto Play", ButtonStyles.ButtonType.PRIMARY, "CONTROL");
        betButton = new ControlButton("Bet", ButtonStyles.ButtonType.PRIMARY, "CONTROL");
        autoPlayButton.setPrefWidth(150);
        betButton.setPrefWidth(150);
        featureButtons.getChildren().addAll(autoPlayButton, betButton);
        controlArea.getChildren().addAll(toolButtons, playButton, featureButtons);
        controlArea.setAlignment(Pos.CENTER);
        controlArea.setPadding(new Insets(0, 50, 0, 50));
        controlArea.setPrefWidth(800);
        return controlArea;
    }

    private void handleStartGame() {
        currentRounds = 0;
        totalRounds = gameController.getMaxDrawings();
        totalWinnings = 0;
       startNextRound();
    }

    private void startNextRound() {
        currentRounds++;
        startGameSound.play();
        resetForNewRound();
        resetPrizeHighlights();
        controlArea.setDisable(true);
        setAllNumberSelectable(false);
        modeSelector.setDisable(true);
        drawingsSelecter.setDisable(true);
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
                prizeMatchSound.play();
                statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_SURPRISE);
            }else {
                finishRoundSound.play();
                statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
            }
            statusLabel.setText("Round over! You matched " + currentMatchCount + " number(s).");

            List<Integer> matchedNumbers = new ArrayList<>();
            for (int num : systemSelections) {
                if (gameController.getSelectedNumbers().contains(num)) {
                    matchedNumbers.add(num);
                }
            }
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
        currentRounds = 0;
        totalWinnings = 0;
        cheatMode = false;
        controlArea.setDisable(false);
        drawingsSelecter.setDisable(false);
        setAllNumberSelectable(true);
        modeSelector.setDisable(false);
        resetNumberButtons();
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

        drawingsSelecter = createDrawingsSelector();

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

        infoArea.getChildren().addAll(modeSelector, slotIconView, statusLabel, slotIconView2, drawingsSelecter);
        return infoArea;
    }

    private MenuButton createDrawingsSelector() {
        MenuButton drawingsSelector = new MenuButton("Select Draws");
        drawingsSelector.setStyle(ButtonStyles.MENU_BUTTON_MODE);
        drawingsSelector.setPrefWidth(170);
        drawingsSelector.setAlignment(Pos.CENTER);
        MenuItem oneDrawings = MenuFactory.createMenuItem("1 round", () -> handleDrawingsChange(GameDrawings.ONE_DRAWING));
        MenuItem twoDrawings = MenuFactory.createMenuItem("2 round", () -> handleDrawingsChange(GameDrawings.TWO_DRAWING));
        MenuItem threeDrawings = MenuFactory.createMenuItem("3 round", () -> handleDrawingsChange(GameDrawings.THREE_DRAWING));
        MenuItem fourDrawings = MenuFactory.createMenuItem("4 round", () -> handleDrawingsChange(GameDrawings.FOUR_DRAWING));
        drawingsSelector.getItems().addAll(oneDrawings, twoDrawings, threeDrawings, fourDrawings);
        return drawingsSelector;
    }

    private void handleDrawingsChange(GameDrawings gameDrawings) {
        modeChangeSound.play();
        gameController.setGameDrawings(gameDrawings);
        drawingsSelecter.setText("Draws : " + gameDrawings.getMaxDrawings());
        statusLabel.setText("Drawings changed to " + gameDrawings.getDisplayName() + "(s).");
        statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
        if (allDisabled && gameController.getGameMode() != null) {
            enableAllButtons();
        }
        resetNumberButtons();
        resetPrizeHighlights();
        playButton.setDisable(true);
        InfoWindow.showOdds(gameController.getGameMode(), true);
    }


    private MenuButton createModeButton() {
        MenuButton modeSelector = new MenuButton("Select Mode");
        modeSelector.setStyle(ButtonStyles.MENU_BUTTON_MODE);
        modeSelector.setPrefWidth(170);
        modeSelector.setAlignment(Pos.CENTER);
        modeSelector.setOnMouseClicked(e -> {
            if (popOver != null && popOver.isShowing()) {
                popOver.hide();
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
        modeChangeSound.play();
        gameController.setGameMode(gameMode);
        modeSelector.setText("Spots : " + gameMode.getMaxSpots());
        statusLabel.setText("Mode changed to " + gameMode.getDisplayName() + "(s).");
        statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
        updatePrizeMatchPanel(gameMode);
        drawingsSelecter.setDisable(false);
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
        clickSound.play();
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
        clearSound.play();
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
        clickSound.play();
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
        drawingsSelecter.setDisable(true);
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
                matchSound.play();
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
