package hr.game.tinyepicdungeonsadventures.model.items;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Represents a generic loot item that can be found in the dungeon.
 * <p>
 * This class extends the base {@link Item} and serves as a concrete
 * implementation for items that provide bonuses to a character's
 * attack or defense.
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LootItem extends Item {

    public LootItem(String name, int attackBonus, int defenseBonus) {
        super(name, attackBonus, defenseBonus);
    }
}
