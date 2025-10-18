package Service;

import Model.GameHistory;
import Model.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * GameService class to manage game logic and state.
 */
public class GameService {

    private GameState gameState;
    private Random rand;
    private static final int RANDOM_SYSTEM_SELECTION_COUNT = 20;

    // Constructor to initialize GameService with GameState
    public GameService(GameState gameState) {
        this.gameState = gameState;
        this.rand = new Random();
    }

    /**
     * Get the maximum number of selections allowed based on the game mode.
     * @return Maximum number of selections.
     */
    public int getMaxSelections() {
        return gameState.getGameMode().getMaxSpots();
    }

    /**
     * Get the maximum number of drawings allowed based on the game drawing settings.
     * @return Maximum number of drawings.
     */
    public int getMaxDrawings() {
        return gameState.getGameDrawings().getMaxDrawings();
    }

    /**
     * Select a number for the user.
     * @param number - The number to select.
     * @return True if the number was successfully selected, false otherwise.
     */
    public boolean selectNumber(int number) {
        if (number > 0 && number < 81 && gameState.getSelectedCount() < getMaxSelections() && !gameState.getSelectedNumbers().contains(number)) {
            gameState.getSelectedNumbers().add(number);
            return true;
        }
        return false;
    }

    /**
     * Deselect a number for the user.
     * @param number - The number to deselect.
     * @return True if the number was successfully deselected, false otherwise.
     */
    public boolean deselectNumber(int number) {
        return gameState.getSelectedNumbers().remove(Integer.valueOf(number));
    }

    /**
     * Clear all selected numbers.
     */
    public void clearSelections() {
        gameState.getSelectedNumbers().clear();
    }

    /**
     * Check if the game is ready to play.
     * @return True if the game is ready to play, false otherwise.
     */
    public boolean isReadyToPlay() {
        return gameState.getGameMode() != null && gameState.getGameDrawings() != null &&  gameState.getSelectedCount() == getMaxSelections();
    }

    /**
     * Randomly select numbers for the user.
     */
    public void randomSelectNumbersForUser() {
        List<Integer> selected = new ArrayList<>();
        while (selected.size() < getMaxSelections()) {
            int num = rand.nextInt(80) + 1; // Random number between 1 and 80
            if (!selected.contains(num)) {
                selected.add(num);
            }
        }
        gameState.setSelectedNumbers(selected);
    }

    /**
     * Randomly select numbers for the system.
     * @return List of randomly selected numbers for the system.
     */
    public List<Integer> randomSelectNumbersForSystem() {
        // If cheat mode is on, include user's selected numbers
        List<Integer> systemSelectedNumbers = gameState.isCheatMode() ? new ArrayList<>(gameState.getSelectedNumbers()) : new ArrayList<>();
        // Fill the rest with random numbers
        while (systemSelectedNumbers.size() < RANDOM_SYSTEM_SELECTION_COUNT) {
            int num = rand.nextInt(80) + 1;
            if (!systemSelectedNumbers.contains(num)) {
                systemSelectedNumbers.add(num);
            }
        }
        return systemSelectedNumbers;
    }

    /**
     * Get the list of matched numbers between user's selections and system's selections.
     * @param systemNumbers - The system's selected numbers.
     * @return List of matched numbers.
     */
    public List<Integer> getMatchedNumbers(List<Integer> systemNumbers) {
        List<Integer> matchedNumbers = new ArrayList<>();
        for (Integer num : systemNumbers) {
            if (gameState.getSelectedNumbers().contains(num)) {
                matchedNumbers.add(num);
            }
        }
        return matchedNumbers;
    }

    /**
     * Calculate the prize based on the number of matches.
     * @param matches - The number of matched numbers.
     * @return The prize amount.
     */
    public int calculatePrize(int matches) {
        return Model.PrizeTable.getPrizeForHits(getMaxSelections(), matches);
    }

    /**
     * Check if the player has hit the jackpot.
     * @param matches - The number of matched numbers.
     * @return True if the player hit the jackpot, false otherwise.
     */
    public boolean isWin(int matches){
        return Model.PrizeTable.isValidPrize(getMaxSelections(), matches);
    }

    /**
     * Generate a textual representation of the game history.
     * @return String representation of game history.
     */
    public String generateHistoryText() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-15s %-15s %-15s\n", "ID","Total Rounds", "Total Matches", "Total Winnings"));
        sb.append("-----------------------------------------------------\n");
        List<GameHistory> gameHistories = gameState.getGameHistories();
        for (int i = 0; i < gameHistories.size(); i++) {
            GameHistory history = gameHistories.get(i);
            sb.append(String.format("%-25d %-25d %-30d $%-14d\n", i + 1, history.getTotalRounds(), history.getTotalMatchedCount(), history.getTotalPrize()));
        }
        return sb.toString();
    }

    /**
     * Reset the game state for a new game.
     */
    public void resetForNewGame(){
        gameState.resetCurrentRound();
        gameState.resetTotalWinnings();
        gameState.resetTotalMatchCount();
        gameState.resetCurrentRound();
        gameState.setCheatMode(false);
    }

}
