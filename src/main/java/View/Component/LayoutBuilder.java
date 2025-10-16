package View.Component;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;

public class LayoutBuilder<T extends Pane> {
    protected T pane;

    public LayoutBuilder(T pane) {
        this.pane = pane;
    }

    public static LayoutBuilder<HBox> hbox() {
        return new LayoutBuilder<>(new HBox());
    }

    public static LayoutBuilder<VBox> vbox() {
        return new LayoutBuilder<>(new VBox());
    }

    public static LayoutBuilder<GridPane> gridPane() {
        return new LayoutBuilder<>(new GridPane());
    }

    public LayoutBuilder<T> style(String style) {
        pane.setStyle(style);
        return this;
    }

    public LayoutBuilder<T> size(double width, double height) {
        pane.setPrefSize(width, height);
        return this;
    }

    public LayoutBuilder<T> spacing(double spacing) {
        if (pane instanceof HBox) {
            ((HBox) pane).setSpacing(spacing);
        } else if (pane instanceof VBox) {
            ((VBox) pane).setSpacing(spacing);
        }
        return this;
    }

    public LayoutBuilder<T> padding(Insets padding) {
        pane.setPadding(padding);
        return this;
    }

    public LayoutBuilder<T> alignment(Pos alignment) {
        if (pane instanceof HBox) {
            ((HBox) pane).setAlignment(alignment);
        } else if (pane instanceof VBox) {
            ((VBox) pane).setAlignment(alignment);
        }
        return this;
    }

    public LayoutBuilder<T> padding(double top, double right, double bottom, double left) {
        pane.setPadding(new Insets(top, right, bottom, left));
        return this;
    }

    public LayoutBuilder<T> children(Node... nodes) {
        pane.getChildren().addAll(nodes);
        return this;
    }

    public LayoutBuilder<T> prefWidth(double width) {
        pane.setPrefWidth(width);
        return this;
    }

    public LayoutBuilder<T> prefHeight(double height) {
        pane.setPrefHeight(height);
        return this;
    }

    public LayoutBuilder<T> gap(double hgap, double vgap) {
        if (pane instanceof GridPane) {
            ((GridPane) pane).setHgap(hgap);
            ((GridPane) pane).setVgap(vgap);
        }
        return this;
    }

    public T build() {
        return pane;
    }
}
