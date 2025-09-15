package hr.game.tinyepicdungeonsadventures.model;

import hr.game.tinyepicdungeonsadventures.model.player.PlayerType;
import lombok.Data;

@Data
public class AppConfiguration {
    private PlayerType playerType;
}
