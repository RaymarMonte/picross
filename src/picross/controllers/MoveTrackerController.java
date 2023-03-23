package picross.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import picross.I18N;
import picross.models.game.Game;

public class MoveTrackerController {

	@FXML
	public ListView movesListView;
	@FXML
	public TitledPane moveTrackerTitle;

	@FXML
	public void initialize() {
		moveTrackerTitle.textProperty().bind(I18N.createStringBinding("MOVE_TRACKER"));
	}

	public void setMoves(ObjectProperty<Game> game) {
		game.addListener((cls, oldGame, newGame) -> {
			if (newGame != null) {
				movesListView.setItems(newGame.getMoves());
			} else {
				movesListView.setItems(FXCollections.observableArrayList());
			}
		});
	}
}
