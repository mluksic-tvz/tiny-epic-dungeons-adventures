package hr.game.tinyepicdungeonsadventures.model;

import lombok.Getter;

@Getter
public class Equipment {
    private Item weapon;
    private Item shield;

    public void equip(Item item) {
        if (item.getAttackBonus() > 0) {
            this.weapon = item;
        } else if (item.getDefenseBonus() > 0) {
            this.shield = item;
        }
    }
}
