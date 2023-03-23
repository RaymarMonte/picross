package picross.controllers.popups;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import picross.I18N;

public class LosePopupController {

	@FXML
	public DialogPane losePopup;

	@FXML
	public void initialize() {
		losePopup.headerTextProperty().bind(I18N.createStringBinding("LOSE_HEADER"));
	}
}
