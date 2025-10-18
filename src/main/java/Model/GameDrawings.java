package Model;

/**
 * Enum representing the number of drawings in a game.
 */
public enum GameDrawings {
    // Enum constants with associated max drawings and display names
    ONE_DRAWING(1, "1 round"),
    TWO_DRAWING(2, "2 round"),
    THREE_DRAWING(3, "3 round"),
    FOUR_DRAWING(4, "4 round");

    private final int maxDrawings;
    private final String displayName;

    // Constructor for the enum constants
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
