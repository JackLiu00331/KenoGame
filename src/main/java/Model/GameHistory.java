package Model;

/**
 * GameHistory class to store the history of the game including total rounds played,
 * total matched count, and total prize won.
 */
public class GameHistory {
    private int totalRounds;
    private int totalMatchedCount;
    private int totalPrize;

    public GameHistory(int totalRounds, int totalMatchedCount, int totalPrize) {
        this.totalRounds = totalRounds;
        this.totalMatchedCount = totalMatchedCount;
        this.totalPrize = totalPrize;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public int getTotalMatchedCount() {
        return totalMatchedCount;
    }

    public int getTotalPrize() {
        return totalPrize;
    }

}
