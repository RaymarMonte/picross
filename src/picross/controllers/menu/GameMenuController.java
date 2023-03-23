package picross.controllers.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import picross.I18N;
import picross.controllers.utils.IconLoader;
import picross.models.app.App;
import picross.models.app.AppColor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameMenuController {

	private static final int COLOR_ICON_SQUARE_SIZE = 20;
	@FXML
	public MenuButton gameMenu;
	public MenuItem saveMenuItem;
	public MenuItem loadMenuItem;
	@FXML
	public MenuItem resetMenuItem;
	@FXML
	public MenuItem solutionMenuItem;
	@FXML
	public Menu colorPickerMenuItem;
	@FXML
	public MenuItem greenSubMenuItem;
	@FXML
	public MenuItem blueSubMenuItem;
	@FXML
	public MenuItem purpleSubMenuItem;
	@FXML
	public MenuItem orangeSubMenuItem;
	@FXML
	public MenuItem exitMenuItem;

	private IconLoader iconLoader;
	private App app;

	public GameMenuController() {
		iconLoader = new IconLoader();
	}

	@FXML
	public void initialize() {
		iconLoader.addIcon(saveMenuItem, "save.png");
		iconLoader.addIcon(loadMenuItem, "load.png");
		iconLoader.addIcon(resetMenuItem, "reset.png");
		iconLoader.addIcon(solutionMenuItem, "solution.png");
		iconLoader.addIcon(colorPickerMenuItem, "color-picker.png");
		iconLoader.addIcon(exitMenuItem, "exit.png");
		iconLoader.addColorIcon(greenSubMenuItem, AppColor.GREEN);
		iconLoader.addColorIcon(blueSubMenuItem, AppColor.BLUE);
		iconLoader.addColorIcon(purpleSubMenuItem, AppColor.PURPLE);
		iconLoader.addColorIcon(orangeSubMenuItem, AppColor.ORANGE);

		gameMenu.textProperty().bind(I18N.createStringBinding("GAME_MENU_BUTTON"));
		saveMenuItem.textProperty().bind(I18N.createStringBinding("SAVE_MENU_ITEM"));
		loadMenuItem.textProperty().bind(I18N.createStringBinding("LOAD_MENU_ITEM"));
		resetMenuItem.textProperty().bind(I18N.createStringBinding("RESET_MENU_ITEM"));
		solutionMenuItem.textProperty().bind(I18N.createStringBinding("SOLUTION_MENU_ITEM"));
		colorPickerMenuItem.textProperty().bind(I18N.createStringBinding("COLOR_PICKER_MENU_ITEM"));
		greenSubMenuItem.textProperty().bind(I18N.createStringBinding("GREEN_COLOR_MENU_ITEM"));
		blueSubMenuItem.textProperty().bind(I18N.createStringBinding("BLUE_COLOR_MENU_ITEM"));
		purpleSubMenuItem.textProperty().bind(I18N.createStringBinding("PURPLE_COLOR_MENU_ITEM"));
		orangeSubMenuItem.textProperty().bind(I18N.createStringBinding("ORANGE_COLOR_MENU_ITEM"));
		exitMenuItem.textProperty().bind(I18N.createStringBinding("EXIT_MENU_ITEM"));
	}

	public void setApp(App session) {
		this.app = session;
	}

	public void handleResetAction(ActionEvent actionEvent) {
		app.getSession().reset();
	}

	public void handleSolutionAction(ActionEvent actionEvent) {
		app.getSession().solution();
	}

	public void handleExitAction(ActionEvent actionEvent) {
		Stage stage = (Stage) gameMenu.getScene().getWindow();
		stage.close();
	}

	public void handleGreenSelect(ActionEvent actionEvent) {
		app.setColor(AppColor.GREEN);
	}

	public void handleBlueSelect(ActionEvent actionEvent) {
		app.setColor(AppColor.BLUE);
	}

	public void handlePurpleSelect(ActionEvent actionEvent) {
		app.setColor(AppColor.PURPLE);
	}

	public void handleOrangeSelect(ActionEvent actionEvent) {
		app.setColor(AppColor.ORANGE);
	}

	public void handleSaveAction(ActionEvent actionEvent) {
		app.getSession().pauseTimer();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(I18N.get("SAVE_FILE_CHOOSER_TITLE"));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Picross Save File", "*.pic"));
		File file = fileChooser.showSaveDialog(gameMenu.getScene().getWindow());

		if (file != null) {
			try {
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				objectOutputStream.writeObject(app.getSessionToSave());
				objectOutputStream.flush();
				objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		app.getSession().resumeTimer();
	}

	public void handleLoadAction(ActionEvent actionEvent) {
		app.getSession().pauseTimer();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(I18N.get("LOAD_FILE_CHOOSER_TITLE"));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Picross Save File", "*.pic"));
		File file = fileChooser.showOpenDialog(gameMenu.getScene().getWindow());

		if (file != null) {
			try {
				FileInputStream fileInputStream
						= new FileInputStream(file);
				ObjectInputStream objectInputStream
						= new ObjectInputStream(fileInputStream);
				app.loadSession(objectInputStream.readObject());
				objectInputStream.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				app.getSession().resumeTimer();
			}
		}
	}
}
