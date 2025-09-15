package hr.game.tinyepicdungeonsadventures.model.items;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * An abstract base class for all items in the game.
 * <p>
 * This class provides fundamental properties for an item, such as its name and
 * any bonuses it provides to a character's attack or defense. It also includes
 * default methods for specific item types, which can be overridden by subclasses.
 */
@Getter
@ToString
@RequiredArgsConstructor
public abstract class Item {
    private final String name;
    private final int attackBonus;
    private final int defenseBonus;

    /**
     * Checks if this item is a healing potion.
     * <p>
     * Subclasses representing healing potions should override this method to return {@code true}.
     *
     * @return {@code true} if this item is a healing potion, {@code false} otherwise.
     */
    public boolean isHealingPotion() {
        return false;
    }

    /**
     * Returns the healing amount of this item.
     * <p>
     * Subclasses representing healing potions should override this method to return
     * the actual healing amount.
     *
     * @return The healing amount, or 0 if this item is not a healing potion.
     */
    public int getHealAmount() {
        return 0;
    }
}
