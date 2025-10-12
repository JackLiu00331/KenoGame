package Utils;

public enum GameMode {
    ONE_SPOT(1, "1 Spot Game"),
    FOUR_SPOT(4, "4 Spot Game"),
    EIGHT_SPOT(8, "8 Spot Game"),
    TEN_SPOT(10, "10 Spot Game");

    private final int maxSpots;
    private final String displayName;

    GameMode(int maxSpots, String displayName) {
        this.maxSpots = maxSpots;
        this.displayName = displayName;
    }

    public String getDescription() {
        return "Select up to" + maxSpots + " numbers";
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
