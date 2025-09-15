package hr.game.tinyepicdungeonsadventures.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Slf4j
public abstract class Character {
    protected String name;
    protected int health;
    protected int maxHealth;

    protected Character(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public void takeDamage(int amount) {
        int healthBefore = this.health;
        this.health = Math.max(0, this.health - amount);

        if (healthBefore > 0 && this.health == 0) {
            log.warn("Character {} has been defeated!", this.name);
        }
    }

    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    public boolean isAlive() {
        return health > 0;
    }
}
