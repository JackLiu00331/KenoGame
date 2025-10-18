package View.Component;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Factory class for creating menus and menu items in a JavaFX application.
 */
public class MenuFactory {

    /**
     * Creates a Help menu with options for game rules, prize table, and exit game.
     * @param showRulesAction - action to show game rules
     * @param showOddsAction - action to show prize table
     * @param exitAction - action to exit the game
     * @return the constructed Help menu
     */
    public static Menu createHelpMenu(Runnable showRulesAction, Runnable showOddsAction, Runnable exitAction) {
        Menu gameMenu = new Menu("Help");
        MenuItem gameRules = createMenuItem("Game Rules", showRulesAction);
        MenuItem prizeTable = createMenuItem("Prize Table", showOddsAction);
        MenuItem exitGame = createMenuItem("Exit Game", exitAction);
        gameMenu.getItems().addAll(gameRules, prizeTable, exitGame);
        return gameMenu;
    }

    /**
     * Creates a Settings menu with options to reset preferences and change theme.
     * @param resetAction - action to reset preferences
     * @param themeAction - action to change theme
     * @return the constructed Settings menu
     */
    public static Menu createSettingsMenu(Runnable resetAction, Runnable themeAction) {
        Menu settings = new Menu("Settings");
        MenuItem resetPreferences = MenuFactory.createMenuItem("Reset Preferences", resetAction);
        MenuItem changeTheme = MenuFactory.createMenuItem("Change Theme", themeAction);
        settings.getItems().addAll(changeTheme, resetPreferences);
        return settings;
    }

    /**
     * Creates a MenuItem with the specified text and action.
     * @param text - the display text of the menu item
     * @param action - the action to perform when the menu item is selected
     * @return the constructed MenuItem
     */
    public static MenuItem createMenuItem(String text, Runnable action) {
        MenuItem menuItem = new MenuItem(text);
        menuItem.setOnAction(e -> action.run());
        return menuItem;
    }
}
