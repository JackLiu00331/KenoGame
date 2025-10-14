package Component;

import Model.GameMode;
import View.InfoWindow;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MenuFactory {
    private GameMode gameMode;

    public static Menu createHelpMenu(Runnable showRulesAction, Runnable showOddsAction, Runnable exitAction) {
        Menu gameMenu = new Menu("Help");
        MenuItem gameRules = createMenuItem("Game Rules", showRulesAction);
        MenuItem prizeTable = createMenuItem("Prize Table", showOddsAction);
        MenuItem exitGame = createMenuItem("Exit Game", exitAction);
        gameMenu.getItems().addAll(gameRules, prizeTable, exitGame);
        return gameMenu;
    }

    public static Menu createSettingsMenu(Runnable resetAction, Runnable themeAction) {
        Menu settings = new Menu("Settings");
        MenuItem resetPreferences = MenuFactory.createMenuItem("Reset Preferences", resetAction);
        MenuItem changeTheme = MenuFactory.createMenuItem("Change Theme", themeAction);
        settings.getItems().addAll(changeTheme, resetPreferences);
        return settings;
    }

    public static MenuItem createMenuItem(String text, Runnable action) {
        MenuItem menuItem = new MenuItem(text);
        menuItem.setOnAction(e -> action.run());
        return menuItem;
    }
}
