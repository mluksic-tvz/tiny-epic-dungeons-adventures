package hr.game.tinyepicdungeonsadventures.model.player;

import lombok.Data;

/**
 * A data class representing the configuration for a player.
 * <p>
 * This class holds specific settings related to a player, such as their type.
 * It is used to encapsulate player-specific configuration data.
 */
@Data
public class PlayerConfiguration {
    private PlayerType playerType;
}
