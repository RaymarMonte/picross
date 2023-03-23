package picross.models.app;

import java.io.Serializable;

public enum State implements Serializable {
	NOT_INGAME, SHOWING_SOLUTION, GAME_IN_PROGRESS, SHOWING_WIN, SHOWING_LOSE
}
