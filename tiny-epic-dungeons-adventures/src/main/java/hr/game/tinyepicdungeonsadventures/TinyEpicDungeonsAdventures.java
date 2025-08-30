package hr.game.tinyepicdungeonsadventures;

import hr.game.tinyepicdungeonsadventures.ui.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TinyEpicDungeonsAdventures extends Application {

    private Stage primaryStage;
    private static final Logger log = LogManager.getLogger(TinyEpicDungeonsAdventures.class);

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        try {
            Font.loadFont(getClass().getResource("/hr/game/tinyepicdungeonsadventures/fonts/MedievalSharp-Regular.ttf").toExternalForm(), 10);
        } catch (Exception e) {
            log.error("Failed to load custom font", e);
        }

        showMainMenu();
    }

    private void showMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/game/tinyepicdungeonsadventures/ui/mainmenu/MainMenu.fxml"));
            Scene scene = new Scene(loader.load(), 1940, 1080);
            scene.getStylesheets().add(getClass().getResource("/hr/game/tinyepicdungeonsadventures/ui/global.css").toExternalForm());

            MainMenuController controller = loader.getController();
            controller.setApp(this);

            primaryStage.setTitle("Tiny Epic Dungeon Adventures");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            log.error("Error while loading MainMenu scene", e);
        }
    }

    public void showHeroSelection() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/game/tinyepicdungeonsadventures/ui/heroselection/HeroSelection.fxml"));
            Scene scene = new Scene(loader.load(), 1940, 1080);
            scene.getStylesheets().add(getClass().getResource("/hr/game/tinyepicdungeonsadventures/ui/heroselection/hero-selection.css").toExternalForm());

            primaryStage.setTitle("Choose Your Hero");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            log.error("Error while loading HeroSelection scene", e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}