package hr.game.tinyepicdungeonsadventures.model;

import lombok.*;

import java.util.*;

@Getter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Dungeon {

    private final List<Room> rooms = new ArrayList<>();
    private final List<Item> lootPool = new ArrayList<>();
    private final List<Monster> monstersAtTheEntrance = new ArrayList<>();
    private Deque<Room> roomCardDeck;
    private Deque<Item> lootCardDeck;

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

