package hr.game.tinyepicdungeonsadventures.core.actions;

import hr.game.tinyepicdungeonsadventures.core.GameState;
import hr.game.tinyepicdungeonsadventures.model.Item;
import hr.game.tinyepicdungeonsadventures.model.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FreeAction extends Action {

    @Override
    public void execute(GameState state, Player player) {
        List<Item> items = new ArrayList<>(player.getInventory().getItems());
        // Find a healing potion and use it to increase health
        for (Item item : items) {
            if (item.isHealingPotion()) {
                if (player.getInventory().removeItem(item)) {
                    int heal = item.getHealAmount();
                    player.getHero().heal(heal);
                    log.info("{} uses {} and heals {} HP (now {}).", player.getHero().getName(), item.getName(), heal, player.getHero().getHealth());
                }
                return;
            }
        }

        // If no healing potion was used, discard other loot item
        for (Item item : items) {
            if (!item.isHealingPotion()) {
                if (player.getInventory().removeItem(item)) {
                    log.info("{} discards loot: {}.", player.getHero().getName(), item.getName());
                }
                return;
            }
        }

        log.info("{} takes no free actions.", player.getHero().getName());
    }
}
