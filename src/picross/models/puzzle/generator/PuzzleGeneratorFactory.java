package picross.models.puzzle.generator;

public class PuzzleGeneratorFactory {

	public PuzzleGenerator create(int rowCount, int colCount) {
		return new PuzzleRandomizer(rowCount, colCount);
	}
}
