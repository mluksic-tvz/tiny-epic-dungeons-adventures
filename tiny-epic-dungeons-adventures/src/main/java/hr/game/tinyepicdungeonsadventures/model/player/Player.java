package hr.game.tinyepicdungeonsadventures.model.player;

import hr.game.tinyepicdungeonsadventures.model.character.hero.Hero;
import hr.game.tinyepicdungeonsadventures.model.items.Inventory;
import hr.game.tinyepicdungeonsadventures.model.items.Item;
import lombok.*;

/**
 * Represents a player in the game.
 * <p>
 * This class links a human player with their in-game {@link Hero} character
 * and their {@link Inventory}. It contains methods for managing the player's
 * hero and their items, acting as a container for all player-related game state.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Player {
    private final String id;
    private Hero hero;
    private Inventory inventory = new Inventory();

    /**
     * Adds an item to the player's inventory.
     * <p>
     * This method is part of the player's item management capabilities.
     *
     * @param item The {@link Item} to be added.
     */
    public void pickUpItem(Item item) {
        inventory.addItem(item);
    }

    /**
     * Attempts to remove an item from the player's inventory.
     * <p>
     * This method is part of the player's item management capabilities.
     *
     * @param item The {@link Item} to be used and removed.
     * @return {@code true} if the item was successfully removed, {@code false} otherwise.
     */
    public boolean useItem(Item item) {
        return inventory.removeItem(item);
    }
}
