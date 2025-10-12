package View;

import Utils.GameMode;
import Utils.PrizeTable;
import Utils.ThemeStyles;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.Map;

public class InfoWindow {

    public static final String GAME_RULES = "Each game round, you can choose up to 1, 4, 8, 10 numbers to play.\n\n" +
            "You can also hit 'Random' to let the system randomly pick numbers for you.\n\n" +
            "After selecting your numbers, click 'Play' to start the game round.\n\n" +
            "Once the game starts, 20 numbers will be drawn randomly.\n\n" +
            "Your winnings depend on how many of your chosen numbers match the drawn numbers.\n\n" +
            "For each spots mode winning details, please hit 'Prize Table' in help menu to check.\n";


    public static void createInfoWindow(int width, int height, String title, String content, String themeColor) {
        Stage window = new Stage();
        window.setTitle(title);
        window.setWidth(width);
        window.setMaxHeight(height);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);

        VBox mainLayout = new VBox();
        mainLayout.setStyle(ThemeStyles.INFO_PANEL);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 28px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: " + themeColor + ";");

        VBox contentBox = createContentBox(content);

        mainLayout.getChildren().addAll(titleLabel, contentBox);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setSpacing(10);

        Scene scene = new Scene(mainLayout);
        window.setScene(scene);
        window.show();
    }


    private static VBox createContentBox(String content) {
        VBox contentBox = new VBox();
        contentBox.setStyle("-fx-background: transparent; -fx-border-color: transparent;");

        Label contentLabel = new Label(content);
        contentLabel.setStyle(
                "-fx-font-size: 14px; " +
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

    public static void showRules() {
        createInfoWindow(400, 500, "Game Rules", InfoWindow.GAME_RULES, ThemeStyles.GOLD_DARK);
    }

    public static void showOdds(GameMode mode) {
        if (mode == null) {
            mode = GameMode.TEN_SPOT;
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

        createInfoWindow(400, 480, "Prize Table", sb.toString(), ThemeStyles.GOLD_LIGHT);
    }

    private static String formatPrize(int prize) {
        if (prize >= 100000) {
            return String.format("$%,d*", prize);
        } else if (prize >= 1000) {
            return String.format("$%,d", prize);
        }
        return "$" + prize;
    }
}
