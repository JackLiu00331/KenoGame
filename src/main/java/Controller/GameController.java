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

public class GameController implements MenuCallback {

    private GameView gameView;
    private GameService gameService;
    private GameState gameState;
    private AudioService audioService;
    private AnimationService animationService;
    private WelcomeView welcomeView;

    public GameController(GameView gameView, WelcomeView welcomeView) {
        this.gameView = gameView;
        this.welcomeView = welcomeView;

        this.gameState = new GameState();
        this.gameService = new GameService(gameState);
        this.audioService = new AudioService();
        this.animationService = new AnimationService(audioService, gameService, gameView, gameState);

        setUpEventHandlers();
        setUpKeyHandler();
        setUpMenus();
        gameView.setupMenus(this);

        initializeView();
    }



    private void initializeView(){
        if (gameState.getGameMode() == null && gameState.getGameDrawings() == null) {
            gameView.disableAllButtons();
        }
        animationService.showGameRules();
        animationService.showModeNotification();
    }

    private void setUpEventHandlers() {
        for (NumberButton btn : gameView.getNumberButtons()) {
            btn.setOnAction(e -> handleNumberButtonClick(btn));
        }

        gameView.getPlayButton().setOnAction(e -> handleStartGame());
        gameView.getRandomButton().setOnAction(e -> handleRandomSelection());
        gameView.getClearButton().setOnAction(e -> handleClearSelection());
        gameView.getHistoryButton().setOnAction(e -> handleShowHistory());
        gameView.getRestartButton().setOnAction(e -> handleRestart());

        gameView.getStage().setOnCloseRequest( e -> {
            e.consume();
            handleBack();
        });
    }

    private void setUpKeyHandler() {
        gameView.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case C:
                    if (event.isControlDown() && event.isShiftDown()) {
                        gameState.setCheatMode(true);
                    }
                    break;
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

    private void setUpMenus() {
        MenuItem oneSpot = MenuFactory.createMenuItem("1 Spot", () -> handleModeChange(GameMode.ONE_SPOT));
        MenuItem fourSpot = MenuFactory.createMenuItem("4 Spot", () -> handleModeChange(GameMode.FOUR_SPOT));
        MenuItem eightSpot = MenuFactory.createMenuItem("8 Spot", () -> handleModeChange(GameMode.EIGHT_SPOT));
        MenuItem tenSpot = MenuFactory.createMenuItem("10 Spot", () -> handleModeChange(GameMode.TEN_SPOT));
        gameView.getModeSelector().getItems().addAll(oneSpot, fourSpot, eightSpot, tenSpot);
        gameView.getModeSelector().setOnMouseClicked(e -> animationService.hideModeNotification());


        MenuItem oneDrawings = MenuFactory.createMenuItem("1 round", () -> handleDrawingsChange(GameDrawings.ONE_DRAWING));
        MenuItem twoDrawings = MenuFactory.createMenuItem("2 round", () -> handleDrawingsChange(GameDrawings.TWO_DRAWING));
        MenuItem threeDrawings = MenuFactory.createMenuItem("3 round", () -> handleDrawingsChange(GameDrawings.THREE_DRAWING));
        MenuItem fourDrawings = MenuFactory.createMenuItem("4 round", () -> handleDrawingsChange(GameDrawings.FOUR_DRAWING));
        gameView.getDrawingsSelector().getItems().addAll(oneDrawings, twoDrawings, threeDrawings, fourDrawings);
        gameView.getDrawingsSelector().setOnMouseClicked(e -> animationService.hideDrawingsNotification());

    }

    private void handleStartGame() {
        if(!gameService.isReadyToPlay()){
            gameView.updateStatusLabel("Please select " + gameService.getMaxSelections() + " numbers to play.", ThemeStyles.INFO_LABEL_STATUS_DANGER);
            return;
        }
        animationService.startNextRound();
    }


    private void handleDrawingsChange(GameDrawings gameDrawings) {
        audioService.playSound(AudioService.MODE_SOUND);
        gameState.setGameDrawings(gameDrawings);
        gameView.updateDrawingsSelectorText("Draws : " + gameDrawings.getMaxDrawings());
        gameView.updateStatusLabel("Drawings changed to " + gameDrawings.getDisplayName() + "(s).", ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
        if (gameState.getGameMode() != null) {
            gameView.enableAllButtons();
        }
    }

    private void handleModeChange(GameMode gameMode) {
        audioService.playSound(AudioService.MODE_SOUND);
        gameState.setGameMode(gameMode);
        gameView.updateModeSelectorText("Spots : " + gameMode.getMaxSpots());
        gameView.updateStatusLabel("Mode changed to " + gameMode.getDisplayName() + "(s).", ThemeStyles.INFO_LABEL_STATUS_POSITIVE);

        animationService.updatePrizeMatchPanel(gameMode);
        InfoWindow.showOdds(gameMode, true);

        gameView.getDrawingsSelector().setDisable(false);
        if(gameState.getGameDrawings() == null){
            animationService.showDrawingsNotification();
        }else{
            animationService.resetNumberButtons();
            animationService.resetPrizeHighlights();
            gameView.getPlayButton().setDisable(true);
        }
    }

    private void handleNumberButtonClick(NumberButton btn) {

        if (!btn.isSelectable()) {
            return;
        }
        audioService.playSound(AudioService.CLICK_SOUND);
        animationService.resetForNewRound();
        animationService.resetPrizeHighlights();
        if (btn.isSelected()) {
            handleNumberDeselection(btn);
        } else {
            handleNumberSelection(btn);
        }
    }

    private void handleNumberDeselection(NumberButton btn) {
        if (!gameService.deselectNumber(btn.getNumber())) {
            return;
        }
        btn.deselect();
        if (gameState.getSelectedCount() == 0) {
            gameView.updateStatusLabel( "Please select your lucky numbers.", ThemeStyles.INFO_LABEL_STATUS_NEUTRAL);
            gameView.getPlayButton().setDisable(true);
        }
    }

    private void handleNumberSelection(NumberButton btn) {
        if (!gameService.selectNumber(btn.getNumber())) {
            if (gameState.getSelectedCount() >= gameService.getMaxSelections()) {
                gameView.updateStatusLabel("You can only select up to " + gameService.getMaxSelections() + " numbers.", ThemeStyles.INFO_LABEL_STATUS_DANGER);
            }
            return;
        }

        btn.select();
        gameView.updateStatusLabel( "You have selected " + gameState.getSelectedCount() + " number(s).", ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
        gameView.getPlayButton().setDisable(false);
    }

    private void handleClearSelection() {
        if (gameView.getPlayButton().isDisabled()) {
            return;
        }
        animationService.resetNumberButtons();
        animationService.resetPrizeHighlights();
        audioService.playSound(AudioService.CLEAR_SOUND);
        gameService.clearSelections();
        gameView.updateStatusLabel("Selections cleared.", ThemeStyles.INFO_LABEL_STATUS_POSITIVE);
        gameView.getPlayButton().setDisable(true);
    }


    private void handleBack() {
        gameView.hide();
        if (welcomeView != null) {
            welcomeView.show();
        }
    }

    private void handleRandomSelection() {
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

    private void handleShowHistory() {
        String histories = gameState.getGameHistories().isEmpty()
                ? "No game history available."
                : gameService.generateHistoryText();
        InfoWindow.showHistory(histories);
    }

    private void handleRestart() {
        animationService.resetGame();
    }

    @Override
    public void onShowRules() {
        InfoWindow.showRules();
    }

    @Override
    public void onShowOdds() {
        GameMode currentMode = gameState.getGameMode();
        InfoWindow.showOdds(currentMode != null ? currentMode : GameMode.ONE_SPOT);
    }

    @Override
    public void onExitGame() {
        gameView.getStage().close();
    }

    @Override
    public void onResetPreferences() {
        InfoWindow.resetAllPreferences();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Settings Reset");
        alert.setContentText("All preferences have been reset to default.");
        alert.showAndWait();
    }
}
