package hr.game.tinyepicdungeonsadventures.ui;

import hr.game.tinyepicdungeonsadventures.TinyEpicDungeonsAdventures;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainMenuController {

    private static final Logger log = LogManager.getLogger(MainMenuController.class);

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
                app.showHeroSelection();
            } catch (Exception ex) {
                log.error("Failed to open HeroSelection scene", ex);

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Unexpected Error");
                alert.setHeaderText("Something went wrong");
                alert.setContentText("Hero selection couldn't be loaded. Please try again.");
                alert.showAndWait();
            }
        });

        quitButton.setOnAction(e -> Platform.exit());
    }
}
