package picross.controllers.board;

import javafx.beans.property.ObjectProperty;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import picross.PicrossLauncher;
import picross.models.app.AppColor;
import picross.models.app.State;
import picross.models.board.Board;
import picross.models.board.cell.BoardCell;
import picross.models.entities.CellCoords;
import picross.models.session.Session;

import java.io.IOException;
import java.util.Map;

public class BoardController {

	@FXML
	private GridPane boardGrid;

	private Session session;
	private ObjectProperty<AppColor> color;

	public void setup(Session session, ObjectProperty<AppColor> color) {
		this.session = session;
		this.color = color;
		session.gameProperty().addListener((cls, oldGame, newGame) -> {
			if (newGame != null) {
				newGame.getBoard().getPlayableCells().addListener(
						(MapChangeListener<? super CellCoords, ? super BoardCell>) (mcl) -> renderBoard());
			}
			renderBoard();
		});
		session.stateProperty().addListener((cls, oldState, newState) -> {
			if (State.GAME_IN_PROGRESS != newState) {
				renderBoard();
			}
		});
		color.addListener((obs, oldColor, newColor) -> renderBoard());
	}

	private void renderBoard() {
		boardGrid.getColumnConstraints().removeAll(boardGrid.getColumnConstraints());
		boardGrid.getRowConstraints().removeAll(boardGrid.getRowConstraints());
		boardGrid.getChildren().removeAll(boardGrid.getChildren());
		if (session.getGame() == null) {
			return;
		}
		Board board = session.getGame().getBoard();

		for (int i = 0; i < board.getColCount(); i++) {
			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setPercentHeight(100d / board.getColCount());
			boardGrid.getRowConstraints().add(rowConstraints);
		}

		for (int i = 0; i < board.getRowCount(); i++) {
			ColumnConstraints colConstraints = new ColumnConstraints();
			colConstraints.setPercentWidth(100d / board.getRowCount());
			boardGrid.getColumnConstraints().add(colConstraints);
		}

		// Draw Empty Cells
		for (CellCoords cellCoords : board.getEmptyCells()) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						PicrossLauncher.class.getResource("views/board/empty-cell.fxml"));
				Pane pane = fxmlLoader.load();
				boardGrid.add(pane, cellCoords.getX(), cellCoords.getY());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		// Draw Clue Cells
		Map<CellCoords, Integer> clueCells = board.getClueCells();
		for (CellCoords cellCoords : clueCells.keySet()) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						PicrossLauncher.class.getResource("views/board/clue-cell.fxml"));
				Pane pane = fxmlLoader.load();
				ClueCellController controller = fxmlLoader.getController();
				controller.setClue(clueCells.get(cellCoords));
				boardGrid.add(pane, cellCoords.getX(), cellCoords.getY());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		// Draw Playable Cells
		ObservableMap<CellCoords, BoardCell> playableCells = board.getPlayableCells();
		for (CellCoords cellCoords : playableCells.keySet()) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(
						PicrossLauncher.class.getResource("views/board/playable-cell.fxml"));
				Pane pane = fxmlLoader.load();
				PlayableCellController controller = fxmlLoader.getController();
				controller.setupCell(playableCells.get(cellCoords), color.get());
				if (session.getState() == State.GAME_IN_PROGRESS) {
					pane.addEventHandler(MouseEvent.MOUSE_CLICKED,
							mouseEvent -> onBoardCellClick(mouseEvent, cellCoords));
				}
				boardGrid.add(pane, cellCoords.getX(), cellCoords.getY());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void onBoardCellClick(MouseEvent mouseEvent, CellCoords cellCoords) {
		if (mouseEvent.getButton() == MouseButton.PRIMARY) {
			session.fill(cellCoords);
		} else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
			session.mark(cellCoords);
		}
	}
}