package Utils;

/**
 * Theme style utility class
 * Provides overall theme styles for the game, including background, titles, etc.
 */
public class ThemeStyles {

    // ============ Color Constants ============
    public static final String CASINO_GREEN_DARK = "#0B6623";
    public static final String CASINO_GREEN = "#0E7A2E";
    public static final String CASINO_GREEN_LIGHT = "#118D3A";

    public static final String GOLD_DARK = "#B8860B";
    public static final String GOLD = "#DAA520";
    public static final String GOLD_LIGHT = "#FFD700";

    public static final String VELVET_RED = "#8B0000";
    public static final String VELVET_RED_LIGHT = "#A52A2A";

    public static final String DARK_BG = "#1A1A1A";
    public static final String DARK_BG_LIGHT = "#2C2C2C";

    // ============ Background Styles ============

    /**
     * Welcome to Stage background - dark casino style with radial gradient
     * Brighter in the center, darker around, creating a spotlight effect
     */
    public static final String WELCOME_BACKGROUND = String.format(
            "-fx-background-color: " +
                    "radial-gradient(focus-angle 0deg, focus-distance 0%%, " +
                    "center 50%% 50%%, radius 70%%, " +
                    "%s 0%%, %s 100%%);",
            CASINO_GREEN, DARK_BG);

    /**
     * Game Stage background - classic casino green gradient
     * From dark green to lighter green, simulating a table feel
     * Subtle gradient, does not affect gameplay
     */
    public static final String GAME_BACKGROUND = String.format(
            "-fx-background-color: " +
                    "linear-gradient(to bottom, " +
                    "%s 0%%, " +
                    "%s 50%%, " +
                    "%s 100%%);",
            CASINO_GREEN_DARK, CASINO_GREEN, CASINO_GREEN_DARK);

    /**
     * Alternative: softer casino background (dark gray-green tone)
     * Use this if pure green is too bright
     */
    public static final String GAME_BACKGROUND_SUBTLE = String.format(
            "-fx-background-color: " +
                    "linear-gradient(to bottom, " +
                    "%s 0%%, " +
                    "%s 50%%, " +
                    "%s 100%%);",
            "#1C3A29", "#234D35", "#1C3A29"); // These colors are not defined as constants

    /**
     * Luxury background - velvet texture (dark red-black gradient)
     * Suitable for high-end casino style
     */
    public static final String LUXURY_BACKGROUND = String.format(
            "-fx-background-color: " +
                    "linear-gradient(to bottom right, " +
                    "%s 0%%, " +
                    "%s 30%%, " +
                    "%s 50%%, " +
                    "%s 70%%, " +
                    "%s 100%%);",
            "#1A0505", "#3D0A0A", VELVET_RED, "#3D0A0A", "#1A0505"); // Only VELVET_RED is a constant

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
            "-fx-background-color: linear-gradient(to right, #34495E 0%, #2C3E50 50%, #34495E 100%); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 10 20 10 20; " +
                    "-fx-background-radius: 18; " +
                    "-fx-border-color: rgba(255, 255, 255, 0.2); " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 18; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.6), 10, 0, 0, 3), " +
                    "           innershadow(gaussian, rgba(255, 255, 255, 0.15), 3, 0, 0, 1);";

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
            "-fx-background-color: #212226; " +
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

    // ============ 信息标签样式（余额、模式等）============

    /**
     * 游戏模式标签样式 - 红色/橙色渐变背景
     * 类似图片中的 "KENO" 标签
     */
    public static final String INFO_LABEL_MODE =
            "-fx-background-color: linear-gradient(to right, #C0392B 0%, #E74C3C 50%, #C0392B 100%); " +
                    "-fx-text-color: #ffffff" +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 12 25 12 25; " +
                    "-fx-background-radius: 20; " +
                    "-fx-border-color: rgba(0, 0, 0, 0.3); " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 20; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.6), 10, 0, 0, 3), " +
                    "           innershadow(gaussian, rgba(255, 255, 255, 0.2), 3, 0, 0, 1);";

    /**
     * 余额标签样式 - 金色/黄色渐变背景
     * 类似图片中的 "+75.00 USD" 标签
     */
    public static final String INFO_LABEL_BALANCE =
            "-fx-background-color: linear-gradient(to right, #F39C12 0%, #F1C40F 50%, #F39C12 100%); " +
                    "-fx-text-fill: #2C3E50; " +
                    "-fx-font-size: 18px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: 'Courier New', monospace; " +
                    "-fx-padding: 12 25 12 25; " +
                    "-fx-background-radius: 20; " +
                    "-fx-border-color: rgba(0, 0, 0, 0.3); " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 20; " +
                    "-fx-effect: dropshadow(gaussian, rgba(243, 156, 18, 0.6), 12, 0, 0, 3), " +
                    "           innershadow(gaussian, rgba(255, 255, 255, 0.3), 3, 0, 0, 1);";

    /**
     * 状态标签样式 - 绿色背景（用于积极信息）
     */
    public static final String INFO_LABEL_STATUS_POSITIVE =
            "-fx-background-color: linear-gradient(to right, #27AE60 0%, #2ECC71 50%, #27AE60 100%); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 15px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 10 20 10 20; " +
                    "-fx-background-radius: 18; " +
                    "-fx-border-color: rgba(0, 0, 0, 0.3); " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 18; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 8, 0, 0, 2), " +
                    "           innershadow(gaussian, rgba(255, 255, 255, 0.2), 3, 0, 0, 1);";

    /**
     * 中性标签样式 - 蓝色背景（用于一般信息）
     */
    public static final String INFO_LABEL_NEUTRAL =
            "-fx-background-color: linear-gradient(to right, #2980B9 0%, #3498DB 50%, #2980B9 100%); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 15px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 10 20 10 20; " +
                    "-fx-background-radius: 18; " +
                    "-fx-border-color: rgba(0, 0, 0, 0.3); " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 18; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 8, 0, 0, 2), " +
                    "           innershadow(gaussian, rgba(255, 255, 255, 0.2), 3, 0, 0, 1);";

    /**
     * 投注金额标签样式 - 紫色渐变
     */
    public static final String INFO_LABEL_BET =
            "-fx-background-color: linear-gradient(to right, #8E44AD 0%, #9B59B6 50%, #8E44AD 100%); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-family: 'Courier New', monospace; " +
                    "-fx-padding: 10 20 10 20; " +
                    "-fx-background-radius: 18; " +
                    "-fx-border-color: rgba(0, 0, 0, 0.3); " +
                    "-fx-border-width: 2; " +
                    "-fx-border-radius: 18; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 8, 0, 0, 2), " +
                    "           innershadow(gaussian, rgba(255, 255, 255, 0.2), 3, 0, 0, 1);";

    /**
     * 信息区域容器样式
     */
    public static final String INFO_AREA_CONTAINER =
            "-fx-background-color: rgba(0, 0, 0, 0.3); " +
                    "-fx-background-radius: 15; " +
                    "-fx-padding: 15; " +
                    "-fx-spacing: 15; " +
                    "-fx-effect: innershadow(gaussian, rgba(0,0,0,0.4), 8, 0, 0, 2);";
}