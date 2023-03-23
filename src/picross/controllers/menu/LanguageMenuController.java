package picross.controllers.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import picross.I18N;
import picross.controllers.utils.IconLoader;
import picross.models.app.App;

import java.util.Locale;

public class LanguageMenuController {

	private App app;

	@FXML
	public MenuButton languageMenu;
	@FXML
	public MenuItem englishMenuItem;
	@FXML
	public MenuItem frenchMenuItem;

	private IconLoader iconLoader;

	public LanguageMenuController() {
		iconLoader = new IconLoader();
	}

	public void setApp(App app) {
		this.app = app;
	}

	@FXML
	public void initialize() {
		iconLoader.addIcon(englishMenuItem, "english.png");
		iconLoader.addIcon(frenchMenuItem, "french.png");

		languageMenu.textProperty().bind(I18N.createStringBinding("LANG_MENU_BUTTON"));
		englishMenuItem.textProperty().bind(I18N.createStringBinding("ENGLISH_MENU_ITEM"));
		frenchMenuItem.textProperty().bind(I18N.createStringBinding("FRENCH_MENU_ITEM"));
	}

	public void handleEnglishSelect(ActionEvent actionEvent) {
		app.setLocale(Locale.ENGLISH);
	}

	public void handleFrenchSelect(ActionEvent actionEvent) {
		app.setLocale(Locale.FRENCH);
	}
}