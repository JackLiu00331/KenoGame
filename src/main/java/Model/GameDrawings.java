package Model;

public enum GameDrawings {
    ONE_DRAWING(1, "1 round"),
    TWO_DRAWING(2, "2 round"),
    THREE_DRAWING(3, "3 round"),
    FOUR_DRAWING(4, "4 round");

    private final int maxDrawings;
    private final String displayName;

    GameDrawings(int maxDrawings, String displayName) {
        this.maxDrawings = maxDrawings;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public int getMaxDrawings() {
        return maxDrawings;
    }

    public String getDisplayName() {
        return displayName;
    }
}
