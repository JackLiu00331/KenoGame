package Controller;

import Model.GameDrawings;
import Model.GameMode;
import Model.GameState;
import Service.AnimationService;
import Service.AudioService;
import Service.GameService;
import Utils.MenuCallback;
import Utils.ThemeStyles;
import View.Component.MenuFactory;
import View.Component.NumberButton;
import View.GameView;
import View.InfoWindow;
import View.WelcomeView;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

/**
 * GameController manages the interactions between the GameView and the game logic.
 * It handles user inputs, updates the game state, and coordinates animations and audio feedback.
 */
public class GameController implements MenuCallback {

    // Dependencies
    private GameView gameView;
    private GameService gameService;
    private GameState gameState;
    private AudioService audioService;
    private AnimationService animationService;
    private WelcomeView welcomeView;

    // Constructor
    public GameController(GameView gameView, WelcomeView welcomeView) {
        this.gameView = gameView;
        this.welcomeView = welcomeView;

        // Initialize game state and services
        this.gameState = new GameState();
        this.gameService = new GameService(gameState);

        // Initialize audio and animation services
        this.audioService = new AudioService();
        this.animationService = new AnimationService(audioService, gameService, gameView, gameState);

        // Set up event handlers and menus
        setUpEventHandlers();
        setUpKeyHandler();
        setUpMenus();
        gameView.setupMenus(this);

        // Initialize the game view
        initializeView();
    }

    /**
     * Initializes the game view based on the current game state.
     */
    private void initializeView(){
        // Disable all buttons when game start
        if (gameState.getGameMode() == null && gameState.getGameDrawings() == null) {
            gameView.disableAllButtons();
        }

        // Show game rules window and mode notification on start
        animationService.showGameRules();
        animationService.showModeNotification();
    }

    /**
     * Sets up event handlers for the game view components.
     */
    private void setUpEventHandlers() {
        // Set up handlers for all number buttons
        for (NumberButton btn : gameView.getNumberButtons()) {
            btn.setOnAction(e -> handleNumberButtonClick(btn));
        }

        // Set up handlers for control buttons
        gameView.getPlayButton().setOnAction(e -> handleStartGame());
        gameView.getRandomButton().setOnAction(e -> handleRandomSelection());
        gameView.getClearButton().setOnAction(e -> handleClearSelection());
        gameView.getHistoryButton().setOnAction(e -> handleShowHistory());
        gameView.getRestartButton().setOnAction(e -> handleRestart());

        // Handle window close request
        gameView.getStage().setOnCloseRequest( e -> {
            e.consume();
            handleBack();
        });
    }

    /**
     * Sets up key handlers for keyboard shortcuts.
     */
    private void setUpKeyHandler() {
        gameView.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                // Cheat mode activation
                case C:
                    if (event.isControlDown() && event.isShiftDown()) {
                        gameState.setCheatMode(true);
                    }
                    break;
                // Quick actions
                case ENTER:
                    if (gameState.getGameMode() != null && !gameView.getPlayButton().isDisable()) {
                        handleStartGame();
                    }
                    break;
                case Q:
                    if (gameState.getGameMode() != null && !gameView.getPlayButton().isDisable()) {
                        handleClearSelection();
                    }
                    break;
                case R:
                    if (gameState.getGameMode() != null) {
                        handleRandomSelection();
                    }
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * Sets up the game mode and drawings selection menus.
     */
    private void setUpMenus() {
        // Set up game mode selection menu
        MenuItem oneSpot = MenuFactory.createMenuItem("1 Spot", () -> handleModeChange(GameMode.ONE_SPOT));
        MenuItem fourSpot = MenuFactory.createMenuItem("4 Spot", () -> handleModeChange(GameMode.FOUR_SPOT));
        MenuItem eightSpot = MenuFactory.createMenuItem("8 Spot", () -> handleModeChange(GameMode.EIGHT_SPOT));
        MenuItem tenSpot = MenuFactory.createMenuItem("10 Spot", () -> handleModeChange(GameMode.TEN_SPOT));
        gameView.getModeSelector().getItems().addAll(oneSpot, fourSpot, eightSpot, tenSpot);
        gameView.getModeSelector().setOnMouseClicked(e -> animationService.hideModeNotification());


        // Set up game drawings selection menu
        MenuItem oneDrawings = MenuFactory.createMenuItem("1 round", () -> handleDrawingsChange(GameDrawings.ONE_DRAWING));
        MenuItem twoDrawings = MenuFactory.createMenuItem("2 round", () -> handleDrawingsChange(GameDrawings.TWO_DRAWING));
        MenuItem threeDrawings = MenuFactory.createMenuItem("3 round", () -> handleDrawingsChange(GameDrawings.THREE_DRAWING));
        MenuItem fourDrawings = MenuFactory.createMenuItem("4 round", () -> handleDrawingsChange(GameDrawings.FOUR_DRAWING));
        gameView.getDrawingsSelector().getItems().addAll(oneDrawings, twoDrawings, threeDrawings, fourDrawings);
        gameView.getDrawingsSelector().setOnMouseClicked(e -> animationService.hideDrawingsNotification());

    }

    /**
     * Handles the start game action.
     */
    private void handleStartGame() {
        // Check if the game is ready to play, if not, show a message and return
        if(!gameService.isReadyToPlay()){
            gameView.updateStatusLabel("Please select " + gameService.getMaxSelections() + " numbers to play.", ThemeStyles.INFO_LABEL_STATUS_DANGER);
            return;
        }

        // Start the game
        animationService.startNextRound();
    }

    /**
     * Handles the change of game drawings.
     * @param gameDrawings - The selected game drawings option.
     */
    private void handleDrawingsChange(GameDrawings gameDrawings) {
        // Play sound and update game state
        audioService.playSound(AudioService.MODE_SOUND);
        gameState.setGameDrawings(gameDrawings);
        gameView.updateDrawingsSelectorText("Draws : " + gameDrawings.getMaxDrawings());
        gameView.updateStatusLabel("Drawings changed to " + gameDrawings.getDisplayName() + "(s).", ThemeStyles.INFO_LABEL_STATUS_POSITIVE);

        // Enable all buttons if game mode is already selected
        if (gameState.getGameMode() != null) {
            gameView.enableAllButtons();
        }
    }

    /**
     * Handles the change of game mode.
     * @param gameMode - The selected game mode option.
     */
    private void handleModeChange(GameMode gameMode) {
        // Play sound and update game state
        audioService.playSound(AudioService.MODE_SOUND);
        gameState.setGameMode(gameMode);
        gameView.updateModeSelectorText("Spots : " + gameMode.getMaxSpots());
        gameView.updateStatusLabel("Mode changed to " + gameMode.getDisplayName() + "(s).", ThemeStyles.INFO_LABEL_STATUS_POSITIVE);

        // Update prize match panel and show odds window
        animationService.updatePrizeMatchPanel(gameMode);
        InfoWindow.showOdds(gameMode, true, gameView.getRoot());

        // Enable drawings selector and show notification if drawings not selected
        // else reset the game view for new mode
        gameView.getDrawingsSelector().setDisable(false);
        if(gameState.getGameDrawings() == null){
            animationService.showDrawingsNotification();
        }else{
            animationService.resetNumberButtons();
            animationService.resetPrizeHighlights();
            gameView.getPlayButton().setDisable(true);
        }
    }

    /**
     * Handles the click event of a number button.
     * @param btn - The number button that was clicked.
     */
    private void handleNumberButtonClick(NumberButton btn) {
        // Ignore clicks on non-selectable buttons
        if (!btn.isSelectable()) {
            return;
        }
        // Play click sound and reset animations
        audioService.playSound(AudioService.CLICK_SOUND);
        animationService.resetForNewRound();
        animationService.resetPrizeHighlights();

        // Handle selection or deselection based on current state
        if (btn.isSelected()) {
            handleNumberDeselection(btn);
        } else {
            handleNumberSelection(btn);
        }
    }

    /**
     * Handles the deselection of a number button.
     * @param btn - The number button to be deselected.
     */
    private void handleNumberDeselection(NumberButton btn) {
        // If the number is not in the selected list, return
        if (!gameService.deselectNumber(btn.getNumber())) {
            return;
        }
        // Deselect the button and update status
        btn.deselect();
        // If all numbers are deselected, update status and disable play button
        if (gameState.getSelectedCount() == 0) {
            gameView.updateStatusLabel( "Please select your lucky numbers.", ThemeStyles.INFO_LABEL_STATUS_NEUTRAL);
            gameView.getPlayButton().setDisable(true);
        }
    }

    /**
     * Handles the selection of a number button.
     * @param btn - The number button to be selected.
     */
    private void handleNumberSelection(NumberButton btn) {
        // If the number cannot be selected (e.g., max selections reached), show message and return
        if (!gameService.selectNumber(btn.getNumber())) {
            if (gameState.getSelectedCount() >= gameService.getMaxSelections()) {
                gameView.updateStatusLabel("You can only select up to " + gameService.getMaxSelections() + " numbers.", ThemeStyles.INFO_LABEL_STATUS_DANGER);
            }
            return;
        }

        // Select the button and update status
        btn.select();
        gameView.updateStatusLabel( "You have selected " + gameState.getSelectedCount() + " number(s).", ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
        gameView.getPlayButton().setDisable(false);
    }

    /**
     * Handles the clear selection action.
     */
    private void handleClearSelection() {
        // If play button is disabled, nothing to clear
        if (gameView.getPlayButton().isDisabled()) {
            return;
        }

        // Reset animations, play sound, clear selections, and update status
        animationService.resetNumberButtons();
        animationService.resetPrizeHighlights();
        audioService.playSound(AudioService.CLEAR_SOUND);
        gameService.clearSelections();
        gameView.updateStatusLabel("Selections cleared.", ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
        gameView.getPlayButton().setDisable(true);
    }

    /**
     * Handles the back action to return to the welcome view.
     */
    private void handleBack() {
        gameView.hide();
        if (welcomeView != null) {
            welcomeView.show();
        }
    }

    /*
     * Handles the random selection action.
     */
    private void handleRandomSelection() {
        // Reset animations, play sound, randomly select numbers, and update status
        animationService.resetNumberButtons();
        animationService.resetPrizeHighlights();
        audioService.playSound(AudioService.CLICK_SOUND);
        gameService.randomSelectNumbersForUser();
        for (int num : gameState.getSelectedNumbers()) {
            gameView.getNumberButtons()[num - 1].select();
        }
        gameView.getPlayButton().setDisable(false);
        gameView.updateStatusLabel("Randomly selected " + gameState.getSelectedCount() + " number(s).", ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
    }

    /**
     * Handles the show history action.
     */
    private void handleShowHistory() {
        // Generate and display game history window
        String histories = gameState.getGameHistories().isEmpty()
                ? "No game history available."
                : gameService.generateHistoryText();
        InfoWindow.showHistory(histories, gameView.getRoot());
    }

    /**
     * Handles the restart game action.
     */
    private void handleRestart() {
        animationService.resetGame();
    }

    /**
     * MenuCallback implementations for show rules
     */
    @Override
    public void onShowRules() {
        InfoWindow.showRules(gameView.getRoot());
    }

    /**
     * MenuCallback implementations for show odds
     */
    @Override
    public void onShowOdds() {
        GameMode currentMode = gameState.getGameMode();
        InfoWindow.showOdds(currentMode != null ? currentMode : GameMode.ONE_SPOT, gameView.getRoot());
    }

    /**
     * MenuCallback implementations for exit game
     */
    @Override
    public void onExitGame() {
        gameView.getStage().close();
    }

    /**
     * MenuCallback implementations for reset preferences
     */
    @Override
    public void onResetPreferences() {
        InfoWindow.resetAllPreferences();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Settings Reset");
        alert.setContentText("All preferences have been reset to default.");
        alert.showAndWait();
    }
}
