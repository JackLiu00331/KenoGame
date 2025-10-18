package View.Component;

import Utils.ButtonStyles;
import javafx.scene.control.Button;

/**
 * A button representing a number that can be selected by the user or system.
 */
public class NumberButton extends Button implements SelectableButton {
    // Enumeration representing the selection state of the button
    public enum ButtonState {
        UNSELECTED, USER_SELECTED, SYSTEM_SELECTED, BOTH_SELECTED
    }

    private final int number;
    private ButtonState currentState;
    private boolean selectable = true;

    // Constructor to initialize the NumberButton with a specific number
    public NumberButton(int number) {
        super(String.valueOf(number));
        this.number = number;
        // Default state is UNSELECTED
        this.currentState = ButtonState.UNSELECTED;

        this.setPrefSize(50, 50);
        updateState();
        setupMouseEvents();
    }

    /**
     * Sets up mouse event handlers to change button style on hover.
     */
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

    /**
     * Checks if the button is selected by the user.
     * @return true if the button is user-selected, false otherwise
     */
    @Override
    public boolean isSelected() {
        return currentState == ButtonState.USER_SELECTED;
    }

    /**
     * Selects the button as user-selected.
     */
    @Override
    public void select() {
        if (currentState == ButtonState.UNSELECTED) {
            currentState = ButtonState.USER_SELECTED;
            updateState();
        }
    }

    /**
     * Deselects the button if it is user-selected.
     */
    @Override
    public void deselect() {
        if (currentState == ButtonState.USER_SELECTED) {
            currentState = ButtonState.UNSELECTED;
            updateState();
        }
    }

    /**
     * Selects the button as system-selected.
     */
    public void systemSelect() {
        if (currentState == ButtonState.UNSELECTED) {
            currentState = ButtonState.SYSTEM_SELECTED;
        } else if (currentState == ButtonState.USER_SELECTED) {
            currentState = ButtonState.BOTH_SELECTED;
        }
        updateState();
    }

    /**
     * Updates the visual state of the button based on its current selection state.
     */
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

    /**
     * Resets the button to the unselected state.
     */
    @Override
    public void reset() {
        currentState = ButtonState.UNSELECTED;
        updateState();
    }

    /**
     * Gets the current selection state of the button.
     * @return the current ButtonState
     */
    @Override
    public ButtonState getState() {
        return currentState;
    }

    /**
     * Checks if the button is selected by both user and system.
     * @return true if both selected, false otherwise
     */
    public boolean bothSelected() {
        return currentState == ButtonState.BOTH_SELECTED;
    }


    // Getter and Setter
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
