package hr.game.tinyepicdungeonsadventures.ui;

import hr.game.tinyepicdungeonsadventures.TinyEpicDungeonsAdventures;
import hr.game.tinyepicdungeonsadventures.utils.DialogUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainMenuController {

    @Setter
    private TinyEpicDungeonsAdventures app;

    @FXML
    private Button startButton;
    @FXML
    private Button quitButton;

    @FXML
    private void initialize() {
        startButton.setOnAction(e -> {
            try {
                app.showPlayerSelection();
            } catch (Exception ex) {
                log.error("Failed to open HeroSelection scene", ex);
                DialogUtils.showDialog(Alert.AlertType.ERROR, "Unexpected Error", "Something went wrong", "Hero selection couldn't be loaded. Please try again.");
            }
        });

        quitButton.setOnAction(e -> Platform.exit());
    }
}
