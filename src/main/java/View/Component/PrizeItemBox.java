package View.Component;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


public class PrizeItemBox extends HBox {
    private NumberButton matchedCountBtn;
    private Label prizeLabel;
    private Integer requiredMatches;

    public PrizeItemBox(Integer requiredMatches, Integer prize) {
        this.requiredMatches = requiredMatches;
        matchedCountBtn = new NumberButton(requiredMatches);
        matchedCountBtn.setDisable(true);
        prizeLabel = new Label("$" + prize);
        prizeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        this.setSpacing(20);
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(matchedCountBtn, prizeLabel);
    }

    public void highlightPrize() {
        matchedCountBtn.setState(NumberButton.ButtonState.BOTH_SELECTED);
        prizeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gold; -fx-font-weight: bold;");
    }

    public void reset() {
        matchedCountBtn.reset();
        prizeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
    }

    public int getRequiredMatches() {
        return requiredMatches;
    }
}
