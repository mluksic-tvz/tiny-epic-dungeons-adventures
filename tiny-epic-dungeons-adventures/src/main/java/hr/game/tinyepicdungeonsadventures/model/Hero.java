package hr.game.tinyepicdungeonsadventures.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Hero extends Character {
    private int mana;
    private int maxMana;
    private List<Spell> spells = new ArrayList<>();

    public Hero(String name, int maxHealth, int maxMana) {
        super(name, maxHealth);
        this.maxMana = maxMana;
        this.mana = maxMana;
    }

    public void learnSpell(Spell spell) {
        spells.add(spell);
    }

    public boolean castSpell(Spell spell, Character target) {
        if (!spells.contains(spell) || mana < spell.getCost()) return false;
        mana -= spell.getCost();
        target.takeDamage(spell.getPower());
        return true;
    }

    public void regenerateMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
    }
}
