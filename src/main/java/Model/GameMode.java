package Model;

/**
 * Enum representing different game modes with their maximum spots and display names.
 */
public enum GameMode {
    // Define game modes with max spots and display names
    ONE_SPOT(1, "1 Spot"),
    FOUR_SPOT(4, "4 Spot"),
    EIGHT_SPOT(8, "8 Spot"),
    TEN_SPOT(10, "10 Spot");

    private final int maxSpots;
    private final String displayName;

    // Constructor for GameMode enum
    GameMode(int maxSpots, String displayName) {
        this.maxSpots = maxSpots;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public int getMaxSpots() {
        return maxSpots;
    }

    public String getDisplayName() {
        return displayName;
    }
}
