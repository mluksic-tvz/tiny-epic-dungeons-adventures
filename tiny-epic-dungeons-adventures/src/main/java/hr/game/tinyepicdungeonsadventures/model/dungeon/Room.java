package hr.game.tinyepicdungeonsadventures.model.dungeon;

import hr.game.tinyepicdungeonsadventures.model.character.monster.Monster;
import hr.game.tinyepicdungeonsadventures.model.items.Item;
import lombok.*;

import java.util.List;

/**
 * Represents a single room or location within the game's dungeon.
 * <p>
 * A {@code Room} contains a unique ID, a list of monsters, and a list of items.
 * It also tracks whether it has been revealed to the players. This class provides
 * methods to check the contents of the room, take items, remove monsters, and
 * reveal the room.
 */
@Getter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Room {
    private final String id;
    private final List<Monster> monsters;
    private final List<Item> items;
    private boolean revealed = false;

    /**
     * Checks if the room currently contains any monsters.
     *
     * @return {@code true} if the list of monsters is not empty, {@code false} otherwise.
     */
    public boolean hasMonsters() {
        return !monsters.isEmpty();
    }

    /**
     * Checks if the room currently contains any items.
     *
     * @return {@code true} if the list of items is not empty, {@code false} otherwise.
     */
    public boolean hasItems() {
        return !items.isEmpty();
    }

    /**
     * Attempts to take a specific item from the room.
     * <p>
     * If the item is present in the room's item list, it is removed.
     *
     * @param item The {@link Item} to be taken.
     * @return {@code true} if the item was successfully removed, {@code false} otherwise.
     */
    public boolean takeItem(Item item) {
        return items.remove(item);
    }

    /**
     * Marks the room as revealed.
     * <p>
     * Once a room is revealed, it is visible to the players on the game board.
     */
    public void reveal() {
        revealed = true;
    }

    /**
     * Removes a monster from the room.
     * <p>
     * This method is typically called when a monster has been defeated by a player.
     *
     * @param monster The {@link Monster} to be removed.
     * @return {@code true} if the monster was successfully removed, {@code false} otherwise.
     */
    public boolean removeMonster(Monster monster) {
        return monsters.remove(monster);
    }
}
