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

    public Dungeon(List<Room> rooms, List<Item> lootPool) {
        this.rooms = new ArrayList<>(Objects.requireNonNull(rooms));
        this.lootPool = new ArrayList<>(Objects.requireNonNull(lootPool));
        this.monstersAtTheEntrance = new ArrayList<>();
    }

    public void shuffleAndPrepareCardDecks() {
        Collections.shuffle(lootPool);
        Collections.shuffle(rooms);
        roomCardDeck = new ArrayDeque<>(rooms);
        lootCardDeck = new ArrayDeque<>(lootPool);
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

