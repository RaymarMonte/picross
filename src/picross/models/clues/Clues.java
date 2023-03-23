package picross.models.clues;

import java.util.List;

public class Clues {

	private List<List<Integer>> horizontalClues;
	private List<List<Integer>> verticalClues;

	public Clues(List<List<Integer>> horizontalClues, List<List<Integer>> verticalClues) {
		this.horizontalClues = horizontalClues;
		this.verticalClues = verticalClues;
	}

	public List<List<Integer>> getHorizontalClues() {
		return horizontalClues;
	}

	public List<List<Integer>> getVerticalClues() {
		return verticalClues;
	}

	public int getLargestVerticalClueEntryCount() {
		int count = 0;
		for (List<Integer> clueRow : verticalClues) {
			int thisRowsColCount = clueRow.size();
			count = thisRowsColCount > count ? thisRowsColCount : count;
		}
		return count;
	}

	public int getLargestHorizontalClueEntryCount() {
		int count = 0;
		for (List<Integer> clueCol : horizontalClues) {
			int thisColsRowCount = clueCol.size();
			count = thisColsRowCount > count ? thisColsRowCount : count;
		}
		return count;
	}
}
