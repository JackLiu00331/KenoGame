package Model;

public enum GameMode {
    ONE_SPOT(1, "1 Spot"),
    FOUR_SPOT(4, "4 Spot"),
    EIGHT_SPOT(8, "8 Spot"),
    TEN_SPOT(10, "10 Spot");

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
