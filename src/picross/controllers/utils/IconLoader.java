package picross.controllers.utils;

import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import picross.models.app.AppColor;

public class IconLoader {

	private static final int SQUARE_ICON_SIZE = 20;

	public void addIcon(MenuItem menuItem, String iconFileName) {
		Image openIcon = new Image(getClass().getResourceAsStream("/picross/resources/icons/" + iconFileName));
		ImageView openView = new ImageView(openIcon);
		openView.setFitWidth(SQUARE_ICON_SIZE);
		openView.setFitHeight(SQUARE_ICON_SIZE);
		menuItem.setGraphic(openView);
	}

	public void addColorIcon(MenuItem menuItem, AppColor color) {
		Pane icon = new Pane();
		icon.setPrefSize(SQUARE_ICON_SIZE, SQUARE_ICON_SIZE);
		icon.setStyle("-fx-background-color: " + color.getHex());
		menuItem.setGraphic(icon);
	}
}
