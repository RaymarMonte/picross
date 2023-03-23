package picross.models.puzzle.generator;

import picross.models.puzzle.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PuzzleRandomizer implements PuzzleGenerator {

	private final int rowCount;
	private final int colCount;

	public PuzzleRandomizer(int rowCount, int colCount) {
		this.rowCount = rowCount;
		this.colCount = colCount;
	}
	/**
	 * TODO: Refactor this
	 */
	@Override
	public Puzzle generate() {
		int cellCount = rowCount * colCount;

		SplittableRandom splittableRandom = new SplittableRandom();
		int filledCellsCount = splittableRandom.nextInt(1, cellCount);

		List<Integer> cellIndexes = IntStream.rangeClosed(0, cellCount - 1)
				.boxed().collect(Collectors.toList());
		Collections.shuffle(cellIndexes);
		List<Integer> chosenCellsIndexToFill = cellIndexes.subList(0, filledCellsCount);

		Boolean[] myFalseArray = new Boolean[cellCount];
		Arrays.fill(myFalseArray, false);
		List<Boolean> puzzleCells = Arrays.asList(myFalseArray);
		for (Integer chosenCell : chosenCellsIndexToFill) {
			puzzleCells.set(chosenCell, true);
		}

		int puzzleCellsIterator = 0;
		List<List<Boolean>> cells = new ArrayList<>();
		for (int i = 0; i < colCount; i++) {
			List<Boolean> column = new ArrayList<>();
			for (int j = 0; j < rowCount; j++) {
				column.add(puzzleCells.get(puzzleCellsIterator));
				puzzleCellsIterator++;
			}
			cells.add(column);
		}
		return new Puzzle(cells);
	}
}
