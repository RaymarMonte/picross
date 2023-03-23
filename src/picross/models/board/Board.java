package picross.models.board;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import picross.models.board.cell.BoardCell;
import picross.models.clues.Clues;
import picross.models.entities.CellCoords;
import picross.models.puzzle.Puzzle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Board implements Serializable {

	private transient ObservableMap<CellCoords, BoardCell> playableCells;
	private Map<CellCoords, Integer> clueCells;
	private Set<CellCoords> emptyCells;
	private final int rowCount;
	private final int colCount;
	private final int horizontalCluesRowCount;
	private final int verticalCluesColCount;
	private final int puzzleRowsCount;
	private final int puzzleColsCount;

	public Board(Puzzle puzzle, Clues clues) {
		clueCells = new HashMap<>();
		emptyCells = new HashSet<>();

		horizontalCluesRowCount = clues.getLargestHorizontalClueEntryCount();
		verticalCluesColCount = clues.getLargestVerticalClueEntryCount();
		puzzleRowsCount = puzzle.getRowCount();
		puzzleColsCount = puzzle.getColCount();
		this.rowCount = horizontalCluesRowCount + puzzleRowsCount;
		this.colCount = verticalCluesColCount + puzzleColsCount;

		// Add upper-left empty cells
		for (int x = 0; x < horizontalCluesRowCount; x++) {
			for (int y = 0; y < verticalCluesColCount; y++) {
				emptyCells.add(new CellCoords(x, y));
			}
		}

		// Add vertical clues area cells
		List<List<Integer>> verticalClues = clues.getVerticalClues();
		for (int x = 0; x < verticalClues.size(); x++) {
			int actualCellXPos = x + horizontalCluesRowCount;
			int emptyCellsCount = verticalCluesColCount - verticalClues.get(x).size();
			for (int y = 0; y < verticalCluesColCount; y++) {
				// Fill empty cells first before adding clues
				if (y < emptyCellsCount) {
					emptyCells.add(new CellCoords(actualCellXPos, y));
				} else {
					int clueIteratorY = y - emptyCellsCount;
					clueCells.put(new CellCoords(actualCellXPos, y), verticalClues.get(x).get(clueIteratorY));
				}
			}
		}

		// Add horizontal clues area cells
		List<List<Integer>> horizontalClues = clues.getHorizontalClues();
		for (int y = 0; y < horizontalClues.size(); y++) {
			int actualCellYPos = y + verticalCluesColCount;
			int emptyCellsCount = horizontalCluesRowCount - horizontalClues.get(y).size();
			for (int x = 0; x < horizontalCluesRowCount; x++) {
				// Fill empty cells first before adding clues
				if (x < emptyCellsCount) {
					emptyCells.add(new CellCoords(x, actualCellYPos));
				} else {
					int clueIteratorX = x - emptyCellsCount;
					clueCells.put(new CellCoords(x, actualCellYPos), horizontalClues.get(y).get(clueIteratorX));
				}
			}
		}

		// Add the playable area cells
		initializePlayableCells();
	}

	public void initializePlayableCells() {
		playableCells = FXCollections.observableHashMap();
		for (int x = 0; x < puzzleRowsCount; x++) {
			int actualCellXPos = x + horizontalCluesRowCount;
			for (int y = 0; y < puzzleColsCount; y++) {
				int actualCellYPos = y + verticalCluesColCount;
				playableCells.put(new CellCoords(actualCellXPos, actualCellYPos), BoardCell.NEUTRAL);
			}
		}
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColCount() {
		return colCount;
	}

	public ObservableMap<CellCoords, BoardCell> getPlayableCells() {
		return playableCells;
	}

	public Map<CellCoords, Integer> getClueCells() {
		return clueCells;
	}

	public Set<CellCoords> getEmptyCells() {
		return emptyCells;
	}

	public Set<CellCoords> getFilledCellsSetWithoutCountingClueCells() {
		Set<CellCoords> filledCells = new HashSet<>();
		for (CellCoords cellCoords : playableCells.keySet()) {
			if (BoardCell.FILLED == playableCells.get(cellCoords)) {
				filledCells.add(new CellCoords(cellCoords.getX() - horizontalCluesRowCount,
						cellCoords.getY() - verticalCluesColCount));
			}
		}
		return filledCells;
	}

	public void applySolutionToBoard(Puzzle puzzle) {
		initializePlayableCells();
		for (CellCoords cellCoords : playableCells.keySet()) {
			if (puzzle.isFilled(new CellCoords(cellCoords.getX() - horizontalCluesRowCount,
					cellCoords.getY() - verticalCluesColCount))) {
				playableCells.put(cellCoords, BoardCell.FILLED);
			}
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(new HashMap<>(playableCells));
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		Map<CellCoords, BoardCell> map = (Map<CellCoords, BoardCell>) s.readObject();
		playableCells = FXCollections.observableMap(map);
	}
}
