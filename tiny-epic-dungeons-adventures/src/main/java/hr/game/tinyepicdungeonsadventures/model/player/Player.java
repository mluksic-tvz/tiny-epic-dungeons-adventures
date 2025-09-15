package hr.game.tinyepicdungeonsadventures.model.player;

import hr.game.tinyepicdungeonsadventures.model.character.hero.Hero;
import hr.game.tinyepicdungeonsadventures.model.items.Inventory;
import hr.game.tinyepicdungeonsadventures.model.items.Item;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Player {
    private final String id;
    private Hero hero;
    private Inventory inventory = new Inventory();

    public void pickUpItem(Item item) {
        inventory.addItem(item);
    }

    public boolean useItem(Item item) {
        return inventory.removeItem(item);
    }
}
