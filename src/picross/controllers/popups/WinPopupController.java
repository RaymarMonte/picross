package picross.controllers.popups;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import picross.I18N;

public class WinPopupController {

	@FXML
	public DialogPane winPopup;

	@FXML
	public void initialize() {
		winPopup.headerTextProperty().bind(I18N.createStringBinding("WIN_HEADER"));
	}
}
