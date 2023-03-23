package picross.models;

import javafx.beans.property.IntegerProperty;
import picross.models.board.Board;
import picross.models.clues.Clues;

public class PlayingBoard {

	private Board board;
	private Clues clues;
	private IntegerProperty points;

	public PlayingBoard(Board board, Clues clues, IntegerProperty points) {
		this.board = board;
		this.clues = clues;
		this.points = points;
	}


}
