package View;

import Component.ControlButton;
import Component.MenuFactory;
import Controller.AnimationController;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GameStage {

    // Data Fields
    private GameController gameController;
    private NumberButton[] numberButtons = new NumberButton[80];
    private boolean cheatMode = false;
    private AudioController audioController;
    private WelcomeStage welcomeStage;
    private AnimationController animationController;

    // UI Components
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

    public GameStage(WelcomeStage welcomeStage) {
        audioController = new AudioController();
        gameController = new GameController();
        initializeStage();
        animationController = new AnimationController(audioController, gameController, prizeMatchPanel, controlArea,modeSelector,drawingsSelector,statusLabel,slotIconView,slotIconView2,numberButtons,cheatMode);
        animationController.showNotification(modeSelector);
        this.welcomeStage = welcomeStage;
        animationController.popUpGameRules();
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
            String histories = gameController.getGameHistories().isEmpty() ? "No game history available." : gameController.generateHistoryText();
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



    private void handleStartGame() {
        if(!gameController.isReadyToPlay()){
            statusLabel.setText("Please select " + gameController.getMaxSelections() + " numbers to play.");
            statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_DANGER);
            return;
        }
        animationController.startNextRound();
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
        drawingsSelector.setOnMouseClicked(e -> animationController.hideDrawingsNotification());
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
        if (gameController.getGameMode() != null) {
            enableAllButtons();
        }
    }


    private MenuButton createModeButton() {
        MenuButton modeSelector = new MenuButton("Select Mode");
        modeSelector.setStyle(ButtonStyles.MENU_BUTTON_MODE);
        modeSelector.setPrefWidth(170);
        modeSelector.setAlignment(Pos.CENTER);
        modeSelector.setOnMouseClicked(e -> animationController.hideNotification());
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
        animationController.updatePrizeMatchPanel(gameMode);
        InfoWindow.showOdds(gameController.getGameMode(), true);
        drawingsSelector.setDisable(false);
        if(gameController.getGameDrawings() == null){
            animationController.showNotification(drawingsSelector);
        }else{
            animationController.resetNumberButtons();
            animationController.resetPrizeHighlights();
            playButton.setDisable(true);
        }
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
        animationController.resetForNewRound();
        animationController.resetPrizeHighlights();
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
        animationController.resetNumberButtons();
        animationController.resetPrizeHighlights();
        audioController.playSound(AudioController.CLEAR_SOUND);
        gameController.getSelectedNumbers().clear();
        statusLabel.setText("Selections cleared.");
        statusLabel.setStyle(ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
        playButton.setDisable(true);
    }

    private MenuBar createMenu() {
        MenuBar menuBar = new MenuBar();
        Menu help = MenuFactory.createHelpMenu(InfoWindow::showRules
                , () -> InfoWindow.showOdds(gameController.getGameMode() == null ? GameMode.TEN_SPOT : gameController.getGameMode())
                , stage::close);
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
        animationController.resetNumberButtons();
        animationController.resetPrizeHighlights();
        audioController.playSound(AudioController.CLICK_SOUND);
        gameController.randomSelectNumbersForUser();
        for (int num : gameController.getSelectedNumbers()) {
            numberButtons[num - 1].select();
        }
        playButton.setDisable(false);
        statusLabel.setText("Randomly selected " + gameController.getSelectedCount() + " number(s).");
    }





    private void disableAllButtons() {
        numberGrid.setDisable(true);
        controlArea.setDisable(true);
        drawingsSelector.setDisable(true);
    }

    private void enableAllButtons() {
        numberGrid.setDisable(false);
        controlArea.setDisable(false);
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
