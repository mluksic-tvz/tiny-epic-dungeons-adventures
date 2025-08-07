package hr.game.tinyepicdungeonsadventures.core;

import hr.game.tinyepicdungeonsadventures.model.Dungeon;
import hr.game.tinyepicdungeonsadventures.model.Player;

import java.util.List;

public class GameEngine {

    private final GameState state;
    private final GameTurnManager turnManager;

    public GameEngine(List<Player> players, Dungeon dungeon) {
        this.state = new GameState(players, dungeon);
        this.turnManager = new GameTurnManager();
    }

    public void startGame() {
        state.initialize();
    }

    public void nextTurn() {
        Player current = state.getCurrentPlayer();
        turnManager.processTurn(state, current);
        state.advanceTurn();
    }

    public GameState getGameState() {
        return state;
    }
}
