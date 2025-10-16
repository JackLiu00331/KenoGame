package View.Component;

import javafx.scene.control.Button;

public abstract class StyledButton extends Button implements StatefulButton {
    protected String normalStyle;
    protected String hoverStyle;
    protected String pressedStyle;

    public StyledButton(String text) {
        super(text);
        setupMouseEvents();
    }

    protected abstract void initializeStyles();

    protected void applyCurrentStyles() {
        this.setStyle(normalStyle);
    }

    private void setupMouseEvents() {
        this.setOnMouseEntered(e -> {
            if (!this.isDisabled()) {
                this.setStyle(hoverStyle);
            }
        });

        this.setOnMouseExited(e -> {
            if (!this.isDisabled()) {
                this.setStyle(normalStyle);
            }
        });

        this.setOnMousePressed(e -> {
            if (!this.isDisabled()) {
                this.setStyle(pressedStyle);
            }
        });

        this.setOnMouseReleased(e -> {
            if (!this.isDisabled()) {
                if (this.isHover()) {
                    this.setStyle(hoverStyle);
                } else {
                    this.setStyle(normalStyle);
                }
            }
        });
    }

    protected void resetStyles() {
        initializeStyles();
        applyCurrentStyles();
    }
}
