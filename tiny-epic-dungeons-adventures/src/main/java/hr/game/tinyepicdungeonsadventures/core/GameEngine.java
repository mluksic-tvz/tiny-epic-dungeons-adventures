package hr.game.tinyepicdungeonsadventures.core;

import hr.game.tinyepicdungeonsadventures.model.dungeon.Dungeon;
import hr.game.tinyepicdungeonsadventures.model.player.Player;
import lombok.Getter;

import java.util.List;

/**
 * The central component for managing the game's flow.
 * <p>
 * It orchestrates the game's state, including player turns and the dungeon's evolution.
 * This class provides methods to start the game and advance to the next turn,
 * ensuring the game progresses according to its rules.
 */
public class GameEngine {

    @Getter
    private final GameState state;

    public GameEngine(List<Player> players, Dungeon dungeon) {
        this.state = new GameState(players, dungeon);
    }

    public void startGame() {
        state.initialize();
    }

    public void advanceTurn() {
        state.advanceTurn();
    }
}
