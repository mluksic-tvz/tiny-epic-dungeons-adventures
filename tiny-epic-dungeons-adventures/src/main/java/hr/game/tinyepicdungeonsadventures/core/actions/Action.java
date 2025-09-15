package hr.game.tinyepicdungeonsadventures.core.actions;

import hr.game.tinyepicdungeonsadventures.core.GameState;
import hr.game.tinyepicdungeonsadventures.model.character.monster.Monster;
import hr.game.tinyepicdungeonsadventures.model.player.Player;

/**
 * Represents an abstract action that can be performed within the game.
 * <p>
 * Each concrete action (e.g., Movement, Attack) extends this class and provides
 * a specific implementation for the {@code execute} method.
 */
public abstract class Action {
    public abstract void execute(GameState state, Player player);
    public void execute(GameState state, Player player, Monster target) { }
}
