package hr.game.tinyepicdungeonsadventures.core;

import hr.game.tinyepicdungeonsadventures.model.*;

import java.util.ArrayList;
import java.util.List;

public class DungeonFactory {
    public static Dungeon createTestDungeon() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Entrance Hall", new ArrayList<>(), new ArrayList<>(), true));
        rooms.add(new Room("Goblin Cave", new ArrayList<>(List.of(new Monster(MonsterType.GOBLIN))), new ArrayList<>(), false));
        rooms.add(new Room("Orc Barracks", new ArrayList<>(List.of(new Monster(MonsterType.ORC))), new ArrayList<>(List.of(new LootItem("Old Sword"))), false));
        rooms.add(new Room("Treasure Room", new ArrayList<>(), new ArrayList<>(List.of(new HealingPotion("Minor Healing Potion", 3))), false));
        rooms.add(new Room("Skeleton Crypt", new ArrayList<>(List.of(new Monster(MonsterType.SKELETON))), new ArrayList<>(), false));

        List<Item> lootPool = new ArrayList<>();
        lootPool.add(new LootItem("Magic Ring"));
        lootPool.add(new HealingPotion("Standard Healing Potion", 5));

        return new Dungeon(rooms, lootPool);
    }
}
