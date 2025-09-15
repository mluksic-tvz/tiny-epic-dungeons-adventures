package hr.game.tinyepicdungeonsadventures.model.dungeon;

import hr.game.tinyepicdungeonsadventures.model.character.monster.Monster;
import hr.game.tinyepicdungeonsadventures.model.items.Item;
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
