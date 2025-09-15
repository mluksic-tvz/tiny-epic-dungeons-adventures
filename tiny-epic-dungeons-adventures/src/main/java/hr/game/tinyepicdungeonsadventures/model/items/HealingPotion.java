package hr.game.tinyepicdungeonsadventures.model.items;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a healing potion, a type of {@link Item} that restores a character's health.
 * <p>
 * This class extends the base {@link Item} class and adds a specific property for the
 * amount of health it restores. It overrides methods to correctly identify itself
 * as a healing potion and to provide its heal amount.
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class HealingPotion extends Item {
    private final int healAmount;

    public HealingPotion(String name, int healAmount) {
        super(name, 0, 0);
        this.healAmount = healAmount;
    }

    /**
     * Overrides the base method to confirm that this item is a healing potion.
     *
     * @return {@code true}, as this is a healing potion.
     */
    @Override
    public boolean isHealingPotion() {
        return true;
    }

    /**
     * Overrides the base method to return the specific heal amount of this potion.
     *
     * @return The amount of health this potion restores.
     */
    @Override
    public int getHealAmount() {
        return healAmount;
    }
}
