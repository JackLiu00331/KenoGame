package View;

import View.Component.ButtonBuilder;
import View.Component.ControlButton;
import Model.GameMode;
import Model.PrizeTable;
import Utils.ButtonStyles;
import Utils.ThemeStyles;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.effect.GaussianBlur;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

public class InfoWindow {

    private static final Preferences prefs = Preferences.userNodeForPackage(InfoWindow.class);
    public static final String PREFS_KEY_SHOW_RULES = "show_rules";
    public static final String PREFS_KEY_SHOW_ODDS = "show_odds";
    private static Stage window;

    /**
     * Create and display an information window.
     * @param width - window width
     * @param height - window height
     * @param title - window title
     * @param content - main content text
     * @param themeColor - title color
     * @param prefsKey - preference key for "Don't show again" checkbox
     * @param showCheckBox - whether to show the checkbox
     * @param wait - whether to block interaction with other windows
     * @param buttonArea - optional button area at the bottom
     * @param root - the root pane to apply blur effect
     */
    public static void createInfoWindow(int width, int height, String title, String content, String themeColor, String prefsKey, boolean showCheckBox, boolean wait, HBox buttonArea, Pane root) {
        window = new Stage();
        window.setTitle(title);
        window.setWidth(width);
        window.setHeight(height);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);

        VBox mainLayout = new VBox();
        mainLayout.setStyle(ThemeStyles.INFO_PANEL);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 28px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: " + themeColor + ";");

        VBox contentBox = createContentBox(content);

        // Add "Don't show again" checkbox if needed
        CheckBox dontShowAgainCheckBox = showCheckBox ? createCheckBox(prefsKey, window) : null;
        if (dontShowAgainCheckBox != null) {
            mainLayout.getChildren().addAll(titleLabel, contentBox, dontShowAgainCheckBox);
        } else {
            mainLayout.getChildren().addAll(titleLabel, contentBox);
        }

        // Add button area if provided
        if(buttonArea != null) {
            mainLayout.getChildren().add(buttonArea);
        }
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setSpacing(10);

        // Center the window on the screen
        Scene scene = new Scene(mainLayout);
        window.setScene(scene);
        window.setAlwaysOnTop(true);

        // Apply blur effect to the root pane
        GaussianBlur blur = new GaussianBlur(20);
        ColorAdjust darker = new ColorAdjust(0, -1.0, -0.7, -0.2);
        darker.setInput(blur);
        root.setEffect(blur);
        window.setOnHidden( e -> root.setEffect(null));

        // Show the window, blocking if specified
        if (wait) {
            window.initStyle(StageStyle.UNDECORATED);
            window.showAndWait();
        } else {
            window.show();
        }

    }

    /**
     * Create a checkbox for "Don't show this again" functionality.
     * @param prefsKey - preference key to store the user's choice
     * @param window - the current window to close when checkbox is toggled
     * @return
     */
    private static CheckBox createCheckBox(String prefsKey, Stage window) {
        CheckBox checkBox = new CheckBox("Don't show this again");
        checkBox.setStyle("-fx-text-fill: #E8E8E8; -fx-font-size: 13px;");
        checkBox.setSelected(!shouldShowAgain(prefsKey));

        // Update preference when checkbox is toggled
        checkBox.setOnAction(e -> {
            prefs.putBoolean(prefsKey, !checkBox.isSelected());
            System.out.println("Preference " + prefsKey + " set to " + !checkBox.isSelected());
            PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(0.4));
            pause.play();
            pause.setOnFinished(event -> Platform.runLater(window::close));
        });
        return checkBox;
    }


    /**
     * Create the content box with styled label.
     * @param content - main content text
     * @return VBox containing the content label
     */
    private static VBox createContentBox(String content) {
        VBox contentBox = new VBox();
        contentBox.setStyle("-fx-background: transparent; -fx-border-color: transparent;");

        Label contentLabel = new Label(content);
        contentLabel.setStyle(
                "-fx-font-size: 16px; " +
                        "-fx-text-fill: #ffffff; " +
                        "-fx-wrap-text: true; " +
                        "-fx-padding: 20; " +
                        "-fx-background-color: rgba(0,0,0,0.3); " +
                        "-fx-background-radius: 10;"
        );
        contentLabel.setWrapText(true);
        contentBox.getChildren().add(contentLabel);
        contentBox.setAlignment(Pos.CENTER);
        return contentBox;
    }

    /**
     * Show game rules window if user hasn't opted out.
     * @param autoShow - whether to check user preference
     * @param root - the root pane to apply blur effect
     */
    public static void showRules(boolean autoShow, Pane root) {
        if (autoShow && !shouldShowAgain(PREFS_KEY_SHOW_RULES)) {
            return;
        }
        String GAME_RULES = "Pick a mode first by using the button at left top corner!\n\n" +
                "Each game round, you can choose up to 1, 4, 8, 10 numbers to play.\n\n" +
                "You can also hit 'Random' to let the system pick numbers for you.\n\n" +
                "After selecting your numbers, click 'Play' to start.\n\n" +
                "Once the game starts, 20 numbers will be drawn randomly.\n\n" +
                "Your winnings depend on how many of your chosen numbers match the drawn numbers.\n\n" +
                "For each spots mode winning details, please hit 'Prize Table' in help menu to check.\n\n" +
                "Try 'Control + Shift + C' for a surprise!\n ";
        createInfoWindow(500, 580, "Game Rules", GAME_RULES, ThemeStyles.GOLD_DARK, PREFS_KEY_SHOW_RULES, autoShow, false, null, root);

    }

    /**
     *  Show odds and prize table for the selected game mode.
     * @param mode - current game mode
     * @param autoShow - whether to check user preference
     * @param root - the root pane to apply blur effect
     */
    public static void showOdds(GameMode mode, boolean autoShow, Pane root) {
        // Default to 10-spot mode if mode is null
        if (mode == null) {
            mode = GameMode.TEN_SPOT;
        }

        // Check user preference, if autoShow is true and user opted out, skip showing
        if (autoShow && !shouldShowAgain(PREFS_KEY_SHOW_ODDS)) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        int spotsPlayed = mode.getMaxSpots();
        sb.append("Current Mode: ");
        sb.append(mode.getDisplayName());
        sb.append("\n\n");
        sb.append("Payouts for a $1.00 bet:\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        var prizeTable = PrizeTable.getPrizeTableForSpots(spotsPlayed);
        if (prizeTable.isEmpty()) {
            sb.append("No payout information available for this game mode.\n");
        } else {
            sb.append("   Match              Prize   \n");
            sb.append("══════════════════════════\n");
            prizeTable.entrySet().stream()
                    .sorted(Comparator.comparingInt(Map.Entry::getKey))
                    .forEach(entry -> {
                        int matchCount = entry.getKey();
                        int payout = entry.getValue();

                        String prizeStr = formatPrize(payout);
                        sb.append(String.format("     %2d                     %-10s\n", matchCount, prizeStr));
                    });
            sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
            sb.append(String.format("Overall Odds: 1 in %.2f\n", PrizeTable.getOdds(spotsPlayed)));
        }

        createInfoWindow(400, 550, "Prize Table", sb.toString(), ThemeStyles.GOLD_LIGHT, PREFS_KEY_SHOW_ODDS, autoShow, false, null, root);
    }

    /**
     * Show the result of the current round.
     * @param currentRound - current round number
     * @param totalRounds - total rounds in the game
     * @param matchedCount - count of matched numbers
     * @param prize - prize won this round
     * @param matchedNumbers - list of matched numbers
     * @param onContinue - action to perform on continue
     * @param onFinish - action to perform on finish
     * @param root - the root pane to apply blur effect
     */
    public static void showResult(int currentRound, int totalRounds, int matchedCount, int prize, List<Integer> matchedNumbers, Runnable onContinue, Runnable onFinish, VBox root) {
        StringBuilder sb = new StringBuilder();
        sb.append("Round ").append(currentRound).append(" of ").append(totalRounds).append("\n\n");
        sb.append("Drawn Numbers:\n");
        sb.append(matchedNumbers.toString().replaceAll("[\\[\\],]", "")).append("\n\n");
        sb.append("You matched ").append(matchedCount).append(" number").append(matchedCount == 1 ? "" : "s").append(".\n");
        if (prize > 0) {
            sb.append("Congratulations!\n");
            sb.append("You won ").append(formatPrize(prize)).append("!\n");
        } else {
            sb.append("No prize this round.\n");
            sb.append("Better luck next time!\n");
        }
        // Create button area based on game progress
        HBox buttonArea = createButtonArea(currentRound, totalRounds, onContinue, onFinish);
        createInfoWindow(400, 400, "Round Result", sb.toString(), ThemeStyles.GOLD_DARK, null, false, true,buttonArea, root);
    }


    /**
     * Show the game history window with past game summaries.
     * @param historyContent - formatted history text
     * @param root - the root pane to apply blur effect
     */
    public static void showHistory(String historyContent, VBox root) {
        createInfoWindow(500, 600, "Game History", historyContent, ThemeStyles.GOLD_DARK, null, false, false, null, root);
    }

    /**
     * Create the button area for the result window.
     * @param currentRound - current round number
     * @param totalRounds - total rounds in the game
     * @param onContinue - action to perform on continue
     * @param onFinish - action to perform on finish
     * @return HBox containing the buttons
     */
    private static HBox createButtonArea(int currentRound, int totalRounds, Runnable onContinue, Runnable onFinish) {
        HBox buttonArea = new HBox(20);
        buttonArea.setAlignment(Pos.CENTER);

        // If there are more rounds, show "Continue" button; otherwise show "Close" button
        if (currentRound < totalRounds) {

            ControlButton continueBtn = new ButtonBuilder("Continue")
                    .style(ButtonStyles.ButtonType.SUCCESS)
                    .asMenu()
                    .onClick(e -> {
                        window.close();
                        if (onContinue != null) {
                            onContinue.run();
                        }
                    }).build();

            buttonArea.getChildren().addAll(continueBtn);

        } else {

            ControlButton closeBtn = new ButtonBuilder("Close")
                    .style(ButtonStyles.ButtonType.DANGER)
                    .asMenu()
                    .onClick(e -> {
                        window.close();
                        if (onFinish != null) {
                            onFinish.run();
                        }
                    }).build();
            buttonArea.getChildren().add(closeBtn);
        }

        return buttonArea;
    }

    /**
     * Overloaded method to show odds without checking user preference.
     * @param mode - current game mode
     * @param root - the root pane to apply blur effect
     */
    public static void showOdds(GameMode mode, Pane root) {
        showOdds(mode, false, root);
    }

    /**
     * Overloaded method to show rules without checking user preference.
     * @param root - the root pane to apply blur effect
     */
    public static void showRules(Pane root) {
        showRules(false, root);
    }

    /**
     * Format prize amount with appropriate styling.
     * @param prize - prize amount in dollars
     * @return
     */
    private static String formatPrize(int prize) {
        if (prize >= 100000) {
            return String.format("$%,d*", prize);
        } else if (prize >= 1000) {
            return String.format("$%,d", prize);
        }
        return "$" + prize;
    }

    /**
     * Check if the dialog should be shown again based on user preference.
     * @param key - preference key
     * @return
     */
    private static boolean shouldShowAgain(String key) {
        return prefs.getBoolean(key, true);
    }

    /**
     * Reset all preferences to show dialogs again.
     */
    public static void resetAllPreferences() {
        prefs.putBoolean(PREFS_KEY_SHOW_RULES, true);
        prefs.putBoolean(PREFS_KEY_SHOW_ODDS, true);
        System.out.println("All preferences reset to show dialogs again.");
    }
}
