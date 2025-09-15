package hr.game.tinyepicdungeonsadventures.model.character.monster;

import hr.game.tinyepicdungeonsadventures.model.character.Character;
import lombok.*;
import lombok.experimental.SuperBuilder;

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