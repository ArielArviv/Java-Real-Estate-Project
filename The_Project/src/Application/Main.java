package Application;

import Controller.Controller;
import Model.Agency;
import View.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
			Agency model = new Agency();
			View view = new View(primaryStage);
			Controller controller = new Controller(model, view);
	}

}
