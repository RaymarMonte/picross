package picross.controllers.board;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClueCellController {

	@FXML
	private Label clueLabel;

	public void setClue(int clue) {
		clueLabel.setText(String.valueOf(clue));
	}
}
