package View.Component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;

/**
 * A builder class for creating layout panes with a fluent interface.
 * @param <T> The type of Pane being built.
 */
public class LayoutBuilder<T extends Pane> {
    // Generic pane instance
    protected T pane;

    /**
     * Constructor with mandatory pane parameter.
     * @param pane - The pane instance to be built.
     */
    public LayoutBuilder(T pane) {
        this.pane = pane;
    }

    /**
     * Static method to create a LayoutBuilder for HBox.
     * @return A new instance of LayoutBuilder for HBox.
     */
    public static LayoutBuilder<HBox> hbox() {
        return new LayoutBuilder<>(new HBox());
    }

    /**
     * Static method to create a LayoutBuilder for VBox.
     * @return A new instance of LayoutBuilder for VBox.
     */
    public static LayoutBuilder<VBox> vbox() {
        return new LayoutBuilder<>(new VBox());
    }

    /**
     * Static method to create a LayoutBuilder for GridPane.
     * @param style - The style string to apply to the GridPane.
     * @return A new instance of LayoutBuilder for GridPane.
     */
    public LayoutBuilder<T> style(String style) {
        pane.setStyle(style);
        return this;
    }

    /**
     * Set the size of the pane.
     * @param width - The width of the pane.
     * @param height - The height of the pane.
     * @return The current instance of LayoutBuilder.
     */
    public LayoutBuilder<T> size(double width, double height) {
        pane.setPrefSize(width, height);
        return this;
    }

    /**
     * Set the spacing between children in the pane.
     * @param spacing - The spacing value.
     * @return The current instance of LayoutBuilder.
     */
    public LayoutBuilder<T> spacing(double spacing) {
        if (pane instanceof HBox) {
            ((HBox) pane).setSpacing(spacing);
        } else if (pane instanceof VBox) {
            ((VBox) pane).setSpacing(spacing);
        }
        return this;
    }

    /**
     * Set the padding of the pane.
     * @param padding - The Insets padding value.
     * @return The current instance of LayoutBuilder.
     */
    public LayoutBuilder<T> padding(Insets padding) {
        pane.setPadding(padding);
        return this;
    }

    /**
     * Set the alignment of the pane.
     * @param alignment - The Pos alignment value.
     * @return The current instance of LayoutBuilder.
     */
    public LayoutBuilder<T> alignment(Pos alignment) {
        if (pane instanceof HBox) {
            ((HBox) pane).setAlignment(alignment);
        } else if (pane instanceof VBox) {
            ((VBox) pane).setAlignment(alignment);
        }
        return this;
    }

    /**
     * Set the padding of the pane with individual values.
     * @param top - The top padding.
     * @param right - The right padding.
     * @param bottom - The bottom padding.
     * @param left - The left padding.
     * @return The current instance of LayoutBuilder.
     */
    public LayoutBuilder<T> padding(double top, double right, double bottom, double left) {
        pane.setPadding(new Insets(top, right, bottom, left));
        return this;
    }

    /**
     * Add children nodes to the pane.
     * @param nodes - The nodes to be added as children.
     * @return The current instance of LayoutBuilder.
     */
    public LayoutBuilder<T> children(Node... nodes) {
        pane.getChildren().addAll(nodes);
        return this;
    }

    /**
     * Set the preferred width of the pane.
     * @param width - The preferred width.
     * @return The current instance of LayoutBuilder.
     */
    public LayoutBuilder<T> prefWidth(double width) {
        pane.setPrefWidth(width);
        return this;
    }

    /**
     * Set the preferred height of the pane.
     * @param height - The preferred height.
     * @return The current instance of LayoutBuilder.
     */
    public LayoutBuilder<T> prefHeight(double height) {
        pane.setPrefHeight(height);
        return this;
    }

    public T build() {
        return pane;
    }
}
