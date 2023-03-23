package picross.models.entities;

import java.io.Serializable;
import java.util.Objects;

public class CellCoords implements Serializable {

	private final int x;
	private final int y;

	public CellCoords(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CellCoords that = (CellCoords) o;
		return x == that.x && y == that.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
