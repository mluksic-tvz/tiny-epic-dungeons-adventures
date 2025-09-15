package hr.game.tinyepicdungeonsadventures.model.character.monster;

import lombok.Getter;

/**
 * An enumeration of the different types of monsters in the game.
 * <p>
 * Each enum constant represents a specific monster type, defining its
 * name, maximum health, and attack power.
 */
public enum MonsterType {
    GOBLIN("Goblin", 2, 1),
    ORC("Orc Brute", 4, 2),
    SKELETON("Skeleton", 3, 1),
    BOSS("Dungeon Boss", 10, 3);

    @Getter
    private final String name;
    @Getter
    private final int maxHealth;
    @Getter
    private final int attackPower;

    MonsterType(String name, int maxHealth, int attackPower) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.attackPower = attackPower;
    }
}
