package View.Component;

import Utils.ButtonStyles;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * A builder class for creating ControlButton instances with a fluent interface.
 */
public class ButtonBuilder {
    private String text;
    private double width = -1;
    private double height = -1;
    private ButtonStyles.ButtonType styleType;
    // Default button type is CONTROL
    private String buttonType = "CONTROL";
    private EventHandler<ActionEvent> action;

    // Constructor with mandatory text parameter
    public ButtonBuilder(String text) {
        this.text = text;
    }

    /**
     * Set the style type of the button.
     * @param styleType - The style type to apply to the button.
     * @return The current instance of ButtonBuilder.
     */
    public ButtonBuilder style(ButtonStyles.ButtonType styleType) {
        this.styleType = styleType;
        return this;
    }

    /**
     * Set the size of the button.
     * @param width - The width of the button.
     * @param height - The height of the button.
     * @return The current instance of ButtonBuilder.
     */
    public ButtonBuilder size(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Set the action to be performed when the button is clicked.
     * @param action - The event handler for the button click action.
     * @return The current instance of ButtonBuilder.
     */
    public ButtonBuilder onClick(EventHandler<ActionEvent> action) {
        this.action = action;
        return this;
    }

    /**
     * Set the button type to MENU.
     * @return The current instance of ButtonBuilder.
     */
    public ButtonBuilder asMenu() {
        this.buttonType = "MENU";
        return this;
    }

    /**
     * Build and return the ControlButton instance.
     * @return A configured ControlButton instance.
     */
    public ControlButton build() {
        ControlButton btn = new ControlButton(text, styleType, buttonType);
        if (width > 0) {
            btn.setPrefWidth(width);
        }
        if (height > 0) {
            btn.setPrefHeight(height);
        }
        if (action != null) {
            btn.setOnAction(action);
        }
        return btn;
    }

}
