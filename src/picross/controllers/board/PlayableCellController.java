package picross.controllers.board;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import picross.models.app.AppColor;
import picross.models.board.cell.BoardCell;

public class PlayableCellController {

	@FXML
	private Label playLabel;

	@FXML
	private Pane playCell;

	private AppColor color;

	public void setupCell(BoardCell cell, AppColor color) {
		this.color = color;
		switch (cell) {
			case NEUTRAL:
				setNeutral();
				break;
			case FILLED:
				setFilled();
				break;
			case MARKED:
				setMarked();
				break;
		}

		EventHandler filter = new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				playCell.fireEvent(event);
				event.consume();
			}
		};
		playLabel.addEventFilter(MouseEvent.MOUSE_CLICKED, filter);
	}

	private void setNeutral() {
		playCell.setStyle("-fx-background-color: " + AppColor.GRAY.getHex());
		playLabel.setText("");
	}

	private void setFilled() {
		playCell.setStyle("-fx-background-color: " + color.getHex());
		playLabel.setText("");
	}

	private void setMarked() {
		playCell.setStyle("-fx-background-color: " + AppColor.GRAY.getHex());
		playLabel.setText("X");
	}

	public void handleClick(MouseEvent mouseEvent) {
		playCell.fireEvent(mouseEvent);
	}
}
