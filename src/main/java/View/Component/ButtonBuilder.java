package View.Component;

import Utils.ButtonStyles;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ButtonBuilder {
    private String text;
    private double width = -1;
    private double height = -1;
    private ButtonStyles.ButtonType styleType;
    private String buttonType = "CONTROL";
    private EventHandler<ActionEvent> action;

    public ButtonBuilder(String text) {
        this.text = text;
    }

    public ButtonBuilder style(ButtonStyles.ButtonType styleType) {
        this.styleType = styleType;
        return this;
    }

    public ButtonBuilder size(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public ButtonBuilder onClick(EventHandler<ActionEvent> action) {
        this.action = action;
        return this;
    }

    public ButtonBuilder asMenu() {
        this.buttonType = "MENU";
        return this;
    }

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
