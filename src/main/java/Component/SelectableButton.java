package Component;

public interface SelectableButton extends StatefulButton {
    boolean isSelected();

    void select();

    void deselect();
}
