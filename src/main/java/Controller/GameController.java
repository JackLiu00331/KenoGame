package Controller;

import Model.GameMode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {


    private GameMode gameMode;
    private List<Integer> selectedNumbers;
    private Random random;

    public GameController() {
        this.gameMode = null;
        this.selectedNumbers = new ArrayList<>();
    }

    public void setGameMode(GameMode mode) {
        this.gameMode = mode;
        this.selectedNumbers.clear(); // Clear selections when mode changes
    }

    public int getMaxSelections() {
        return gameMode.getMaxSpots();
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

    public List<Integer> getSelectedNumbers() {
        return selectedNumbers;
    }

    public void randomSelectNumbersForUser() {
        selectedNumbers.clear();
        Random rand = new Random();
        while (selectedNumbers.size() < getMaxSelections()) {
            int num = rand.nextInt(80) + 1; // Random number between 1 and 80
            if (!selectedNumbers.contains(num)) {
                selectedNumbers.add(num);
            }
        }
    }
}
