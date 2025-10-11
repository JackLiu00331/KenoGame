package Utils;

/**
 * Theme style utility class
 * Provides overall theme styles for the game, including background, titles, etc.
 */
public class ThemeStyles {

    // ============ Background Styles ============

    /**
     * Welcome to Stage background - dark casino style with radial gradient
     * Brighter in the center, darker around, creating a spotlight effect
     */
    public static final String WELCOME_BACKGROUND =
            "-fx-background-color: " +
                    "radial-gradient(focus-angle 0deg, focus-distance 0%, " +
                    "center 50% 50%, radius 70%, " +
                    "#0E7A2E 0%, #1A1A1A 100%);";

    /**
     * Game Stage background - classic casino green gradient
     * From dark green to lighter green, simulating a table feel
     * Subtle gradient, does not affect gameplay
     */
    public static final String GAME_BACKGROUND =
            "-fx-background-color: " +
                    "linear-gradient(to bottom, " +
                    "#0B6623 0%, " +
                    "#0E7A2E 50%, " +
                    "#0B6623 100%);";

    /**
     * Alternative: softer casino background (dark gray-green tone)
     * Use this if pure green is too bright
     */
    public static final String GAME_BACKGROUND_SUBTLE =
            "-fx-background-color: " +
                    "linear-gradient(to bottom, " +
                    "#1C3A29 0%, " +
                    "#234D35 50%, " +
                    "#1C3A29 100%);";

    /**
     * Luxury background - velvet texture (dark red-black gradient)
     * Suitable for high-end casino style
     */
    public static final String LUXURY_BACKGROUND =
            "-fx-background-color: " +
                    "linear-gradient(to bottom right, " +
                    "#1A0505 0%, " +
                    "#3D0A0A 30%, " +
                    "#8B0000 50%, " +
                    "#3D0A0A 70%, " +
                    "#1A0505 100%);";

    /**
     * Card/Panel background - semi-transparent dark with slight gloss
     */
    public static final String PANEL_BACKGROUND =
            "-fx-background-color: rgba(0, 0, 0, 0.4); " +
                    "-fx-background-radius: 15; " +
                    "-fx-border-color: rgba(255, 215, 0, 0.3); " +
                    "-fx-border-width: 1; " +
                    "-fx-border-radius: 15; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 20, 0, 0, 5);";

    // ============ Title Styles ============

    /**
     * Main game title - golden luxury style
     * Large font + gold gradient + glow effect
     */
    public static final String GAME_TITLE_LARGE =
            "-fx-font-size: 56px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: 'Arial Black', 'Impact', sans-serif; " +
                    "-fx-text-fill: linear-gradient(to bottom, #FFD700 0%, #FFA500 50%, #DAA520 100%); " +
                    "-fx-effect: dropshadow(gaussian, rgba(218, 165, 32, 0.8), 15, 0.7, 0, 0), " +
                    "           innershadow(gaussian, rgba(255, 255, 255, 0.5), 5, 0, 0, 1);";

    /**
     * Medium title - for Welcome page
     */
    public static final String GAME_TITLE_MEDIUM =
            "-fx-font-size: 42px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: 'Arial Black', 'Impact', sans-serif; " +
                    "-fx-text-fill: linear-gradient(to bottom, #FFD700 0%, #FFA500 50%, #DAA520 100%); " +
                    "-fx-effect: dropshadow(gaussian, rgba(218, 165, 32, 0.6), 12, 0.6, 0, 0);";

    /**
     * Subtitle - for subheadings
     */
    public static final String SUBTITLE =
            "-fx-font-size: 20px; " +
                    "-fx-font-weight: normal; " +
                    "-fx-font-style: italic; " +
                    "-fx-text-fill: #E8E8E8; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 8, 0, 0, 2);";

    /**
     * Status text - in-game prompt text
     */
    public static final String STATUS_TEXT =
            "-fx-font-size: 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-text-fill: #FFFFFF; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.7), 5, 0, 0, 1);";

    /**
     * Money display - balance/prize, etc.
     */
    public static final String MONEY_TEXT =
            "-fx-font-size: 24px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: 'Courier New', monospace; " +
                    "-fx-text-fill: #FFD700; " +
                    "-fx-effect: dropshadow(gaussian, rgba(218, 165, 32, 0.8), 8, 0, 0, 0);";

    /**
     * Jackpot text - used when winning the jackpot
     */
    public static final String JACKPOT_TEXT =
            "-fx-font-size: 48px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: 'Arial Black', sans-serif; " +
                    "-fx-text-fill: linear-gradient(to right, #FFD700 0%, #FF6347 50%, #FFD700 100%); " +
                    "-fx-effect: dropshadow(gaussian, rgba(255, 215, 0, 0.9), 20, 0.8, 0, 0), " +
                    "           innershadow(gaussian, rgba(255, 255, 255, 0.7), 8, 0, 0, 2);";

    // ============ Decorative Element Styles ============

    /**
     * Separator line - gold decorative line
     */
    public static final String DECORATIVE_SEPARATOR =
            "-fx-background-color: linear-gradient(to right, " +
                    "transparent 0%, " +
                    "#DAA520 50%, " +
                    "transparent 100%); " +
                    "-fx-pref-height: 2; " +
                    "-fx-effect: dropshadow(gaussian, rgba(218, 165, 32, 0.6), 5, 0, 0, 0);";

    /**
     * Info panel style - for displaying rules, odds, etc.
     */
    public static final String INFO_PANEL =
            "-fx-background-color: rgba(44, 44, 44, 0.95); " +
                    "-fx-background-radius: 12; " +
                    "-fx-border-color: #DAA520; " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 12; " +
                    "-fx-padding: 20; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 15, 0, 0, 5);";

    /**
     * Game area container style - container for number buttons
     */
    public static final String GAME_AREA =
            "-fx-background-color: rgba(0, 0, 0, 0.3); " +
                    "-fx-background-radius: 15; " +
                    "-fx-padding: 25; " +
                    "-fx-effect: innershadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 3);";
}