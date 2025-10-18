/**
 * Course: CS 342
 * Name: Chao Liu
 * NetId: cliu1051
 * Email: cliu1051@uic.edu
 * Description:
 * A modern, interactive Keno game built
 * with Java and JavaFX, featuring smooth
 * animations, sound effects, and an intuitive user interface.
 * Date: 2025-10-17
 */

import View.WelcomeView;
import javafx.application.Application;

import javafx.stage.Stage;


public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        // Show the welcome view
        WelcomeView welcomeView = new WelcomeView();
        welcomeView.show();
	}

}
