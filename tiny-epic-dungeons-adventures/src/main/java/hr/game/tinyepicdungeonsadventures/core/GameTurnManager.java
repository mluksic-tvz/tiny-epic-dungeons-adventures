package hr.game.tinyepicdungeonsadventures.core;

import hr.game.tinyepicdungeonsadventures.core.actions.*;
import hr.game.tinyepicdungeonsadventures.model.Player;

import java.util.List;

public class GameTurnManager {

    private final List<Action> gameActions = List.of(
            new MovementAction(),
            new HeroicAction(),
            new FreeAction(),
            new TorchAction()
    );

    public void processTurn(GameState state, Player current) {
        gameActions.forEach(action -> action.execute(state, current));
        state.advanceTurn();
    }
}
