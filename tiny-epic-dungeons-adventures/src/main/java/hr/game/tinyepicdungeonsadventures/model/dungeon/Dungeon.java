package hr.game.tinyepicdungeonsadventures.model.dungeon;

import hr.game.tinyepicdungeonsadventures.model.character.monster.Monster;
import hr.game.tinyepicdungeonsadventures.model.items.Item;
import lombok.*;

import java.util.*;

/**
 * Represents the dungeon in the game, managing its rooms, loot, and monsters.
 * <p>
 * The dungeon is responsible for storing and providing access to the various
 * game elements that players can encounter, such as rooms to be explored,
 * loot to be found, and monsters to be fought. It uses decks (stacks of cards)
 * for rooms and loot to simulate the exploration and discovery process.
 */
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

    /**
     * Draws a room from the top of the room card deck.
     *
     * @return The next {@link Room} from the deck, or {@code null} if the deck is empty.
     */
    public Room drawRoom() {
        return (roomCardDeck == null || roomCardDeck.isEmpty()) ? null : roomCardDeck.pop();
    }

    /**
     * Draws an item from the top of the loot card deck.
     *
     * @return The next {@link Item} from the deck, or {@code null} if the deck is empty.
     */
    public Item drawLoot() {
        return (lootCardDeck == null || lootCardDeck.isEmpty()) ? null : lootCardDeck.pop();
    }

    /**
     * Adds a monster to the list of monsters at the dungeon entrance.
     *
     * @param monster The {@link Monster} to be added.
     */
    public void addMonsterToEntrance(Monster monster) {
        monstersAtTheEntrance.add(monster);
    }
}

