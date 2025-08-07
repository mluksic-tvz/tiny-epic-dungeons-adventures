package hr.game.tinyepicdungeonsadventures.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public abstract class Item {
    private final String name;

    public boolean isHealingPotion() {
        return false;
    }

    public int getHealAmount() {
        return 0;
    }
}
