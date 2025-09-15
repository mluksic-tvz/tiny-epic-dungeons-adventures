package hr.game.tinyepicdungeonsadventures.core;

import hr.game.tinyepicdungeonsadventures.core.actions.*;
import hr.game.tinyepicdungeonsadventures.model.character.monster.Monster;
import hr.game.tinyepicdungeonsadventures.model.player.Player;

public class GameTurnManager {

    private final MovementAction movementAction = new MovementAction();
    private final HeroicAction heroicAction = new HeroicAction();
    private final FreeAction freeAction = new FreeAction();
    private final TorchAction torchAction = new TorchAction();

    public void performMovementAction(GameState state, Player player) {
        movementAction.execute(state, player);
    }

    public void performHeroicAction(GameState state, Player player, Monster target) {
        if (target != null) {
            heroicAction.execute(state, player, target);
        }
    }

    public void performFreeAction(GameState state, Player player) {
        freeAction.execute(state, player);
    }

    public void endPlayerTurn(GameState state, Player player) {
        torchAction.execute(state, player);
    }
}
