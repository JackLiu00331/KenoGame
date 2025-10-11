package View;

import javafx.stage.Stage;

public class GameStage {
    private Stage stage;

    public GameStage() {
        stage = new Stage();
        stage.setTitle("Keno Game");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(false);
    }
    public void show(){
        stage.show();
    }
}
