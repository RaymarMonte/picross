package picross.models.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import picross.models.game.Game;
import picross.models.session.Session;

public class TimerTicker {

	private Timeline timeline;

	public void start(Game game, Session session) {
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), actionEevent -> {
			int timeRemaining = game.getTimeRemaining();
			timeRemaining--;
			game.timeRemainingProperty().setValue(timeRemaining);
		}));
		timeline.setCycleCount(game.getTimeRemaining());
		timeline.setOnFinished(actionEvent -> session.gameLose());
		timeline.playFromStart();
	}

	public void stop() {
		if (timeline != null) {
			timeline.stop();
		}
	}

	public void pause() {
		if (timeline != null) {
			timeline.pause();
		}
	}

	public void resume() {
		if (timeline != null) {
			timeline.play();
		}
	}
}
