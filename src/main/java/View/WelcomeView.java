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

/**
 * WelcomeView class represents the welcome screen of the Keno game.
 * It provides options to start the game or exit, along with a menu bar for additional information.
 */
public class WelcomeView {
    private Stage stage;
    private MenuBar menuBar;
    private GameController gameController;
    private GameView gameView;
    BorderPane root;

    // Constructor to initialize the WelcomeView
    public WelcomeView() {
        initializeStage();
    }

    /**
     * Initializes the main stage and its components.
     */
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

    /**
     * Creates the main BorderPane layout for the welcome screen.
     * @return the configured BorderPane
     */
    private BorderPane createBorderPane() {
        BorderPane root = new BorderPane();
        VBox homeScene = createScene();
        root.setTop(menuBar);
        root.setCenter(homeScene);
        root.setStyle(ThemeStyles.WELCOME_BACKGROUND);
        return root;
    }

    /**
     * Creates the main scene layout with title and buttons.
     * @return the configured VBox
     */
    private VBox createScene() {
        // Create game title label
        Label gameTitle = new Label("KENO");
        gameTitle.setStyle(ThemeStyles.GAME_TITLE_LARGE);
        gameTitle.setPadding(new Insets(0, 0, 20, 0));

        // Load game icon image
        Image gameIcon = new Image(getClass().getResourceAsStream("/icons/coins.gif"));
        ImageView gameIconView = new ImageView(gameIcon);
        ImageView gameIconView2 = new ImageView(gameIcon);
        gameIconView2.setScaleX(-1);

        // Create title box with icon and title
        HBox titleBox = LayoutBuilder.hbox()
                .alignment(Pos.CENTER)
                .spacing(10)
                .children(gameIconView, gameTitle, gameIconView2)
                .build();

        // Create Start Game button
        Button startGameButton = new ButtonBuilder("Start")
                .asMenu()
                .size(150, -1)
                .onClick(e -> handleStart())
                .style(ButtonStyles.ButtonType.SUCCESS)
                .build();

        // Create Exit Game button
        Button exitGameButton = new ButtonBuilder("Exit")
                .asMenu()
                .size(150, -1)
                .onClick(e -> stage.close())
                .style(ButtonStyles.ButtonType.DANGER)
                .build();

        // Assemble the main scene layout
        VBox scene = LayoutBuilder.vbox()
                .alignment(Pos.CENTER)
                .spacing(20)
                .padding(new Insets(20))
                .children(titleBox, startGameButton, exitGameButton)
                .build();

        return scene;
    }

    /**
     * Handles the action when the Start button is clicked.
     * Initializes the GameView and GameController, shows the game view, and hides the welcome stage.
     */
    private void handleStart() {
        gameView = new GameView();
        gameController = new GameController(gameView, this);
        gameView.show();
        stage.hide();
    }

    /**
     * Creates the menu bar with help options.
     * @return the configured MenuBar
     */
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu gameMenu = MenuFactory.createHelpMenu(() ->InfoWindow.showRules(root), () -> InfoWindow.showOdds(GameMode.TEN_SPOT, root), stage::close);
        menuBar.getMenus().add(gameMenu);
        return menuBar;
    }

    /**
     * Displays the welcome stage.
     */
    public void show() {
        stage.show();
    }


}