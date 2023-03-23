package picross.models.puzzle;

import picross.models.entities.CellCoords;

import java.io.Serializable;
import java.util.List;

/**
 * Class representation of the nonogram puzzle that needs to be figured-out by the one playing the game.
 * The implementation of cell coordinates is 0-based. For example, to check if the top-left-most cell
 * is filled or not, the {@link CellCoords} that needs to be sent is (0, 0)
 */
public class Puzzle implements Serializable {

	private List<List<Boolean>> cells;

	public Puzzle(List<List<Boolean>> cells) {
		this.cells = cells;
	}

	public boolean isFilled(CellCoords cellCoords) {
		return cells.get(cellCoords.getX()).get(cellCoords.getY());
	}

	public int getRowCount() {
		return cells.size();
	}

	public int getColCount() {
		return cells.isEmpty() ? 0 : cells.get(0).size();
	}

	public int getFilledCount() {
		int count = 0;
		for(int x = 0; x < cells.size(); x++) {
			for (int y = 0; y < cells.get(x).size(); y++) {
				if (cells.get(x).get(y)) {
					count++;
				}
			}
		}
		return count;
	}
}
