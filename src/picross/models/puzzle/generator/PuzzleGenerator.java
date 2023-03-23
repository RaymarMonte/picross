package picross.models.puzzle.generator;

import picross.models.puzzle.Puzzle;

/**
 * Generates the Puzzles. Usually runs when starting or restarting game.
 */
public interface PuzzleGenerator {

	Puzzle generate();
}
