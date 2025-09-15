package hr.game.tinyepicdungeonsadventures.model.items;

import lombok.Getter;

/**
 * Represents the equipment slots for a character in the game.
 * <p>
 * This class holds the {@link Item}s currently equipped by a {@link hr.game.tinyepicdungeonsadventures.model.character.hero.Hero},
 * specifically a weapon and a shield. It provides functionality to equip new items
 * into the appropriate slots based on the item's properties.
 */
@Getter
public class Equipment {
    private Item weapon;
    private Item shield;

    /**
     * Equips an item to the appropriate slot (weapon or shield).
     * <p>
     * The method checks if the item provides an attack or defense bonus and
     * places it in the corresponding equipment slot.
     *
     * @param item The {@link Item} to be equipped.
     */
    public void equip(Item item) {
        if (item.getAttackBonus() > 0) {
            this.weapon = item;
        } else if (item.getDefenseBonus() > 0) {
            this.shield = item;
        }
    }
}
