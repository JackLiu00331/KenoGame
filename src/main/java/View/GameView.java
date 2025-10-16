package View;

import Model.GameMode;
import Service.GameService;
import Utils.MenuCallback;
import View.Component.*;
import Utils.ButtonStyles;
import Utils.ThemeStyles;
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

public class GameView {

    private Stage stage;
    private VBox root;
    private VBox prizeMatchPanel;
    private HBox controlArea;
    private HBox gameArea;
    private MenuBar menuBar;
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
    private NumberButton[] numberButtons = new NumberButton[80];

    public GameView() {
        initializeStage();
    }

    private void initializeStage() {
        stage = new Stage();
        stage.setTitle("Keno Game");

        root = createLayout();
        root.setStyle(ThemeStyles.LUXURY_BACKGROUND);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(810);
        stage.setResizable(false);
    }

    private VBox createLayout() {
        menuBar = new MenuBar();
        gameArea = createGameArea();
        HBox infoArea = createInfoArea();
        controlArea = createControlArea();

        return LayoutBuilder.vbox()
                .spacing(20)
                .children(menuBar, infoArea, gameArea, controlArea)
                .build();
    }

    public void setupMenus(MenuCallback menuCallback) {
        Menu helpMenu = MenuFactory.createHelpMenu(menuCallback::onShowRules, menuCallback::onShowOdds, menuCallback::onExitGame);
        Menu settingsMenu = MenuFactory.createSettingsMenu(menuCallback::onResetPreferences, this::toggleTheme);
        menuBar.getMenus().addAll(settingsMenu, helpMenu);
    }

    private HBox createInfoArea() {

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

        return LayoutBuilder.hbox()
                .alignment(Pos.CENTER)
                .spacing(10)
                .padding(new Insets(10))
                .children(modeSelector, slotIconView, statusLabel, slotIconView2, drawingsSelector)
                .build();
    }

    private HBox createGameArea() {

        numberGrid = createNumGrid();
        prizeMatchPanel = createPrizeMatchPanel();

       return LayoutBuilder.hbox()
                .alignment(Pos.CENTER)
                .spacing(20)
                .padding(new Insets(10))
               .children(numberGrid,prizeMatchPanel)
                .build();
    }

    private GridPane createNumGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        for (int i = 0; i < 80; i++) {
            NumberButton btn = new NumberButton(i + 1);
            numberButtons[i] = btn;
            grid.add(btn, i % 10, i / 10);
        }
        return grid;
    }

    private VBox createPrizeMatchPanel() {
        return LayoutBuilder.vbox()
                .spacing(20)
                .padding(new Insets(10))
                .style("-fx-background-color: rgba(0,0,0,0.3); -fx-background-radius: 10;")
                .size(200,200)
                .alignment(Pos.BOTTOM_CENTER)
                .build();
    }

    private HBox createControlArea() {
        VBox toolButtons = createToolButtons();
        VBox featureButtons = createFeatureButtons();

        playButton = new ControlButton("Play", ButtonStyles.ButtonType.SUCCESS, "CONTROL");
        playButton.setPrefWidth(400);
        playButton.setPrefHeight(98);
        playButton.setStyle(playButton.getStyle() + "-fx-font-size: 32px;");
        Image startIcon = new Image(getClass().getResourceAsStream("/icons/start.gif"));
        ImageView startIconView = new ImageView(startIcon);
        playButton.setGraphic(startIconView);
        playButton.setContentDisplay(ContentDisplay.LEFT);


        return LayoutBuilder.hbox()
                .alignment(Pos.CENTER)
                .spacing(50)
                .padding(new Insets(0, 50, 0, 50))
                .prefWidth(800)
                .children(toolButtons, playButton, featureButtons)
                .build();
    }

    private VBox createFeatureButtons() {
        historyButton = new ButtonBuilder("History")
                .size(150, -1)
                .style(ButtonStyles.ButtonType.PRIMARY)
                .build();

        restartButton = new ButtonBuilder("Restart")
                .size(150, -1)
                .style(ButtonStyles.ButtonType.DANGER)
                .build();

        return LayoutBuilder.vbox()
                .spacing(10)
                .children(historyButton, restartButton)
                .build();
    }

    private VBox createToolButtons() {
        randomButton = new ButtonBuilder("Random")
                .size(150, -1)
                .style(ButtonStyles.ButtonType.PRIMARY)
                .build();

        clearButton = new ButtonBuilder("Clear")
                .size(150, -1)
                .style(ButtonStyles.ButtonType.DANGER)
                .build();

        return LayoutBuilder.vbox()
                .spacing(10)
                .children(randomButton, clearButton)
                .build();
    }


    private MenuButton createDrawingsSelector() {
        MenuButton drawingsSelector = new MenuButton("Select Draws");
        drawingsSelector.setStyle(ButtonStyles.MENU_BUTTON_MODE);
        drawingsSelector.setPrefWidth(170);
        drawingsSelector.setAlignment(Pos.CENTER);
        return drawingsSelector;
    }




    private MenuButton createModeButton() {
        MenuButton modeSelector = new MenuButton("Select Mode");
        modeSelector.setStyle(ButtonStyles.MENU_BUTTON_MODE);
        modeSelector.setPrefWidth(170);
        modeSelector.setAlignment(Pos.CENTER);
        return modeSelector;
    }


    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return stage.getScene();
    }

    public VBox getRoot() {
        return root;
    }

    public VBox getPrizeMatchPanel() {
        return prizeMatchPanel;
    }

    public HBox getControlArea() {
        return controlArea;
    }

    public HBox getGameArea() {
        return gameArea;
    }

    public MenuButton getModeSelector() {
        return modeSelector;
    }

    public MenuButton getDrawingsSelector() {
        return drawingsSelector;
    }

    public GridPane getNumberGrid() {
        return numberGrid;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public ImageView getSlotIconView() {
        return slotIconView;
    }

    public ImageView getSlotIconView2() {
        return slotIconView2;
    }

    public ControlButton getPlayButton() {
        return playButton;
    }

    public ControlButton getRandomButton() {
        return randomButton;
    }

    public ControlButton getClearButton() {
        return clearButton;
    }

    public ControlButton getHistoryButton() {
        return historyButton;
    }

    public ControlButton getRestartButton() {
        return restartButton;
    }

    public NumberButton[] getNumberButtons() {
        return numberButtons;
    }


    public void updateStatusLabel(String message, String style) {
        statusLabel.setText(message);
        statusLabel.setStyle(style);
    }

    public void updateModeSelectorText(String text) {
        modeSelector.setText(text);
    }

    public void updateDrawingsSelectorText(String text) {
        drawingsSelector.setText(text);
    }

    public void setSlotIconsVisible(boolean visible) {
        slotIconView.setVisible(visible);
        slotIconView2.setVisible(visible);
    }

    public void hide() {
        stage.hide();
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

    public void disableAllButtons() {
        numberGrid.setDisable(true);
        controlArea.setDisable(true);
        drawingsSelector.setDisable(true);
    }

    public void enableAllButtons() {
        numberGrid.setDisable(false);
        controlArea.setDisable(false);
    }

}
