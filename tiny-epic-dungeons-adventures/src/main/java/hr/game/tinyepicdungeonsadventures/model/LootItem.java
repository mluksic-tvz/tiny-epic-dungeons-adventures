package hr.game.tinyepicdungeonsadventures.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LootItem extends Item {

    public LootItem(String name) {
        super(name);
    }
}
