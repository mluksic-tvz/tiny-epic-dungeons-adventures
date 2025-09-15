package hr.game.tinyepicdungeonsadventures.model.character.hero;

import hr.game.tinyepicdungeonsadventures.model.character.Character;
import hr.game.tinyepicdungeonsadventures.model.items.Equipment;
import hr.game.tinyepicdungeonsadventures.model.items.Item;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Slf4j
public class Hero extends hr.game.tinyepicdungeonsadventures.model.character.Character {
    private int mana;
    private int maxMana;
    private int speed;
    private int maxSpeed;
    private List<Spell> spells = new ArrayList<>();
    private Equipment equipment = new Equipment();

    public Hero(String name, int maxHealth, int maxMana, int speed) {
        super(name, maxHealth);
        this.maxMana = maxMana;
        this.mana = maxMana;
        this.maxSpeed = speed;
        this.speed = speed;
    }

    @Override
    public void takeDamage(int amount) {
        int defense = getTotalDefenseBonus();
        int damageTaken = Math.max(0, amount - defense);

        if (defense > 0)
            log.info("{}'s equipment blocks {} damage!", getName(), defense);

        super.takeDamage(damageTaken);
    }

    public void learnSpell(Spell spell) {
        spells.add(spell);
    }

    public boolean castSpell(Spell spell, Character target) {

        if (!spells.contains(spell) || mana < spell.getCost())
            return false;

        mana -= spell.getCost();
        target.takeDamage(spell.getPower());
        return true;
    }

    public void equip(Item item) {
        equipment.equip(item);
    }

    public int getTotalAttackBonus() {
        if (equipment.getWeapon() != null) {
            return equipment.getWeapon().getAttackBonus();
        }
        return 0;
    }

    public int getTotalDefenseBonus() {
        if (equipment.getShield() != null) {
            return equipment.getShield().getDefenseBonus();
        }
        return 0;
    }

    public void regenerateMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
    }
}
