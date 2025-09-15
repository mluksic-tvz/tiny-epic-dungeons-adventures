package hr.game.tinyepicdungeonsadventures.model.items;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a character's inventory, which holds a collection of {@link Item}s.
 * <p>
 * This class provides methods for managing items, such as adding, removing,
 * and clearing the inventory. It uses a {@link List} to store the items.
 */
@Getter
@ToString
@NoArgsConstructor
public class Inventory {
    private List<Item> items = new ArrayList<>();

    /**
     * Adds an item to the inventory.
     *
     * @param item The {@link Item} to be added.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes a specific item from the inventory.
     *
     * @param item The {@link Item} to be removed.
     * @return {@code true} if the item was found and removed, {@code false} otherwise.
     */
    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    /**
     * Clears all items from the inventory.
     */
    public void clear() {
        items.clear();
    }
}
