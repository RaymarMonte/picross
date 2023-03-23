package picross.models.game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import picross.models.board.Board;
import picross.models.board.cell.BoardCell;
import picross.models.clues.extractor.CluesExtractor;
import picross.models.entities.CellCoords;
import picross.models.puzzle.Puzzle;
import picross.models.puzzle.generator.PuzzleGenerator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Game implements Serializable {

	private transient CluesExtractor cluesExtractor;

	private transient ObservableList<String> moves;
	private final Board board;
	private final Puzzle puzzle;

	private transient IntegerProperty timeRemaining;

	public Game(PuzzleGenerator puzzleGenerator) {
		cluesExtractor = new CluesExtractor();

		timeRemaining = new SimpleIntegerProperty(90);
		moves = FXCollections.observableArrayList();
		puzzle = puzzleGenerator.generate();
		board = new Board(puzzle, cluesExtractor.extract(puzzle));
	}

	public Board getBoard() {
		return board;
	}

	public int getTimeRemaining() {
		return timeRemaining.get();
	}

	public IntegerProperty timeRemainingProperty() {
		return timeRemaining;
	}

	public ObservableList<String> getMoves() {
		return moves;
	}

	public void showSolution() {
		board.applySolutionToBoard(puzzle);
	}

	public void fill(CellCoords cellCoords) {
		ObservableMap<CellCoords, BoardCell> playableCells = board.getPlayableCells();
		if (playableCells.containsKey(cellCoords)) {
			switch (playableCells.get(cellCoords)) {
				case NEUTRAL:
					playableCells.put(cellCoords, BoardCell.FILLED);
					moves.add("selected " + formatCellCoords(cellCoords));
					break;
				case FILLED:
					playableCells.put(cellCoords, BoardCell.NEUTRAL);
					moves.add("deselected " + formatCellCoords(cellCoords));
					break;
			}
		}
	}

	public void mark(CellCoords cellCoords) {
		ObservableMap<CellCoords, BoardCell> playableCells = board.getPlayableCells();
		if (playableCells.containsKey(cellCoords)) {
			switch (playableCells.get(cellCoords)) {
				case NEUTRAL:
					playableCells.put(cellCoords, BoardCell.MARKED);
					moves.add("marked " + formatCellCoords(cellCoords));
					break;
				case MARKED:
					playableCells.put(cellCoords, BoardCell.NEUTRAL);
					moves.add("unmarked " + formatCellCoords(cellCoords));
					break;
			}
		}
	}

	public boolean isWin() {
		Set<CellCoords> filledCells = board.getFilledCellsSetWithoutCountingClueCells();
		if (filledCells.size() != puzzle.getFilledCount()) {
			return false;
		}
		for (CellCoords filledCell : filledCells) {
			if (!puzzle.isFilled(filledCell)) {
				return false;
			}
		}
		return true;
	}

	public static String formatCellCoords(CellCoords cellCoords) {
		return "[" + cellCoords.getX() + ", " + cellCoords.getY() + "]";
	}

	public void load() {

	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(new ArrayList<>(moves));
		s.writeInt(timeRemaining.getValue());
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		List<String> list = (List<String>) s.readObject();
		moves = FXCollections.observableArrayList(list);
		timeRemaining = new SimpleIntegerProperty(s.readInt());
		cluesExtractor = new CluesExtractor();
	}
}
