package picross.controllers.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Window;
import picross.I18N;
import picross.PicrossLauncher;
import picross.controllers.utils.IconLoader;

import java.io.IOException;

public class HelpMenuController {

	@FXML
	public MenuButton helpMenu;
	@FXML
	public MenuItem aboutMenuItem;

	private IconLoader iconLoader;

	public HelpMenuController() {
		iconLoader = new IconLoader();
	}

	@FXML
	public void initialize() {
		iconLoader.addIcon(aboutMenuItem, "about.png");

		helpMenu.textProperty().bind(I18N.createStringBinding("HELP_MENU_BUTTON"));
		aboutMenuItem.textProperty().bind(I18N.createStringBinding("ABOUT_MENU_ITEM"));
	}

	public void handleAboutAction(ActionEvent actionEvent) {
		FXMLLoader fxmlLoader = new FXMLLoader(PicrossLauncher.class.getResource("views/popups/about-popup.fxml"));
		Dialog dialog = new Dialog<>();
		try {
			dialog.setDialogPane(fxmlLoader.load());
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			Window window = dialog.getDialogPane().getScene().getWindow();
			window.setOnCloseRequest(event -> window.hide());
			dialog.show();
		}
	}
}
