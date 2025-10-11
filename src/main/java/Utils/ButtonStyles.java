package Utils;

public class ButtonStyles {

    // ============ Color Constants ============

    // primary color
    public static final String PRIMARY_COLOR = "#3498DB";
    public static final String PRIMARY_HOVER = "#2980B9";
    public static final String PRIMARY_PRESSED = "#21618C";

    // Success/Green
    public static final String SUCCESS_COLOR = "#2ECC71";
    public static final String SUCCESS_HOVER = "#27AE60";
    public static final String SUCCESS_PRESSED = "#1E8449";

    // Danger/Red
    public static final String DANGER_COLOR = "#E74C3C";
    public static final String DANGER_HOVER = "#C0392B";
    public static final String DANGER_PRESSED = "#A93226";

    // Warning/Orange
    public static final String WARNING_COLOR = "#F39C12";
    public static final String WARNING_HOVER = "#E67E22";
    public static final String WARNING_PRESSED = "#D35400";

    // Neutral/Gray
    public static final String NEUTRAL_COLOR = "#95A5A6";
    public static final String NEUTRAL_HOVER = "#7F8C8D";
    public static final String NEUTRAL_PRESSED = "#707B7C";

    // Dark Gray (for disabled states)
    public static final String DARK_COLOR = "#34495E";
    public static final String DARK_HOVER = "#2C3E50";
    public static final String DARK_PRESSED = "#1C2833";

    // Text colors
    public static final String TEXT_WHITE = "#FFFFFF";
    public static final String TEXT_DARK = "#2C3E50";

    // ============ MenuButton Style ============

    /**
     * Primary button style (blue - for main actions like Start)
     */
    public static final String MENU_BUTTON_PRIMARY = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 18px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 10; " +
                    "-fx-border-radius: 10; " +
                    "-fx-border-color: transparent; " +
                    "-fx-border-width: 2; " +
                    "-fx-padding: 15 40 15 40; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);",
            PRIMARY_COLOR, TEXT_WHITE
    );

    public static final String MENU_BUTTON_PRIMARY_HOVER = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 18px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 10; " +
                    "-fx-border-radius: 10; " +
                    "-fx-border-color: transparent; " +
                    "-fx-border-width: 2; " +
                    "-fx-padding: 15 40 15 40; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 3); " +
                    "-fx-scale-x: 1.02; " +
                    "-fx-scale-y: 1.02;",
            PRIMARY_HOVER, TEXT_WHITE
    );

    public static final String MENU_BUTTON_PRIMARY_PRESSED = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 18px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 10; " +
                    "-fx-border-radius: 10; " +
                    "-fx-border-color: transparent; " +
                    "-fx-border-width: 2; " +
                    "-fx-padding: 15 40 15 40; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 5, 0, 0, 1); " +
                    "-fx-scale-x: 0.98; " +
                    "-fx-scale-y: 0.98;",
            PRIMARY_PRESSED, TEXT_WHITE
    );

    /**
     * Success button style (green - for Play, Start, etc.)
     */
    public static final String MENU_BUTTON_SUCCESS = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 18px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 10; " +
                    "-fx-border-radius: 10; " +
                    "-fx-padding: 15 40 15 40; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);",
            SUCCESS_COLOR, TEXT_WHITE
    );

    public static final String MENU_BUTTON_SUCCESS_HOVER = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 18px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 10; " +
                    "-fx-border-radius: 10; " +
                    "-fx-padding: 15 40 15 40; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 3); " +
                    "-fx-scale-x: 1.02; " +
                    "-fx-scale-y: 1.02;",
            SUCCESS_HOVER, TEXT_WHITE
    );

    public static final String MENU_BUTTON_SUCCESS_PRESSED = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 18px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 10; " +
                    "-fx-border-radius: 10; " +
                    "-fx-padding: 15 40 15 40; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 5, 0, 0, 1); " +
                    "-fx-scale-x: 0.98; " +
                    "-fx-scale-y: 0.98;",
            SUCCESS_PRESSED, TEXT_WHITE
    );

    /**
     * Danger button style (red - for Exit, Delete, etc.)
     */
    public static final String MENU_BUTTON_DANGER = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 18px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 10; " +
                    "-fx-border-radius: 10; " +
                    "-fx-padding: 15 40 15 40; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);",
            DANGER_COLOR, TEXT_WHITE
    );

    public static final String MENU_BUTTON_DANGER_HOVER = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 18px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 10; " +
                    "-fx-border-radius: 10; " +
                    "-fx-padding: 15 40 15 40; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 3); " +
                    "-fx-scale-x: 1.02; " +
                    "-fx-scale-y: 1.02;",
            DANGER_HOVER, TEXT_WHITE
    );

    public static final String MENU_BUTTON_DANGER_PRESSED = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 18px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 10; " +
                    "-fx-border-radius: 10; " +
                    "-fx-padding: 15 40 15 40; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 5, 0, 0, 1); " +
                    "-fx-scale-x: 0.98; " +
                    "-fx-scale-y: 0.98;",
            DANGER_PRESSED, TEXT_WHITE
    );

    /**
     * Neutral button style (gray - for Back, Cancel, etc.)
     */
    public static final String MENU_BUTTON_NEUTRAL = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8; " +
                    "-fx-border-radius: 8; " +
                    "-fx-padding: 12 30 12 30; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);",
            NEUTRAL_COLOR, TEXT_WHITE
    );

    public static final String MENU_BUTTON_NEUTRAL_HOVER = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8; " +
                    "-fx-border-radius: 8; " +
                    "-fx-padding: 12 30 12 30; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 12, 0, 0, 3); " +
                    "-fx-scale-x: 1.02; " +
                    "-fx-scale-y: 1.02;",
            NEUTRAL_HOVER, TEXT_WHITE
    );

    public static final String MENU_BUTTON_NEUTRAL_PRESSED = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8; " +
                    "-fx-border-radius: 8; " +
                    "-fx-padding: 12 30 12 30; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0, 0, 1); " +
                    "-fx-scale-x: 0.98; " +
                    "-fx-scale-y: 0.98;",
            NEUTRAL_PRESSED, TEXT_WHITE
    );

    // ============ ControlButton styles (Play/Pause etc.) ============

    /**
     * Control button - Play state (green)
     */
    public static final String CONTROL_BUTTON_PLAY = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8; " +
                    "-fx-border-radius: 8; " +
                    "-fx-padding: 10 25 10 25; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);",
            SUCCESS_COLOR, TEXT_WHITE
    );

    public static final String CONTROL_BUTTON_PLAY_HOVER = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8; " +
                    "-fx-border-radius: 8; " +
                    "-fx-padding: 10 25 10 25; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 12, 0, 0, 3); " +
                    "-fx-scale-x: 1.05; " +
                    "-fx-scale-y: 1.05;",
            SUCCESS_HOVER, TEXT_WHITE
    );

    /**
     * Control button - Pause state (orange)
     */
    public static final String CONTROL_BUTTON_PAUSE = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8; " +
                    "-fx-border-radius: 8; " +
                    "-fx-padding: 10 25 10 25; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);",
            WARNING_COLOR, TEXT_WHITE
    );

    public static final String CONTROL_BUTTON_PAUSE_HOVER = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8; " +
                    "-fx-border-radius: 8; " +
                    "-fx-padding: 10 25 10 25; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 12, 0, 0, 3); " +
                    "-fx-scale-x: 1.05; " +
                    "-fx-scale-y: 1.05;",
            WARNING_HOVER, TEXT_WHITE
    );

    /**
     * Control button - Stop state (red)
     */
    public static final String CONTROL_BUTTON_STOP = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8; " +
                    "-fx-border-radius: 8; " +
                    "-fx-padding: 10 25 10 25; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 2);",
            DANGER_COLOR, TEXT_WHITE
    );

    public static final String CONTROL_BUTTON_STOP_HOVER = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 8; " +
                    "-fx-border-radius: 8; " +
                    "-fx-padding: 10 25 10 25; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 12, 0, 0, 3); " +
                    "-fx-scale-x: 1.05; " +
                    "-fx-scale-y: 1.05;",
            DANGER_HOVER, TEXT_WHITE
    );

    // ============ Small button styles (Reset, Random, Clear, etc.) ============

    /**
     * Small button - primary style (blue)
     */
    public static final String SMALL_BUTTON_PRIMARY = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 14px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 6; " +
                    "-fx-border-radius: 6; " +
                    "-fx-padding: 8 20 8 20; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0, 0, 1);",
            PRIMARY_COLOR, TEXT_WHITE
    );

    public static final String SMALL_BUTTON_PRIMARY_HOVER = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 14px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 6; " +
                    "-fx-border-radius: 6; " +
                    "-fx-padding: 8 20 8 20; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0, 0, 2); " +
                    "-fx-scale-x: 1.03; " +
                    "-fx-scale-y: 1.03;",
            PRIMARY_HOVER, TEXT_WHITE
    );

    /**
     * Small button - warning style (orange - for Random)
     */
    public static final String SMALL_BUTTON_WARNING = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 14px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 6; " +
                    "-fx-border-radius: 6; " +
                    "-fx-padding: 8 20 8 20; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0, 0, 1);",
            WARNING_COLOR, TEXT_WHITE
    );

    public static final String SMALL_BUTTON_WARNING_HOVER = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 14px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 6; " +
                    "-fx-border-radius: 6; " +
                    "-fx-padding: 8 20 8 20; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0, 0, 2); " +
                    "-fx-scale-x: 1.03; " +
                    "-fx-scale-y: 1.03;",
            WARNING_HOVER, TEXT_WHITE
    );

    /**
     * Small button - neutral style (gray - for Clear)
     */
    public static final String SMALL_BUTTON_NEUTRAL = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 14px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 6; " +
                    "-fx-border-radius: 6; " +
                    "-fx-padding: 8 20 8 20; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0, 0, 1);",
            NEUTRAL_COLOR, TEXT_WHITE
    );

    public static final String SMALL_BUTTON_NEUTRAL_HOVER = String.format(
            "-fx-background-color: %s; " +
                    "-fx-text-fill: %s; " +
                    "-fx-font-size: 14px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-background-radius: 6; " +
                    "-fx-border-radius: 6; " +
                    "-fx-padding: 8 20 8 20; " +
                    "-fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0, 0, 2); " +
                    "-fx-scale-x: 1.03; " +
                    "-fx-scale-y: 1.03;",
            NEUTRAL_HOVER, TEXT_WHITE
    );

    // ============ Disabled state ============

    /**
     * Disabled state style (common for all buttons)
     */
    public static final String BUTTON_DISABLED =
            "-fx-background-color: #BDC3C7; " +
                    "-fx-text-fill: #7F8C8D; " +
                    "-fx-font-size: 14px; " +
                    "-fx-background-radius: 6; " +
                    "-fx-border-radius: 6; " +
                    "-fx-padding: 8 20 8 20; " +
                    "-fx-opacity: 0.6; " +
                    "-fx-cursor: default;";
}
