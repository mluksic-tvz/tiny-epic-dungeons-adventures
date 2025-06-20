package hr.game.tinyepicdungeonsadventures.model;

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
