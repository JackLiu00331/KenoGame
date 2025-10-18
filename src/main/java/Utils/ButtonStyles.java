package Utils;

/**
 * Utility class for button styles in JavaFX
 */
public class ButtonStyles {
    // ============ General Button Styles (MenuButton, ControlButton, etc.) ============
    /**
     * Enumeration of button types
     */
    public enum ButtonType {
        PRIMARY,    // Blue
        SUCCESS,    // Green
        DANGER,     // Red
        NEUTRAL     // Gray
    }

    /**
     * Get the style for general buttons
     * @param type  Button type
     * @param state Button state (normal, hover, pressed)
     * @return CSS style string
     */
    public static String getGeneralButtonStyle(ButtonType type, String state) {
        if (type == null) {
            type = ButtonType.PRIMARY;
        }
        String baseColor, hoverColor, pressedColor;

        switch (type) {
            case SUCCESS:
                baseColor = "#2ECC71";
                hoverColor = "#27AE60";
                pressedColor = "#1E8449";
                break;
            case DANGER:
                baseColor = "#E74C3C";
                hoverColor = "#C0392B";
                pressedColor = "#A93226";
                break;
            case NEUTRAL:
                baseColor = "#95A5A6";
                hoverColor = "#7F8C8D";
                pressedColor = "#707B7C";
                break;
            default:
                baseColor = "#3498DB";
                hoverColor = "#2980B9";
                pressedColor = "#21618C";
        }

        String color;
        String scale = "";
        String shadow = "dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2)";

        switch (state.toLowerCase()) {
            case "hover":
                color = hoverColor;
                scale = "-fx-scale-x: 1.02; -fx-scale-y: 1.02;";
                shadow = "dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 3)";
                break;
            case "pressed":
                color = pressedColor;
                scale = "-fx-scale-x: 0.98; -fx-scale-y: 0.98;";
                shadow = "dropshadow(gaussian, rgba(0,0,0,0.15), 5, 0, 0, 1)";
                break;
            default:
                color = baseColor;
        }

        return String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 24px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-radius: 8; " +
                        "-fx-padding: 12 30 12 30; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: %s; " +
                        "%s",
                color, shadow, scale
        );
    }

    /**
     * Get the style for control buttons (smaller size)
     * @param type Button type
     * @param state Button state (normal, hover, pressed)
     * @return CSS style string
     */
    public static String getControlButtonStyle(ButtonType type, String state) {
        if (type == null) {
            type = ButtonType.PRIMARY;
        }
        String baseColor, hoverColor, pressedColor, fontSize = "16px";

        switch (type) {
            case SUCCESS:
                baseColor = "#2ECC71";
                hoverColor = "#27AE60";
                pressedColor = "#1E8449";
                fontSize = "32px";
                break;
            case DANGER:
                baseColor = "#E74C3C";
                hoverColor = "#C0392B";
                pressedColor = "#A93226";
                break;
            case NEUTRAL:
                baseColor = "#95A5A6";
                hoverColor = "#7F8C8D";
                pressedColor = "#707B7C";
                break;
            default:
                baseColor = "#3498DB";
                hoverColor = "#2980B9";
                pressedColor = "#21618C";
        }

        String color;
        String scale = "";
        String shadow = "dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2)";

        switch (state.toLowerCase()) {
            case "hover":
                color = hoverColor;
                scale = "-fx-scale-x: 1.02; -fx-scale-y: 1.02;";
                shadow = "dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 3)";
                break;
            case "pressed":
                color = pressedColor;
                scale = "-fx-scale-x: 0.98; -fx-scale-y: 0.98;";
                shadow = "dropshadow(gaussian, rgba(0,0,0,0.15), 5, 0, 0, 1)";
                break;
            default:
                color = baseColor;
        }

        return String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: %s; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 8; " +
                        "-fx-border-radius: 8; " +
                        "-fx-padding: 12 30 12 30; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: %s; " +
                        "%s",
                color, fontSize, shadow, scale
        );
    }

    // ============ Number Button Styles (Specific to NumberButton) ============
    /**
     * Enumeration of number button states
     */
    public enum NumberButtonState {
        UNSELECTED,         // Unselected
        USER_SELECTED,      // User selected
        SYSTEM_SELECTED,    // System selected
        BOTH_SELECTED       // Both selected (winning)
    }


    /**
     * Get the style for number buttons based on their state and hover status
     * @param state - Number button state
     * @param isHover - Whether the button is being hovered over
     * @return - CSS style string
     */
    public static String getNumberButtonStyle(NumberButtonState state, boolean isHover) {
        switch (state) {
            case UNSELECTED:
                return getUnselectedStyle(isHover);
            case USER_SELECTED:
                return getUserSelectedStyle(isHover);
            case SYSTEM_SELECTED:
                return getSystemSelectedStyle();
            case BOTH_SELECTED:
                return getBothSelectedStyle(isHover);
            default:
                return getUnselectedStyle(false);
        }
    }

    /**
     * Unselected state (dark purple)
     * @param isHover - hover state
     * @return CSS style string
     */
    private static String getUnselectedStyle(boolean isHover) {
        String scale = isHover ? "-fx-scale-x: 1.05; -fx-scale-y: 1.05;" : "";
        String bg = isHover ?
                "radial-gradient(center 50% 50%, radius 50%, #4D2F3E 0%, #3A2530 100%)" :
                "radial-gradient(center 50% 50%, radius 50%, #3D1F2E 0%, #2A1520 100%)";
        String border = isHover ? "#6A4A5A" : "#5A3A4A";

        return String.format(
                "-fx-background-color: %s; " +
                        "-fx-border-color: %s; " +
                        "-fx-border-width: 3; " +
                        "-fx-border-radius: 50%%; " +
                        "-fx-background-radius: 50%%; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 8, 0, 0, 3); " +
                        "%s",
                bg, border, scale
        );
    }

    /**
     * User selected state (pink)
     * @param isHover - hover state
     * @return CSS style string
     */
    private static String getUserSelectedStyle(boolean isHover) {
        String scale = isHover ? "-fx-scale-x: 1.05; -fx-scale-y: 1.05;" : "";
        String bg = isHover ?
                "radial-gradient(center 50% 50%, radius 50%, #FFC3E0 0%, #FF9AD0 60%, #FF7BC0 100%)" :
                "radial-gradient(center 50% 50%, radius 50%, #FFB3D9 0%, #FF8AC6 60%, #FF6BB3 100%)";
        String border = isHover ? "#F06AA8" : "#E85A9F";

        return String.format(
                "-fx-background-color: %s; " +
                        "-fx-border-color: %s; " +
                        "-fx-border-width: 3; " +
                        "-fx-border-radius: 50%%; " +
                        "-fx-background-radius: 50%%; " +
                        "-fx-text-fill: #2A1520; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(232, 90, 159, 0.6), 12, 0, 0, 0), " +
                        "innershadow(gaussian, rgba(255, 255, 255, 0.3), 3, 0, 0, 1); " +
                        "%s",
                bg, border, scale
        );
    }

    /**
     * System selected state (golden)
     * @return CSS style string
     */
    private static String getSystemSelectedStyle() {
        return
                "-fx-background-color: radial-gradient(center 50% 50%, radius 50%, #3D1F2E 0%, #2A1520 100%); " +
                        "-fx-border-color: #FFD700; " +
                        "-fx-border-width: 4; " +
                        "-fx-border-radius: 50%; " +
                        "-fx-background-radius: 50%; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 215, 0, 0.8), 15, 0, 0, 0), " +
                        "innershadow(gaussian, rgba(255, 215, 0, 0.2), 3, 0, 0, 0);";
    }

    /**
     * Both selected state (winning - bright gold)
     * @param isHover - hover state
     * @return CSS style string
     */
    private static String getBothSelectedStyle(boolean isHover) {
        String scale = isHover ? "-fx-scale-x: 1.05; -fx-scale-y: 1.05;" : "";
        String bg = isHover ?
                "radial-gradient(center 50% 50%, radius 50%, #FFC866 0%, #FFAF3C 60%, #FF9D1A 100%)" :
                "radial-gradient(center 50% 50%, radius 50%, #FFB84D 0%, #FF9F1C 60%, #FF8C00 100%)";
        String border = isHover ? "#FFE55C" : "#FFD700";

        return String.format(
                "-fx-background-color: %s; " +
                        "-fx-border-color: %s; " +
                        "-fx-border-width: 4; " +
                        "-fx-border-radius: 50%%; " +
                        "-fx-background-radius: 50%%; " +
                        "-fx-text-fill: #2A1520; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(255, 165, 0, 0.9), 18, 0, 0, 0), " +
                        "innershadow(gaussian, rgba(255, 255, 255, 0.4), 3, 0, 0, 1); " +
                        "%s",
                bg, border, scale
        );
    }

    // ============ Menu Button Specific Style ============
    public static final String MENU_BUTTON_MODE =
            "-fx-background-color: linear-gradient(to right, #F39C12 0%, #F1C40F 50%, #F39C12 100%); " +
                    "-fx-text-fill: #2C3E50; " +
                    "-fx-font-size: 14px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: 'Courier New', monospace; " +
                    "-fx-padding: 8 12 8 12; " +
                    "-fx-background-radius: 20; " +
                    "-fx-border-color: rgba(0, 0, 0, 0.3); " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 20; " +
                    "-fx-effect: dropshadow(gaussian, rgba(243, 156, 18, 0.6), 12, 0, 0, 3), " +
                    "           innershadow(gaussian, rgba(255, 255, 255, 0.3), 3, 0, 0, 1);" +
                    " -fx-cursor: hand; ";
}
