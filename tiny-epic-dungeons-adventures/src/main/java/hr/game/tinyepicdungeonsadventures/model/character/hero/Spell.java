package hr.game.tinyepicdungeonsadventures.model.character.hero;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a spell that can be learned and cast by a {@link Hero} in the game.
 * <p>
 * This class encapsulates the properties of a spell, including its name,
 * attack power, and mana cost.
 */
@Data
@AllArgsConstructor
public class Spell {
    private String name;
    private int power;
    private int cost;
}
