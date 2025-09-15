package hr.game.tinyepicdungeonsadventures.ui;

import hr.game.tinyepicdungeonsadventures.TinyEpicDungeonsAdventures;
import hr.game.tinyepicdungeonsadventures.model.PlayerType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static hr.game.tinyepicdungeonsadventures.TinyEpicDungeonsAdventures.applicationConfiguration;

@Slf4j
public class PlayerSelectionController {

    @Setter
    private TinyEpicDungeonsAdventures app;

    @FXML
    private Button onePlayerButton;
    @FXML
    private Button twoPlayersButton;
    @FXML
    private Button backButton;

    @FXML
    public void initialize() {

        onePlayerButton.setOnAction(e -> {
            applicationConfiguration.setPlayerType(PlayerType.PLAYER_ONE);
            app.showHeroSelection(1);
        });

        twoPlayersButton.setOnAction(e -> {
            applicationConfiguration.setPlayerType(PlayerType.PLAYER_ONE);
            app.showHeroSelection(2);
        });

        backButton.setOnAction(e -> app.showMainMenu());
    }
}
