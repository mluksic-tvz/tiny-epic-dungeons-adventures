package hr.game.tinyepicdungeonsadventures.model.character.hero;

import hr.game.tinyepicdungeonsadventures.model.character.Character;
import hr.game.tinyepicdungeonsadventures.model.items.Equipment;
import hr.game.tinyepicdungeonsadventures.model.items.Item;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a playable Hero character in the game.
 * <p>
 * This class extends the base {@link hr.game.tinyepicdungeonsadventures.model.character.Character} class
 * and adds hero-specific attributes and behaviors, such as mana, speed, spells, and equipment.
 * It also handles the logic for equipping items, casting spells, and calculating defensive bonuses.
 */
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

    /**
     * Reduces the hero's health by a specified amount, considering defense.
     * <p>
     * This method overrides the base {@code takeDamage} method to factor in
     * the hero's defense bonus from equipped items before calculating the
     * final damage taken.
     *
     * @param amount The amount of damage to be dealt.
     */
    @Override
    public void takeDamage(int amount) {
        int defense = getTotalDefenseBonus();
        int damageTaken = Math.max(0, amount - defense);

        if (defense > 0)
            log.info("{}'s equipment blocks {} damage!", getName(), defense);

        super.takeDamage(damageTaken);
    }

    /**
     * Adds a new spell to the hero's list of known spells.
     *
     * @param spell The {@link Spell} to be learned.
     */
    public void learnSpell(Spell spell) {
        spells.add(spell);
    }

    /**
     * Attempts to cast a spell on a target.
     * <p>
     * The spell is cast successfully only if the hero knows the spell and has
     * enough mana to pay for its cost. If successful, the mana is reduced,
     * and the target takes damage.
     *
     * @param spell  The {@link Spell} to cast.
     * @param target The {@link Character} to target with the spell.
     * @return {@code true} if the spell was successfully cast, {@code false} otherwise.
     */
    public boolean castSpell(Spell spell, Character target) {

        if (!spells.contains(spell) || mana < spell.getCost())
            return false;

        mana -= spell.getCost();
        target.takeDamage(spell.getPower());
        return true;
    }

    /**
     * Equips an item to the hero.
     * <p>
     * This method delegates the equipping logic to the hero's {@link Equipment} object.
     *
     * @param item The {@link Item} to equip.
     */
    public void equip(Item item) {
        equipment.equip(item);
    }

    /**
     * Calculates the total attack bonus from the hero's equipped weapon.
     *
     * @return The attack bonus from the equipped weapon, or 0 if no weapon is equipped.
     */
    public int getTotalAttackBonus() {
        if (equipment.getWeapon() != null) {
            return equipment.getWeapon().getAttackBonus();
        }
        return 0;
    }

    /**
     * Calculates the total defense bonus from the hero's equipped shield.
     *
     * @return The defense bonus from the equipped shield, or 0 if no shield is equipped.
     */
    public int getTotalDefenseBonus() {
        if (equipment.getShield() != null) {
            return equipment.getShield().getDefenseBonus();
        }
        return 0;
    }

    /**
     * Restores a specified amount of mana to the hero, up to their maximum mana.
     *
     * @param amount The amount of mana to restore.
     */
    public void regenerateMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
    }
}
