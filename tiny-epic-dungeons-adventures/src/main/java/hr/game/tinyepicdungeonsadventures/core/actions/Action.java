package hr.game.tinyepicdungeonsadventures.core.actions;

import hr.game.tinyepicdungeonsadventures.core.GameState;
import hr.game.tinyepicdungeonsadventures.model.character.monster.Monster;
import hr.game.tinyepicdungeonsadventures.model.player.Player;

public abstract class Action {

    public abstract void execute(GameState state, Player player);
    public void execute(GameState state, Player player, Monster target) { }
}
