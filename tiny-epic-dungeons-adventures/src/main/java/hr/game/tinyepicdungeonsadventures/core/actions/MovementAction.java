package hr.game.tinyepicdungeonsadventures.core.actions;

import hr.game.tinyepicdungeonsadventures.core.GameState;
import hr.game.tinyepicdungeonsadventures.model.Item;
import hr.game.tinyepicdungeonsadventures.model.Player;
import hr.game.tinyepicdungeonsadventures.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MovementAction extends Action {
    private static final Logger log = LogManager.getLogger(MovementAction.class);

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
        log.info("{} enters room: {}", player.getHero().getName(), room.getId());

        // Loot if present
        if (room.hasItems()) {
            Item loot = room.takeItem();
            player.getInventory().addItem(loot);
            log.info("{} finds loot: {}", player.getHero().getName(), loot.getName());
        }

        // Monster encountered (hero takes 1 fixed damage)
        if (room.hasMonsters()) {
            player.getHero().takeDamage(1);
            log.info("{} is hurt by a monster for 1 damage (HP: {}).", player.getHero().getName(), player.getHero().getHealth());
        }
    }
}
