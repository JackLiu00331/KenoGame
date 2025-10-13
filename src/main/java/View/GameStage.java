package View;

import Component.ControlButton;
import Component.NumberButton;
import Controller.GameController;
import Model.GameMode;
import Utils.ButtonStyles;
import Utils.ThemeStyles;
import Utils.Util;
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

public class GameStage {

    GameController gameController;
    NumberButton[] numberButtons = new NumberButton[80];
    private boolean allDisabled = false;

    private WelcomeStage welcomeStage;
    private Stage stage;
    VBox root;
    Label balanceLabel;
    MenuButton modeSelector;
    Label statusLabel;
    ImageView slotIconView;
    ImageView slotIconView2;
    ControlButton playButton;
    ControlButton randomButton;
    PauseTransition slowPause = new PauseTransition(javafx.util.Duration.seconds(0.4));

    public GameStage(WelcomeStage welcomeStage) {
        this.welcomeStage = welcomeStage;
        gameController = new GameController();
        initializeStage();
        slowPause.play();
        slowPause.setOnFinished(e -> {
            InfoWindow.showRules(true);
        });
    }

    private void initializeStage() {
        stage = new Stage();
        stage.setTitle("Keno Game");
        root = createLayout();
        root.setStyle(ThemeStyles.LUXURY_BACKGROUND);
        if (gameController.getGameMode() == null) {
            disableAllButtons();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(1200);
        stage.setHeight(1000);
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
        HBox gameArea = createGameArea();
        HBox infoArea = createInfoArea();
        HBox controlArea = createControlArea();

        layout.getChildren().addAll(menuBar, infoArea, gameArea, controlArea);
        return layout;
    }

    private HBox createControlArea() {
        HBox controlArea = new HBox();
        controlArea.setSpacing(10);
        playButton = new ControlButton("Play", ButtonStyles.ButtonType.PRIMARY);
        randomButton = new ControlButton("Random", ButtonStyles.ButtonType.NEUTRAL);
        controlArea.getChildren().addAll(randomButton, playButton);
        return controlArea;
    }

    private HBox createInfoArea() {
        HBox infoArea = new HBox();
        infoArea.setAlignment(Pos.CENTER);
        infoArea.setSpacing(30);
        infoArea.setPadding(new Insets(20));

        balanceLabel = new Label("Current Balance: $1000");
        balanceLabel.setStyle(ThemeStyles.INFO_LABEL_BALANCE);

        statusLabel = new Label("Please select a game mode to start.");
        statusLabel.setStyle(ThemeStyles.STATUS_TEXT);
        statusLabel.setAlignment(Pos.CENTER);
        statusLabel.setPrefWidth(300);

        modeSelector = new MenuButton("Choose Game Mode");
        MenuItem oneSpot = Util.createMenuItem("1 Spot", () -> handleModeChange(GameMode.ONE_SPOT));
        MenuItem fourSpot = Util.createMenuItem("4 Spot", () -> handleModeChange(GameMode.FOUR_SPOT));
        MenuItem eightSpot = Util.createMenuItem("8 Spot", () -> handleModeChange(GameMode.EIGHT_SPOT));
        MenuItem tenSpot = Util.createMenuItem("10 Spot", () -> handleModeChange(GameMode.TEN_SPOT));
        modeSelector.setStyle(ButtonStyles.MENU_BUTTON_MODE);
        modeSelector.getItems().addAll(oneSpot, fourSpot, eightSpot, tenSpot);
        modeSelector.setPrefWidth(244);

        Image slotIcon = new Image(getClass().getResourceAsStream("/icons/slot.gif"));

        slotIconView = new ImageView(slotIcon);
        slotIconView.setScaleX(-1);
        slotIconView2 = new ImageView(slotIcon);
        slotIconView.setVisible(false);
        slotIconView2.setVisible(false);
        infoArea.getChildren().addAll(modeSelector, slotIconView, statusLabel, slotIconView2, balanceLabel);
        return infoArea;
    }

    private void handleModeChange(GameMode gameMode) {
        gameController.setGameMode(gameMode);
        modeSelector.setText("Game Mode: " + gameMode.getDisplayName());
        statusLabel.setText("Mode changed to " + gameMode.getDisplayName());
        if (allDisabled && gameController.getGameMode() != null) {
            enableAllButtons();
        }
        for (NumberButton btn : numberButtons) {
            btn.reset();
        }
        InfoWindow.showOdds(gameMode, true);
    }

    private HBox createGameArea() {
        HBox gameArea = new HBox();
        gameArea.setAlignment(Pos.CENTER);
        gameArea.setPadding(new Insets(20));
        GridPane numberGrid = createNumGrid();
        gameArea.getChildren().addAll(numberGrid);
        return gameArea;
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
            playButton.setDisable(true);
        }
    }

    private void handleSelection(NumberButton btn) {
        if (!gameController.selectNumber(btn.getNumber())) {
            if (!gameController.canSelectMore()) {
                statusLabel.setText("You can only select up to " + gameController.getMaxSelections() + " numbers.");
            }
            return;
        }

        btn.select();
        if (gameController.getSelectedCount() > 0) {
            statusLabel.setText("You have selected " + gameController.getSelectedCount() + " number(s).");
            playButton.setDisable(false);
        }
    }

    private MenuBar createMenu() {
        MenuBar menuBar = new MenuBar();
        Menu settings = new Menu("Settings");
        Menu help = new Menu("Help");
        MenuItem gameRules = Util.createMenuItem("Game Rules", InfoWindow::showRules);
        MenuItem prizeTable = Util.createMenuItem("Prize Table", () -> InfoWindow.showOdds(gameController.getGameMode() == null ? GameMode.TEN_SPOT : gameController.getGameMode()));
        MenuItem resetPreferences = Util.createMenuItem("Reset Preferences", () -> {
            InfoWindow.resetAllPreferences();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Settings Reset");
            alert.setContentText("All preferences have been reset to default.");
            alert.showAndWait();
        });
        MenuItem changeTheme = Util.createMenuItem("Change Theme", this::toggleTheme);
        settings.getItems().addAll(changeTheme, resetPreferences);
        help.getItems().addAll(gameRules, prizeTable);
        menuBar.getMenus().addAll(settings, help);
        return menuBar;
    }

    public void show() {
        stage.show();
    }

    private void toggleTheme() {
        String currentStyle = root.getStyle();
        if (currentStyle.equals(ThemeStyles.LUXURY_BACKGROUND)) {
            root.setStyle(ThemeStyles.GAME_BACKGROUND);
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

    private void disableAllButtons() {
        for (NumberButton btn : numberButtons) {
            btn.disableButton();
        }
        playButton.setDisable(true);
        randomButton.setDisable(true);
        allDisabled = true;
    }

    private void enableAllButtons() {
        for (NumberButton btn : numberButtons) {
            btn.enableButton();
        }
        playButton.setDisable(false);
        randomButton.setDisable(false);
        allDisabled = false;
    }
}
