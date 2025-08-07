package hr.game.tinyepicdungeonsadventures.model;

import lombok.Getter;

public enum MonsterType {
    GOBLIN("Goblin", 2),
    ORC("Orc Brute", 4),
    SKELETON("Skeleton", 3);

    @Getter
    private final String name;
    @Getter
    private final int maxHealth;

    MonsterType(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
    }
}
