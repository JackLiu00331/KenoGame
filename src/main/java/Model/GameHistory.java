package Model;

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

    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }

    public int getTotalMatchedCount() {
        return totalMatchedCount;
    }

    public void setTotalMatchedCount(int totalMatchedCount) {
        this.totalMatchedCount = totalMatchedCount;
    }

    public int getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(int totalPrize) {
        this.totalPrize = totalPrize;
    }
}
