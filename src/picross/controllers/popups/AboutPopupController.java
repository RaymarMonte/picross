package picross.controllers.popups;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import picross.I18N;

public class AboutPopupController {

	@FXML
	public DialogPane aboutPopup;

	@FXML
	public void initialize() {
		aboutPopup.headerTextProperty().bind(I18N.createStringBinding("ABOUT_HEADER"));
		aboutPopup.contentTextProperty().bind(I18N.createStringBinding("ABOUT_CONTENT"));
	}
}
