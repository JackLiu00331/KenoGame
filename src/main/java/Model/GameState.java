package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameState {
    // Game configuration fields
    private GameMode gameMode;
    private GameDrawings gameDrawings;
    private List<Integer> selectedNumbers;
    private List<GameHistory> gameHistories;
    private boolean cheatMode = false;

    // Tracking fields
    private Integer currentMatchCount = 0;
    private Integer currentRound;
    private Integer totalMatchCount = 0;
    private Integer totalWinnings = 0;

    public GameState(){
        this.selectedNumbers = new ArrayList<>();
        this.gameHistories = new ArrayList<>();
        this.cheatMode = false;
        this.currentRound = 0;
    }

    public GameMode getGameMode(){
        return gameMode;
    }

    public void setGameMode(GameMode gameMode){
        this.gameMode = gameMode;
        this.selectedNumbers.clear();
    }

    public void setGameDrawings(GameDrawings drawings) {
        this.gameDrawings = drawings;
    }
    public GameDrawings getGameDrawings() {
        return gameDrawings;
    }

    public List<Integer> getSelectedNumbers() {
        return selectedNumbers;
    }

    public void setSelectedNumbers(List<Integer> selectedNumbers) {
        this.selectedNumbers = selectedNumbers;
    }

    public int getSelectedCount() {
        return selectedNumbers.size();
    }


    public List<GameHistory> getGameHistories() {
        return gameHistories;
    }

    public void addGameHistory(GameHistory history) {
        this.gameHistories.add(history);
    }

    public boolean isCheatMode() {
        return cheatMode;
    }

    public void setCheatMode(boolean cheatMode) {
        this.cheatMode = cheatMode;
    }

    public Integer getCurrentMatchCount() {
        return currentMatchCount;
    }

    public void incrementCurrentMatchCount() {
        this.currentMatchCount += 1;
    }

    public void resetCurrentMatchCount() {
        this.currentMatchCount = 0;
    }

    public Integer getCurrentRound() {
        return currentRound;
    }

    public void incrementRound() {
        this.currentRound += 1;
    }

    public void resetCurrentRound() {
        this.currentRound = 0;
    }

    public Integer getTotalMatchCount() {
        return totalMatchCount;
    }

    public void addToTotalMatchCount(int matches) {
            this.totalMatchCount += matches;
    }

    public void resetTotalMatchCount() {
        this.totalMatchCount = 0;
    }

    public Integer getTotalWinnings() {
        return totalWinnings;
    }

    public void addToTotalWinnings(int amount) {
        this.totalWinnings += amount;
    }

    public void resetTotalWinnings() {
        this.totalWinnings = 0;
    }
}
