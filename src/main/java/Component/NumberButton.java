package Component;

import Utils.ButtonStyles;
import javafx.scene.control.Button;
import lombok.Getter;

public class NumberButton extends Button implements SelectableButton {
    public enum ButtonState {
        UNSELECTED, USER_SELECTED, SYSTEM_SELECTED, BOTH_SELECTED
    }

    private final int number;
    private ButtonState currentState;
    private boolean selectable = true;

    public NumberButton(int number) {
        super(String.valueOf(number));
        this.number = number;
        this.currentState = ButtonState.UNSELECTED;

        this.setPrefSize(70, 70);
        this.setMinSize(70, 70);
        this.setMaxSize(70, 70);

        updateState();
        setupMouseEvents();
    }

    private void setupMouseEvents() {
        this.setOnMouseEntered(e -> {
            ButtonStyles.NumberButtonState styleState = (ButtonStyles.NumberButtonState) this.getUserData();
            if (styleState != null) {
                this.setStyle(ButtonStyles.getNumberButtonStyle(styleState, true));
            }
        });

        this.setOnMouseExited(e -> {
            ButtonStyles.NumberButtonState styleState = (ButtonStyles.NumberButtonState) this.getUserData();
            if (styleState != null) {
                this.setStyle(ButtonStyles.getNumberButtonStyle(styleState, false));
            }
        });
    }


    @Override
    public boolean isSelected() {
        return currentState == ButtonState.USER_SELECTED;
    }


    @Override
    public void select() {
        if (currentState == ButtonState.UNSELECTED) {
            currentState = ButtonState.USER_SELECTED;
            updateState();
        }
    }

    @Override
    public void deselect() {
        if (currentState == ButtonState.USER_SELECTED) {
            currentState = ButtonState.UNSELECTED;
            updateState();
        }
    }

    public void systemSelect() {
        if (currentState == ButtonState.UNSELECTED) {
            currentState = ButtonState.SYSTEM_SELECTED;
        } else if (currentState == ButtonState.USER_SELECTED) {
            currentState = ButtonState.BOTH_SELECTED;
        }
        updateState();
    }

    public void toggleUserSelection() {
        if (currentState == ButtonState.UNSELECTED) {
            select();
        } else if (currentState == ButtonState.USER_SELECTED) {
            deselect();
        }
    }

    @Override
    public void updateState() {
        ButtonStyles.NumberButtonState styleState;
        switch (currentState) {
            case USER_SELECTED:
                styleState = ButtonStyles.NumberButtonState.USER_SELECTED;
                break;
            case SYSTEM_SELECTED:
                styleState = ButtonStyles.NumberButtonState.SYSTEM_SELECTED;
                break;
            case BOTH_SELECTED:
                styleState = ButtonStyles.NumberButtonState.BOTH_SELECTED;
                break;
            default:
                styleState = ButtonStyles.NumberButtonState.UNSELECTED;
        }
        this.setStyle(ButtonStyles.getNumberButtonStyle(styleState, false));
        this.setUserData(styleState);
    }

    @Override
    public void reset() {
        currentState = ButtonState.UNSELECTED;
        updateState();
    }

    @Override
    public Object getState() {
        return currentState;
    }

    public boolean bothSelected() {
        return currentState == ButtonState.BOTH_SELECTED;
    }

    public void disableButton() {
        this.setDisable(true);
        this.setStyle(ButtonStyles.getNumberButtonStyle(ButtonStyles.NumberButtonState.UNSELECTED, false) + "-fx-opacity: 0.6;");
    }

    public void enableButton() {
        this.setDisable(false);
        updateState();
    }

    public int getNumber() {
        return number;
    }

    public void setState(ButtonState state) {
        this.currentState = state;
        updateState();
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }
}
