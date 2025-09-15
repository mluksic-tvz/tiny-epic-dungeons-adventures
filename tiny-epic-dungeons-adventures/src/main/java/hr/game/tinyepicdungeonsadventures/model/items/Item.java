package hr.game.tinyepicdungeonsadventures.model.items;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public abstract class Item {
    private final String name;
    private final int attackBonus;
    private final int defenseBonus;

    public boolean isHealingPotion() {
        return false;
    }

    public int getHealAmount() {
        return 0;
    }
}
