package hr.game.tinyepicdungeonsadventures.core.actions;

import hr.game.tinyepicdungeonsadventures.core.GameState;
import hr.game.tinyepicdungeonsadventures.model.Item;
import hr.game.tinyepicdungeonsadventures.model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FreeAction extends Action {
    private static final Logger logger = LogManager.getLogger(FreeAction.class);

    @Override
    public void execute(GameState state, Player player) {
        List<Item> items = new ArrayList<>(player.getInventory().getItems());
        // Find a healing potion and use it to increase health
        for (Item item : items) {
            if (item.isHealingPotion()) {
                if (player.getInventory().removeItem(item)) {
                    int heal = item.getHealAmount();
                    player.getHero().heal(heal);
                    logger.info("{} uses {} and heals {} HP (now {}).", player.getHero().getName(), item.getName(), heal, player.getHero().getHealth());
                }
                return;
            }
        }

        // If no healing potion was used, discard other loot item
        for (Item item : items) {
            if (!item.isHealingPotion()) {
                if (player.getInventory().removeItem(item)) {
                    logger.info("{} discards loot: {}.", player.getHero().getName(), item.getName());
                }
                return;
            }
        }

        logger.info("{} takes no free actions.", player.getHero().getName());
    }
}
