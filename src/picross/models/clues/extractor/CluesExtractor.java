package picross.models.clues.extractor;

import picross.models.clues.Clues;
import picross.models.entities.CellCoords;
import picross.models.puzzle.Puzzle;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates Clues from a give Puzzle
 */
public class CluesExtractor {

	/**
	 * TODO: refactor this code
	 */
	public Clues extract(Puzzle puzzle) {
		List<List<Integer>> verticalClues = new ArrayList<>();
		for (int row = 0; row < puzzle.getRowCount(); row++) {
			List<Integer> verticalCluesEntry = new ArrayList<>();
			int counter = 0;
			for (int cell = 0; cell < puzzle.getColCount(); cell++) {
				if (puzzle.isFilled(new CellCoords(row, cell))) {
					counter++;
				} else {
					if (counter > 0) {
						verticalCluesEntry.add(counter);
					}
					counter = 0;
				}
			}
			if (counter > 0 || (counter == 0 && verticalCluesEntry.isEmpty())) {
				verticalCluesEntry.add(counter);
			}
			verticalClues.add(verticalCluesEntry);
		}

		List<List<Integer>> horizontalClues = new ArrayList<>();
		for (int col = 0; col < puzzle.getRowCount(); col++) {
			List<Integer> horizontalCluesEntry = new ArrayList<>();
			int counter = 0;
			for (int cell = 0; cell < puzzle.getColCount(); cell++) {
				if (puzzle.isFilled(new CellCoords(cell, col))) {
					counter++;
				} else {
					if (counter > 0) {
						horizontalCluesEntry.add(counter);
					}
					counter = 0;
				}
			}
			if (counter > 0 || (counter == 0 && horizontalCluesEntry.isEmpty())) {
				horizontalCluesEntry.add(counter);
			}
			horizontalClues.add(horizontalCluesEntry);
		}

		return new Clues(horizontalClues, verticalClues);
	}
}
