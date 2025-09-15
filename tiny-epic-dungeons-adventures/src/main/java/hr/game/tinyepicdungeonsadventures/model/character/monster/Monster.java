package hr.game.tinyepicdungeonsadventures.model.character.monster;

import hr.game.tinyepicdungeonsadventures.model.character.Character;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Represents a monster character in the game.
 * <p>
 * This class extends the base {@link hr.game.tinyepicdungeonsadventures.model.character.Character} class
 * and is specialized for monsters. It inherits health and name properties and
 * adds a specific {@link MonsterType} to define its characteristics.
 */
@Getter
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor(force = true)
public class Monster extends Character {
    private final MonsterType type;

    public Monster(MonsterType type) {
        super(type.getName(), type.getMaxHealth());
        this.type = type;
    }
}