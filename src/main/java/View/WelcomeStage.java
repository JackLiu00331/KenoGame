package View;

import Component.ControlButton;
import Model.GameMode;
import Utils.ButtonStyles;
import Utils.ThemeStyles;
import Utils.Util;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class WelcomeStage {
    private Stage stage;
    private MenuBar menuBar;
    private GameStage gameStage;

    public WelcomeStage() {
        initializeStage();
    }

    private void initializeStage() {
        stage = new Stage();
        stage.setTitle("Keno Game");
        menuBar = createMenuBar();
        BorderPane root = createBorderPane();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(600);
        stage.setResizable(false);
    }

    private BorderPane createBorderPane() {
        BorderPane root = new BorderPane();
        VBox homeScene = createScene();
        root.setTop(menuBar);
        root.setCenter(homeScene);
        root.setStyle(ThemeStyles.WELCOME_BACKGROUND);
        return root;
    }

    private VBox createScene() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setStyle("-fx-padding: 10;");

        Label gameTitle = new Label("KENO");
        gameTitle.setStyle(ThemeStyles.GAME_TITLE_LARGE);
        gameTitle.setPadding(new Insets(0, 0, 20, 0));

        Image gameIcon = new Image(getClass().getResourceAsStream("/icons/coins.gif"));
        ImageView gameIconView = new ImageView(gameIcon);
        ImageView gameIconView2 = new ImageView(gameIcon);
        gameIconView2.setScaleX(-1);

        HBox titleBox = new HBox();
        titleBox.getChildren().addAll(gameIconView, gameTitle, gameIconView2);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setSpacing(10);

        Button startGameButton = createStartGameButton();
        Button exitGameButton = createExitGameButton();

        vbox.getChildren().addAll(titleBox, startGameButton, exitGameButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        return vbox;
    }

    private Button createExitGameButton() {
        ControlButton exitGameButton = new ControlButton("Exit", ButtonStyles.ButtonType.DANGER, "MENU");
        exitGameButton.setMaxWidth(150);
        exitGameButton.setOnAction(e -> stage.close());
        return exitGameButton;
    }

    private Button createStartGameButton() {
        ControlButton startGameButton = new ControlButton("Start", ButtonStyles.ButtonType.SUCCESS, "MENU");
        startGameButton.setMaxWidth(150);
        startGameButton.setOnAction(e -> {
            gameStage = new GameStage(this);
            gameStage.show();
            stage.close();
        });
        return startGameButton;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu gameMenu = new Menu("Help");

        MenuItem gameRules = Util.createMenuItem("Game Rules", InfoWindow::showRules);
        MenuItem prizeTable = Util.createMenuItem("Prize Table", () -> InfoWindow.showOdds(GameMode.TEN_SPOT));

        MenuItem exitGame = Util.createMenuItem("Exit Game", () -> stage.close());
        gameMenu.getItems().addAll(gameRules, prizeTable, exitGame);
        menuBar.getMenus().add(gameMenu);
        return menuBar;
    }


    public void show() {
        stage.show();
    }

    public void hide() {
        stage.hide();
    }

}