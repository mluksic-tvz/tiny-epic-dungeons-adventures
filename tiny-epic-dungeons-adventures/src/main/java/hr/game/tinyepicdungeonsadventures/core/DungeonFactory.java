package hr.game.tinyepicdungeonsadventures.core;

import hr.game.tinyepicdungeonsadventures.model.*;

import java.util.ArrayList;
import java.util.List;

public class DungeonFactory {
    public static Dungeon createTestDungeon() {
        List<Room> regularRooms = new ArrayList<>();
        regularRooms.add(new Room("Entrance Hall", new ArrayList<>(), new ArrayList<>(), false));
        regularRooms.add(new Room("Goblin Cave", new ArrayList<>(List.of(new Monster(MonsterType.GOBLIN))), new ArrayList<>(), false));
        regularRooms.add(new Room("Orc Barracks", new ArrayList<>(List.of(new Monster(MonsterType.ORC))), new ArrayList<>(List.of(new LootItem("Old Sword", 1, 0))), false));
        regularRooms.add(new Room("Treasure Room", new ArrayList<>(), new ArrayList<>(List.of(new HealingPotion("Minor Healing Potion", 3))), false));
        regularRooms.add(new Room("Skeleton Crypt", new ArrayList<>(List.of(new Monster(MonsterType.SKELETON))), new ArrayList<>(), false));
        regularRooms.add(new Room("Spider Nest", new ArrayList<>(List.of(new Monster(MonsterType.GOBLIN))), new ArrayList<>(), false)); // Koristimo Goblina kao placeholder za pauka
        regularRooms.add(new Room("Flooded Passage", new ArrayList<>(), new ArrayList<>(List.of(new LootItem("Magic Amulet", 0, 0))), false));
        regularRooms.add(new Room("Spike Trap Room", new ArrayList<>(), new ArrayList<>(), false));


        Room bossLair = new Room("The Boss's Lair", new ArrayList<>(List.of(new Monster(MonsterType.BOSS))), new ArrayList<>(), false);

        List<Item> lootPool = new ArrayList<>();
        lootPool.add(new LootItem("Magic Ring", 0, 0));
        lootPool.add(new HealingPotion("Standard Healing Potion", 5));
        lootPool.add(new LootItem("Wooden Shield", 0, 1));
        lootPool.add(new LootItem("Dagger", 1, 0));

        return new Dungeon(regularRooms, bossLair, lootPool);
    }
}
