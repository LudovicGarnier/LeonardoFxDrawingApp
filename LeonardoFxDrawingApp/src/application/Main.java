package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MainView;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		MainView mainView = new MainView(stage);
		Scene scene = mainView.initializeMainView();

		try {

			stage.setScene(scene);
			stage.setTitle("DrawingApp");
			stage.setMaximized(true);
			stage.setOnCloseRequest(e -> Platform.exit());
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
