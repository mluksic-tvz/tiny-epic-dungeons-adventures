package hr.game.tinyepicdungeonsadventures.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class HealingPotion extends Item {
    private final int healAmount;

    public HealingPotion(String name, int healAmount) {
        super(name);
        this.healAmount = healAmount;
    }

    @Override
    public boolean isHealingPotion() {
        return true;
    }

    @Override
    public int getHealAmount() {
        return healAmount;
    }
}
