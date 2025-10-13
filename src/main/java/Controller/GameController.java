package Controller;

import Model.GameMode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GameController {


    private GameMode gameMode;
    private List<Integer> selectedNumbers;

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
}
