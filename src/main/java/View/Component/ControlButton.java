package View.Component;

import Utils.ButtonStyles;

public class ControlButton extends StyledButton {

    private final ButtonStyles.ButtonType styleType;
    private String buttonType; // Default to MENU, can be CONTROL

    public ControlButton(String text, ButtonStyles.ButtonType styleType, String buttonType) {
        super(text);
        this.styleType = styleType;
        if (buttonType == null || (!buttonType.equals("MENU") && !buttonType.equals("CONTROL"))) {
            this.buttonType = "MENU";
        } else {
            this.buttonType = buttonType;
        }
        initializeStyles();
        applyCurrentStyles();
    }


    @Override
    protected void initializeStyles() {
        if (buttonType.equals("MENU")) {
            this.normalStyle = ButtonStyles.getGeneralButtonStyle(styleType, "normal");
            this.hoverStyle = ButtonStyles.getGeneralButtonStyle(styleType, "hover");
            this.pressedStyle = ButtonStyles.getGeneralButtonStyle(styleType, "pressed");
        } else if (buttonType.equals("CONTROL")) {
            this.normalStyle = ButtonStyles.getControlButtonStyle(styleType, "normal");
            this.hoverStyle = ButtonStyles.getControlButtonStyle(styleType, "hover");
            this.pressedStyle = ButtonStyles.getControlButtonStyle(styleType, "pressed");
        }
    }

    @Override
    public void updateState() {
        applyCurrentStyles();
    }

    @Override
    public void reset() {
        applyCurrentStyles();
    }

    @Override
    public Object getState() {
        return buttonType;
    }

}
