package hr.game.tinyepicdungeonsadventures.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@SuperBuilder
@NoArgsConstructor(force = true)
public class Monster extends Character {
    public Monster(String name, int maxHealth) {
        super(name, maxHealth);
    }
}