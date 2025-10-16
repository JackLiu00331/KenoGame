package View;

import Controller.GameController;
import View.Component.ButtonBuilder;
import View.Component.LayoutBuilder;
import View.Component.MenuFactory;
import Model.GameMode;
import Utils.ButtonStyles;
import Utils.ThemeStyles;
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


public class WelcomeView {
    private Stage stage;
    private MenuBar menuBar;
    private GameController gameController;
    private GameView gameView;
    BorderPane root;

    public WelcomeView() {
        initializeStage();
    }

    private void initializeStage() {
        stage = new Stage();
        stage.setTitle("Keno Game");
        menuBar = createMenuBar();
        root = createBorderPane();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(600);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
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
        Label gameTitle = new Label("KENO");
        gameTitle.setStyle(ThemeStyles.GAME_TITLE_LARGE);
        gameTitle.setPadding(new Insets(0, 0, 20, 0));

        Image gameIcon = new Image(getClass().getResourceAsStream("/icons/coins.gif"));
        ImageView gameIconView = new ImageView(gameIcon);
        ImageView gameIconView2 = new ImageView(gameIcon);
        gameIconView2.setScaleX(-1);

        HBox titleBox = LayoutBuilder.hbox()
                .alignment(Pos.CENTER)
                .spacing(10)
                .children(gameIconView, gameTitle, gameIconView2)
                .build();

        Button startGameButton = new ButtonBuilder("Start")
                .asMenu()
                .size(150, -1)
                .onClick(e -> handleStart())
                .style(ButtonStyles.ButtonType.SUCCESS)
                .build();

        Button exitGameButton = new ButtonBuilder("Exit")
                .asMenu()
                .size(150, -1)
                .onClick(e -> stage.close())
                .style(ButtonStyles.ButtonType.DANGER)
                .build();

        VBox scene = LayoutBuilder.vbox()
                .alignment(Pos.CENTER)
                .spacing(20)
                .padding(new Insets(20))
                .children(titleBox, startGameButton, exitGameButton)
                .build();

        return scene;
    }

    private void handleStart() {
        gameView = new GameView();
        gameController = new GameController(gameView, this);
        gameView.show();
        stage.hide();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu gameMenu = MenuFactory.createHelpMenu(() ->InfoWindow.showRules(root), () -> InfoWindow.showOdds(GameMode.TEN_SPOT, root), stage::close);
        menuBar.getMenus().add(gameMenu);
        return menuBar;
    }


    public void show() {
        stage.show();
    }


}