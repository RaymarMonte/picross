package picross.controllers.menu;

import javafx.fxml.FXML;
import picross.models.app.App;

public class MenuController {

	@FXML
	private GameMenuController gameMenuController;
	@FXML
	private LanguageMenuController languageMenuController;

	private App app;

	public void setApp(App app) {
		this.app = app;
		gameMenuController.setApp(app);
		languageMenuController.setApp(app);
	}
}
