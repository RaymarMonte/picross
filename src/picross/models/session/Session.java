package picross.models.session;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import picross.models.app.State;
import picross.models.entities.CellCoords;
import picross.models.game.Game;
import picross.models.puzzle.generator.PuzzleGenerator;
import picross.models.puzzle.generator.PuzzleGeneratorFactory;
import picross.models.utils.SolutionTimer;
import picross.models.utils.TimerTicker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;

/**
 * Represents your gaming session from opening the app up until closing it.
 */
public class Session implements Serializable {

	private transient final PuzzleGeneratorFactory puzzleGeneratorFactory;
	private transient final TimerTicker timerTicker;
	private transient final SolutionTimer solutionTimer;

	private transient ObjectProperty<State> state;
	private transient ObjectProperty<Game> game;
	private transient IntegerProperty points;

	public Session() {
		state = new SimpleObjectProperty<>(State.NOT_INGAME);
		game = new SimpleObjectProperty<>();
		points = new SimpleIntegerProperty(0);

		puzzleGeneratorFactory = new PuzzleGeneratorFactory();
		timerTicker = new TimerTicker();
		solutionTimer = new SolutionTimer();
	}

	public Game getGame() {
		return game.get();
	}

	public ObjectProperty<Game> gameProperty() {
		return game;
	}

	public State getState() {
		return state.get();
	}

	public ObjectProperty<State> stateProperty() {
		return state;
	}

	public int getPoints() {
		return points.get();
	}

	public IntegerProperty pointsProperty() {
		return points;
	}

	public void startGame() {
		if (State.NOT_INGAME == state.getValue()) {
			newGame();
		}
	}

	public void reset() {
		state.setValue(State.NOT_INGAME);
		points.setValue(0);
		game.setValue(null);
		timerTicker.stop();
	}

	public void solution() {
		if (State.GAME_IN_PROGRESS == state.getValue()) {
			losePoints();
			game.get().showSolution();
			state.setValue(State.SHOWING_SOLUTION);
			timerTicker.stop();
			solutionTimer.start(this);
		}
	}

	public void fill(CellCoords cellCoords) {
		game.get().fill(cellCoords);
		if (game.get().isWin()) {
			int currentPoints = getPoints();
			currentPoints++;
			points.setValue(currentPoints);
			state.setValue(State.SHOWING_WIN);
			timerTicker.stop();
		}
	}

	public void mark(CellCoords cellCoords) {
		game.get().mark(cellCoords);
	}

	public void gameLose() {
		losePoints();
		state.setValue(State.SHOWING_LOSE);
	}

	public void newGame() {
		state.setValue(State.GAME_IN_PROGRESS);
		game.setValue(new Game(create5x5PuzzleGenerator()));
		timerTicker.start(game.getValue(), this);
	}

	public void load(Session loadedSession) {
		timerTicker.stop();
		solutionTimer.stop();

		state.setValue(loadedSession.getState());
		points.setValue(loadedSession.getPoints());
		Game loadedGame = loadedSession.game.getValue();
		if (loadedSession.getGame() != null) {
			this.game.setValue(loadedGame);
			timerTicker.stop();
			timerTicker.start(loadedGame, this);
		}

	}

	private void losePoints() {
		int currentPoints = getPoints();
		if (currentPoints > 0) {
			currentPoints--;
		}
		points.setValue(currentPoints);
	}

	public void pauseTimer() {
		timerTicker.pause();
	}

	public void resumeTimer() {
		timerTicker.resume();
	}

	private PuzzleGenerator create5x5PuzzleGenerator() {
		return puzzleGeneratorFactory.create(5, 5);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(state.getValue());
		s.writeInt(points.getValue());
		Game gameToSave = game.getValue();
		if (null != gameToSave) {
			s.writeObject(gameToSave);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		state = new SimpleObjectProperty<>((State) s.readObject());
		points = new SimpleIntegerProperty(s.readInt());
		game = new SimpleObjectProperty<>();
		try {
			game.setValue((Game) s.readObject());
		} catch (OptionalDataException e) {
			// Don't load game state if no game exists
		}
	}
}
