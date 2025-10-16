package Service;

import Model.GameDrawings;
import Model.GameHistory;
import Model.GameMode;
import Model.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameService {

    private GameState gameState;
    private Random rand;
    private static final int RANDOM_SYSTEM_SELECTION_COUNT = 20;

    public GameService(GameState gameState) {
        this.gameState = gameState;
        this.rand = new Random();
    }

    public int getMaxSelections() {
        return gameState.getGameMode().getMaxSpots();
    }

    public int getMaxDrawings() {
        return gameState.getGameDrawings().getMaxDrawings();
    }


    public boolean selectNumber(int number) {
        if (number > 0 && number < 81 && gameState.getSelectedCount() < getMaxSelections() && !gameState.getSelectedNumbers().contains(number)) {
            gameState.getSelectedNumbers().add(number);
            return true;
        }
        return false;
    }

    public boolean deselectNumber(int number) {
        return gameState.getSelectedNumbers().remove(Integer.valueOf(number));
    }

    public void clearSelections() {
        gameState.getSelectedNumbers().clear();
    }

    public boolean isReadyToPlay() {
        return gameState.getGameMode() != null && gameState.getGameDrawings() != null &&  gameState.getSelectedCount() == getMaxSelections();
    }


    public List<Integer> randomSelectNumbersForUser() {
        List<Integer> selected = new ArrayList<>();
        while (selected.size() < getMaxSelections()) {
            int num = rand.nextInt(80) + 1; // Random number between 1 and 80
            if (!selected.contains(num)) {
                selected.add(num);
            }
        }
        gameState.setSelectedNumbers(selected);
        return selected;
    }

    public List<Integer> randomSelectNumbersForSystem() {
        List<Integer> systemSelectedNumbers = gameState.isCheatMode() ? new ArrayList<>(gameState.getSelectedNumbers()) : new ArrayList<>();
        while (systemSelectedNumbers.size() < RANDOM_SYSTEM_SELECTION_COUNT) { // System always selects 20 numbers
            int num = rand.nextInt(80) + 1; // Random number between 1 and 80
            if (!systemSelectedNumbers.contains(num)) {
                systemSelectedNumbers.add(num);
            }
        }
        return systemSelectedNumbers;
    }

    public int calculateMatches(List<Integer> systemNumbers) {
        int matches = 0;
        for (Integer num : systemNumbers) {
            if (gameState.getSelectedNumbers().contains(num)) {
                matches++;
            }
        }
        return matches;
    }

    public List<Integer> getMatchedNumbers(List<Integer> systemNumbers) {
        List<Integer> matchedNumbers = new ArrayList<>();
        for (Integer num : systemNumbers) {
            if (gameState.getSelectedNumbers().contains(num)) {
                matchedNumbers.add(num);
            }
        }
        return matchedNumbers;
    }

    public int calculatePrize(int matches) {
        return Model.PrizeTable.getPrizeForHits(getMaxSelections(), matches);
    }

    public boolean isJackPot(int matches){
        return Model.PrizeTable.isValidPrize(getMaxSelections(), matches);
    }

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

    public void resetForNewGame(){
        gameState.resetCurrentRound();
        gameState.resetTotalWinnings();
        gameState.resetTotalMatchCount();
        gameState.resetCurrentRound();
        gameState.setCheatMode(false);
    }

}
