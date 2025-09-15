package hr.game.tinyepicdungeonsadventures.core.actions;

import hr.game.tinyepicdungeonsadventures.core.GameState;
import hr.game.tinyepicdungeonsadventures.model.player.Player;
import hr.game.tinyepicdungeonsadventures.model.dungeon.Room;
import lombok.extern.slf4j.Slf4j;

/**
 * Represents the player's Movement Action for the turn, allowing them to explore the dungeon.
 * <p>
 * This specific implementation simplifies the board game's movement rules by always
 * drawing and revealing one new room from the dungeon deck and moving the player into it.
 * It also handles the "ambush" mechanic, where a hero immediately takes damage
 * upon entering a room with monsters.
 */
@Slf4j
public class MovementAction extends Action {

    @Override
    public void execute(GameState state, Player player) {
        // Determine through how many rooms a hero can move
        int speed = player.getHero().getSpeed();
        log.info("{} may move up to {} rooms.", player.getHero().getName(), speed);

        // Reveal exactly one room per turn
        Room room = state.getDungeon().drawRoom();
        if (room == null) {
            log.info("No more rooms to reveal.");
            return;
        }
        room.reveal();
        state.setCurrentRoom(player, room);
        log.info("{} enters room: {}", player.getHero().getName(), room.getId());

        // There is loot in the room that can be taken
        if (room.hasItems()) {
            log.info("Loot has been revealed in the room! Player can now interact with it.");
        }

        // Monster encountered (hero takes 1 fixed damage)
        if (room.hasMonsters()) {
            player.getHero().takeDamage(1);
            log.info("{} is hurt by a monster for 1 damage (HP: {}).", player.getHero().getName(), player.getHero().getHealth());
        }
    }
}
