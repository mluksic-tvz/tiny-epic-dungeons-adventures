package hr.game.tinyepicdungeonsadventures.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Spell {
    private String name;
    private int power;
    private int cost;
}
