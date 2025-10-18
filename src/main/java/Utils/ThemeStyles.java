package Utils;

/**
 * Theme style utility class
 * Provides overall theme styles for the game, including background, titles, etc.
 */
public class ThemeStyles {

    // ============ Color Constants ============
    public static final String CASINO_GREEN_DARK = "#0B6623";
    public static final String CASINO_GREEN = "#0E7A2E";

    public static final String GOLD_DARK = "#B8860B";
    public static final String GOLD_LIGHT = "#FFD700";

    public static final String VELVET_RED = "#8B0000";

    public static final String DARK_BG = "#1A1A1A";

    // ============ Background Styles ============

    // Welcome screen background - radial gradient from casino green to dark
    public static final String WELCOME_BACKGROUND = String.format(
            "-fx-background-color: " +
                    "radial-gradient(focus-angle 0deg, focus-distance 0%%, " +
                    "center 50%% 50%%, radius 70%%, " +
                    "%s 0%%, %s 100%%);",
            CASINO_GREEN, DARK_BG);


    // Main game background - subtle dark green gradient
    public static final String GAME_BACKGROUND_SUBTLE = String.format(
            "-fx-background-color: " +
                    "linear-gradient(to bottom, " +
                    "%s 0%%, " +
                    "%s 50%%, " +
                    "%s 100%%);",
            "#1C3A29", "#234D35", "#1C3A29");

    // Main game background - luxurious dark red gradient
    public static final String LUXURY_BACKGROUND = String.format(
            "-fx-background-color: " +
                    "linear-gradient(to bottom right, " +
                    "%s 0%%, " +
                    "%s 30%%, " +
                    "%s 50%%, " +
                    "%s 70%%, " +
                    "%s 100%%);",
            "#1A0505", "#3D0A0A", VELVET_RED, "#3D0A0A", "#1A0505");


    // ============ Title Styles ============

    // Game title - large, bold, gold gradient with shadow
    public static final String GAME_TITLE_LARGE =
            "-fx-font-size: 56px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: 'Arial Black', 'Impact', sans-serif; " +
                    "-fx-text-fill: linear-gradient(to bottom, #FFD700 0%, #FFA500 50%, #DAA520 100%); " +
                    "-fx-effect: dropshadow(gaussian, rgba(218, 165, 32, 0.8), 15, 0.7, 0, 0), " +
                    "           innershadow(gaussian, rgba(255, 255, 255, 0.5), 5, 0, 0, 1);";



    // ============ Decorative Element Styles ============
    // Info panel style - dark background with gold border and shadow
    public static final String INFO_PANEL =
            "-fx-background-color: #212226; " +
                    "-fx-background-radius: 12; " +
                    "-fx-border-color: #DAA520; " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 12; " +
                    "-fx-padding: 20; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 15, 0, 0, 5);";

    // Info label for status messages in positive
    public static final String INFO_LABEL_STATUS_POSITIVE =
            "-fx-background-color: #212226; " +
                    "-fx-background-radius: 12; " +
                    "-fx-border-color: #DAA520; " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 12; " +
                    "-fx-text-fill: #0aff0a; " +
                    "-fx-font-size: 15px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: 'Courier New', monospace; " +
                    "-fx-padding: 20; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 15, 0, 0, 5);";

    // Info label for status messages in danger
    public static final String INFO_LABEL_STATUS_DANGER =
            "-fx-background-color: #212226; " +
                    "-fx-background-radius: 12; " +
                    "-fx-border-color: #DAA520; " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 12; " +
                    "-fx-text-fill: #E74C3C; " +
                    "-fx-font-size: 15px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: 'Courier New', monospace; " +
                    "-fx-padding: 20; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 15, 0, 0, 5);";

    // Info label for status messages in neutral
    public static final String INFO_LABEL_STATUS_NEUTRAL =
            "-fx-background-color: #212226; " +
                    "-fx-background-radius: 12; " +
                    "-fx-border-color: #DAA520; " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 12; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 15px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: 'Courier New', monospace; " +
                    "-fx-padding: 20; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 15, 0, 0, 5);";

    // Info label for status messages in surprise
    public static final String INFO_LABEL_STATUS_SURPRISE =
            "-fx-background-color: #212226; " +
                    "-fx-background-radius: 12; " +
                    "-fx-border-color: #DAA520; " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 12; " +
                    "-fx-text-fill: #eb34eb; " +
                    "-fx-font-size: 15px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: 'Courier New', monospace; " +
                    "-fx-padding: 20; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 15, 0, 0, 5);";

    
}