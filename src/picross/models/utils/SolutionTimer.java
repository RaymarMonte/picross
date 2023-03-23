package picross.models.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import picross.models.session.Session;

public class SolutionTimer {

	private Timeline timeline;

	public void start(Session session) {
		timeline = new Timeline(new KeyFrame(Duration.seconds(2), actionEevent -> {
			session.newGame();
		}));
		timeline.playFromStart();
	}

	public void stop() {
		if (timeline != null) {
			timeline.stop();
		}
	}
}
