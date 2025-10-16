import View.WelcomeView;
import javafx.application.Application;

import javafx.stage.Stage;


public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
        WelcomeView welcomeView = new WelcomeView();
        welcomeView.show();
	}

}
