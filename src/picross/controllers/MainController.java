package picross.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.stage.Window;
import picross.PicrossLauncher;
import picross.controllers.board.BoardController;
import picross.controllers.menu.MenuController;
import picross.models.app.App;
import picross.models.app.State;
import picross.models.session.Session;

import java.io.IOException;

public class MainController {

	@FXML
	private StatusController statusController;
	@FXML
	private BoardController boardController;
	@FXML
	private MoveTrackerController moveTrackerController;
	@FXML
	private BottomBarController bottomBarController;
	@FXML
	private MenuController menuController;

	private App app;

	public void setApp(App app) {
		this.app = app;
		Session session = app.getSession();
		statusController.setSession(session);
		boardController.setup(session, app.colorProperty());
		moveTrackerController.setMoves(session.gameProperty());
		bottomBarController.setSession(session);
		menuController.setApp(app);

		session.stateProperty().addListener((observableValue, old, newState) -> checkGameOverState(newState, session.stateProperty()));
	}

	private void checkGameOverState(State newState, ObjectProperty<State> state) {
		String popup = "";
		if (State.SHOWING_WIN == newState) {
			popup = "views/popups/win-popup.fxml";
		}
		else if (State.SHOWING_LOSE == newState) {
			popup = "views/popups/lose-popup.fxml";
		}
		if (State.SHOWING_WIN == newState || State.SHOWING_LOSE == newState) {
			FXMLLoader fxmlLoader = new FXMLLoader(PicrossLauncher.class.getResource(popup));
			Dialog dialog = new Dialog<>();
			try {
				dialog.setDialogPane(fxmlLoader.load());
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				Window window = dialog.getDialogPane().getScene().getWindow();
				window.setOnCloseRequest(event -> {
					window.hide();
					state.setValue(State.NOT_INGAME);
				});
				dialog.show();
			}
		}
	}


}
