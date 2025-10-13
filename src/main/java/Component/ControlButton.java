package Component;

import Utils.ButtonStyles;
import lombok.Getter;

@Getter
public class ControlButton extends StyledButton {

    private final ButtonStyles.ButtonType buttonType;
    private final String actionType;

    public ControlButton(String text, ButtonStyles.ButtonType buttonType) {
        super(text);
        this.buttonType = buttonType;
        this.actionType = text;
        //applyCurrentStyles();
        initializeStyles();
        applyCurrentStyles();
    }


    @Override
    protected void initializeStyles() {
        this.normalStyle = ButtonStyles.getGeneralButtonStyle(buttonType, "normal");
        this.hoverStyle = ButtonStyles.getGeneralButtonStyle(buttonType, "hover");
        this.pressedStyle = ButtonStyles.getGeneralButtonStyle(buttonType, "pressed");
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
