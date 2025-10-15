package Controller;

import Model.GameDrawings;
import Model.GameHistory;
import Model.GameMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {


    private GameMode gameMode;
    private GameDrawings gameDrawings;
    private List<Integer> selectedNumbers;
    private List<GameHistory> gameHistories;
    private Random rand;
    private static final int RANDOM_SYSTEM_SELECTION_COUNT = 20;

    public GameController() {
        this.gameMode = null;
        this.selectedNumbers = new ArrayList<>();
        this.gameHistories = new ArrayList<>();
    }

    public void setGameMode(GameMode mode) {
        this.gameMode = mode;
        this.selectedNumbers.clear(); // Clear selections when mode changes
    }

    public void setGameDrawings(GameDrawings drawings) {
        this.gameDrawings = drawings;
    }

    public int getMaxSelections() {
        return gameMode.getMaxSpots();
    }

    public int getMaxDrawings() {
        return gameDrawings.getMaxDrawings();
    }

    public boolean selectNumber(int number) {
        if (number > 0 && number < 81 && selectedNumbers.size() < getMaxSelections() && !selectedNumbers.contains(number)) {
            selectedNumbers.add(number);
            return true;
        }
        return false;
    }

    public boolean deselectNumber(int number) {
        return selectedNumbers.remove(Integer.valueOf(number));
    }

    public int getSelectedCount() {
        return selectedNumbers.size();
    }

    public boolean canSelectMore() {
        return selectedNumbers.size() < getMaxSelections();
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public GameDrawings getGameDrawings() {
        return gameDrawings;
    }

    public boolean isReadyToPlay() {
        return gameMode != null && gameDrawings != null && selectedNumbers.size() == getMaxSelections();
    }

    public List<Integer> getSelectedNumbers() {
        return selectedNumbers;
    }

    public void randomSelectNumbersForUser() {
        selectedNumbers.clear();
        rand = new Random();
        while (selectedNumbers.size() < getMaxSelections()) {
            int num = rand.nextInt(80) + 1; // Random number between 1 and 80
            if (!selectedNumbers.contains(num)) {
                selectedNumbers.add(num);
            }
        }
    }

    public List<Integer> randomSelectNumbersForSystem(boolean cheatMode) {
        rand = new Random();
        List<Integer> systemSelectedNumbers = cheatMode ? new ArrayList<>(getSelectedNumbers()) : new ArrayList<>();
        while (systemSelectedNumbers.size() < RANDOM_SYSTEM_SELECTION_COUNT) { // System always selects 20 numbers
            int num = rand.nextInt(80) + 1; // Random number between 1 and 80
            if (!systemSelectedNumbers.contains(num)) {
                systemSelectedNumbers.add(num);
            }
        }
        return systemSelectedNumbers;
    }

    public void addGameHistory(GameHistory history) {
        gameHistories.add(history);
    }


    public List<GameHistory> getGameHistories() {
        return gameHistories;
    }

    public String generateHistoryText() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-15s %-15s %-15s\n", "ID","Total Rounds", "Total Matches", "Total Winnings"));
        sb.append("-----------------------------------------------------\n");
        for (int i = 0; i < gameHistories.size(); i++) {
            GameHistory history = gameHistories.get(i);
            sb.append(String.format("%-25d %-25d %-30d $%-14d\n", i + 1, history.getTotalRounds(), history.getTotalMatchedCount(), history.getTotalPrize()));
        }
        return sb.toString();
    }

}
