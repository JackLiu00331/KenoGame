package View.Component;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * PrizeItemBox represents a UI component that displays the number of matches required
 * and the corresponding prize amount in a horizontal box layout.
 */
public class PrizeItemBox extends HBox {
    private NumberButton matchedCountBtn;
    private Label prizeLabel;
    private Integer requiredMatches;

    // Constructor to initialize the PrizeItemBox with required matches and prize amount
    public PrizeItemBox(Integer requiredMatches, Integer prize) {
        this.requiredMatches = requiredMatches;
        // Initialize the matched count button and prize label
        matchedCountBtn = new NumberButton(requiredMatches);
        matchedCountBtn.setDisable(true);
        prizeLabel = new Label("$" + prize);
        prizeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        this.setSpacing(20);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(matchedCountBtn, prizeLabel);
    }

    /**
     * Highlights the prize item to indicate it has been won.
     */
    public void highlightPrize() {
        matchedCountBtn.setState(NumberButton.ButtonState.BOTH_SELECTED);
        prizeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gold; -fx-font-weight: bold;");
    }

    /**
     * Resets the prize item to its default state.
     */
    public void reset() {
        matchedCountBtn.reset();
        prizeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
    }

    /**
     * Gets the number of required matches for this prize item.
     * @return The number of required matches.
     */
    public int getRequiredMatches() {
        return requiredMatches;
    }
}
