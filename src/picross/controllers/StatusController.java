package picross.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import picross.I18N;
import picross.models.app.State;
import picross.models.session.Session;

public class StatusController {

	@FXML
	public Label pointsLabel;
	@FXML
	public Label timeRemainingLabel;
	@FXML
	private Label pointsField;
	@FXML
	private Label timeRemainingField;

	@FXML
	public void initialize() {
		pointsLabel.textProperty().bind(I18N.createStringBinding("POINTS_LABEL"));
		timeRemainingLabel.textProperty().bind(I18N.createStringBinding("TIME_REMAINING_LABEL"));
	}

	public void setSession(Session session) {
		pointsField.textProperty().bind(session.pointsProperty().asString());
		session.stateProperty().addListener((observableValue, oldState, newState) -> {
			if (newState != State.GAME_IN_PROGRESS) {
				timeRemainingField.textProperty().unbind();
				timeRemainingField.setText("0");
			}
		});
		session.gameProperty().addListener((cls, oldGame, newGame) -> {
			if (newGame != null) {
				timeRemainingField.textProperty().unbind();
				timeRemainingField.textProperty().bind(newGame.timeRemainingProperty().asString());
			}
		});
	}
}
