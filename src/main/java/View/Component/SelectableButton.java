package View.Component;

/**
 * An interface representing a button that can be selected or deselected.
 */
public interface SelectableButton extends StatefulButton {
    boolean isSelected();

    void select();

    void deselect();
}
