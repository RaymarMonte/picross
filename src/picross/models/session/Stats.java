package picross.models.session;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Stats {

	private IntegerProperty points;
	private IntegerProperty timeRemaining;

	public Stats() {
		points = new SimpleIntegerProperty(0);
		timeRemaining = new SimpleIntegerProperty(0);
	}

	public int getPoints() {
		return points.get();
	}

	public IntegerProperty pointsProperty() {
		return points;
	}

	public int getTimeRemaining() {
		return timeRemaining.get();
	}

	public IntegerProperty timeRemainingProperty() {
		return timeRemaining;
	}
}
