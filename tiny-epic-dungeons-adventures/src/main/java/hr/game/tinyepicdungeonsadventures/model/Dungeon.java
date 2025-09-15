package hr.game.tinyepicdungeonsadventures.model;

import lombok.*;

import java.util.*;

@Getter
@ToString
public class Dungeon {

    private final List<Room> rooms;
    private final List<Item> lootPool;
    private final List<Monster> monstersAtTheEntrance;
    private Deque<Room> roomCardDeck;
    private Deque<Item> lootCardDeck;

    public Dungeon(List<Room> regularRooms, Room bossLair, List<Item> lootPool) {
        Collections.shuffle(regularRooms);

        this.rooms = new ArrayList<>(regularRooms);
        this.rooms.add(bossLair);
        this.roomCardDeck = new ArrayDeque<>(this.rooms);
        this.lootPool = new ArrayList<>(lootPool);

        Collections.shuffle(this.lootPool);
        this.lootCardDeck = new ArrayDeque<>(this.lootPool);

        this.monstersAtTheEntrance = new ArrayList<>();
    }

    public Room drawRoom() {
        return (roomCardDeck == null || roomCardDeck.isEmpty()) ? null : roomCardDeck.pop();
    }

    public Item drawLoot() {
        return (lootCardDeck == null || lootCardDeck.isEmpty()) ? null : lootCardDeck.pop();
    }

    public void addMonsterToEntrance(Monster monster) {
        monstersAtTheEntrance.add(monster);
    }
}

