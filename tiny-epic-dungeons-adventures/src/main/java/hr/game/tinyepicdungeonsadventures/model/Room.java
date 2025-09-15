package hr.game.tinyepicdungeonsadventures.model;

import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Room {
    private final String id;
    private final List<Monster> monsters;
    private final List<Item> items;
    private boolean revealed = false;

    public boolean hasMonsters() {
        return !monsters.isEmpty();
    }

    public boolean hasItems() {
        return !items.isEmpty();
    }

    public boolean takeItem(Item item) {
        return items.remove(item);
    }

    public void reveal() {
        revealed = true;
    }

    public boolean removeMonster(Monster monster) {
        return monsters.remove(monster);
    }
}
