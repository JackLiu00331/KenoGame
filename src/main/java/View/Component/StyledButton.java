package View.Component;

import javafx.scene.control.Button;

/**
 * An abstract class representing a styled button with different visual states.
 */
public abstract class StyledButton extends Button implements StatefulButton {
    // Styles for different button states
    protected String normalStyle;
    protected String hoverStyle;
    protected String pressedStyle;

    // Constructor
    public StyledButton(String text) {
        super(text);
        setupMouseEvents();
    }

    /**
     * Initializes the styles for the button states.
     */
    protected abstract void initializeStyles();

    /**
     * Applies the current style based on the button's state.
     */
    protected void applyCurrentStyles() {
        this.setStyle(normalStyle);
    }

    /**
     * Sets up mouse event handlers to change styles based on user interaction.
     */
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
}
