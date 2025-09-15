package hr.game.tinyepicdungeonsadventures.core;

import hr.game.tinyepicdungeonsadventures.model.dungeon.Dungeon;
import hr.game.tinyepicdungeonsadventures.model.player.Player;
import lombok.Getter;

import java.util.List;

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
