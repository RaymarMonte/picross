package picross;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.stage.Window;
import picross.controllers.MainController;
import picross.models.app.App;

import java.io.IOException;

public class PicrossLauncher extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader parentLoader = new FXMLLoader(PicrossLauncher.class.getResource("views/main.fxml"));
		Scene scene = new Scene(parentLoader.load(), 1000, 1000);
		stage.setTitle("Picross '23");
		stage.setScene(scene);

		App app = new App();
		MainController mainController = parentLoader.getController();
		mainController.setApp(app);

		stage.show();

		FXMLLoader fxmlLoader = new FXMLLoader(PicrossLauncher.class.getResource("views/popups/welcome-popup.fxml"));
		Dialog dialog = new Dialog<>();
		try {
			dialog.setDialogPane(fxmlLoader.load());
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			Window window = dialog.getDialogPane().getScene().getWindow();
			window.setOnCloseRequest(event -> {
				window.hide();
			});
			dialog.show();
		}
	}

	public static void main(String[] args) {
		launch();
	}
}