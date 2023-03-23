package picross.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import picross.I18N;
import picross.models.session.Session;

public class BottomBarController {

	@FXML
	public Button startGameButton;

	private Session session;

	@FXML
	public void initialize() {
		startGameButton.textProperty().bind(I18N.createStringBinding("START_GAME_BUTTON"));
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void handleStartGameClick(ActionEvent actionEvent) {
		session.startGame();
	}
}
