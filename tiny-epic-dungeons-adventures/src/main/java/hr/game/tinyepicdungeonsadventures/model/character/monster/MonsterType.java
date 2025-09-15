package hr.game.tinyepicdungeonsadventures.model.character.monster;

import lombok.Getter;

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
