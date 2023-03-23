package picross.controllers.popups;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import picross.I18N;

public class WelcomePopupController {

	@FXML
	public DialogPane welcomePopup;

	@FXML
	public void initialize() {
		welcomePopup.headerTextProperty().bind(I18N.createStringBinding("WELCOME_MESSAGE"));
	}
}
