package Utils;

import javafx.scene.control.MenuItem;

public class Util {

    public static MenuItem createMenuItem(String text, Runnable action) {
        MenuItem menuItem = new MenuItem(text);
        menuItem.setOnAction(e -> action.run());
        return menuItem;
    }
}
