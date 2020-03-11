package app;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{
    GUI gui;
	public App() {
		gui = new GUI();
	}
	public void start(Stage stage) {
		stage.setTitle("Movie database");
		gui.setStage(stage);
		stage.setScene(gui.menuScene());
		stage.show();
	}
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}