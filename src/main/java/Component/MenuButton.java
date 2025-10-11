package Component;

import Utils.ButtonStyles;
import javafx.scene.control.Button;

import java.awt.*;

public class MenuButton extends Button {
    public enum ButtonType {
        PRIMARY,
        SUCCESS,
        DANGER,
        NEUTRAL
    }

    private ButtonType buttonType;
    private String normalStyle;
    private String hoverStyle;
    private String pressedStyle;

    public MenuButton(String text, ButtonType type) {
        super(text);
        this.buttonType = type;
        initializeStyles();
        applyStyles();
    }


    private void initializeStyles() {
        switch (buttonType){
            case PRIMARY:
                normalStyle = ButtonStyles.MENU_BUTTON_PRIMARY;
                hoverStyle = ButtonStyles.MENU_BUTTON_PRIMARY_HOVER;
                pressedStyle = ButtonStyles.MENU_BUTTON_PRIMARY_PRESSED;
                break;
            case SUCCESS:
                normalStyle = ButtonStyles.MENU_BUTTON_SUCCESS;
                hoverStyle = ButtonStyles.MENU_BUTTON_SUCCESS_HOVER;
                pressedStyle = ButtonStyles.MENU_BUTTON_SUCCESS_PRESSED;
                break;
            case DANGER:
                normalStyle = ButtonStyles.MENU_BUTTON_DANGER;
                hoverStyle = ButtonStyles.MENU_BUTTON_DANGER_HOVER;
                pressedStyle = ButtonStyles.MENU_BUTTON_DANGER_PRESSED;
                break;
            case NEUTRAL:
                normalStyle = ButtonStyles.MENU_BUTTON_NEUTRAL;
                hoverStyle = ButtonStyles.MENU_BUTTON_NEUTRAL_HOVER;
                pressedStyle = ButtonStyles.MENU_BUTTON_NEUTRAL_PRESSED;
                break;
        }
    }

    private void applyStyles() {
        // Set initial style
        this.setStyle(normalStyle);

        // Add hover effect
        this.setOnMouseEntered(e -> this.setStyle(hoverStyle));
        this.setOnMouseExited(e -> this.setStyle(normalStyle));

        // Add pressed effect
        this.setOnMousePressed(e -> this.setStyle(pressedStyle));
        this.setOnMouseReleased(e -> {
            if (this.isHover()) {
                this.setStyle(hoverStyle);
            } else {
                this.setStyle(normalStyle);
            }
        });
    }

    public void resetStyle(){
        this.setStyle(normalStyle);
    }
}
