package hr.game.tinyepicdungeonsadventures.model.items;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LootItem extends Item {

    public LootItem(String name, int attackBonus, int defenseBonus) {
        super(name, attackBonus, defenseBonus);
    }
}
