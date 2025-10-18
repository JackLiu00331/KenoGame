package View.Component;

/**
 * An interface representing a button that maintains a state.
 */
public interface StatefulButton {
    void updateState();

    void reset();

    Object getState();
}
