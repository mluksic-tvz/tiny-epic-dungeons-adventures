package hr.game.tinyepicdungeonsadventures;

import hr.game.tinyepicdungeonsadventures.chat.ChatManager;
import hr.game.tinyepicdungeonsadventures.model.ApplicationConfiguration;
import hr.game.tinyepicdungeonsadventures.model.Hero;
import hr.game.tinyepicdungeonsadventures.ui.GameController;
import hr.game.tinyepicdungeonsadventures.ui.HeroSelectionController;
import hr.game.tinyepicdungeonsadventures.ui.MainMenuController;
import hr.game.tinyepicdungeonsadventures.ui.PlayerSelectionController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TinyEpicDungeonsAdventures extends Application {

    public static final ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();

    private Stage primaryStage;

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

    public void showMainMenu() {
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

    public void showHeroSelection(int numberOfPlayers) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/game/tinyepicdungeonsadventures/ui/heroselection/HeroSelection.fxml"));
            Scene scene = new Scene(loader.load(), 1940, 1080);
            scene.getStylesheets().add(getClass().getResource("/hr/game/tinyepicdungeonsadventures/ui/heroselection/hero-selection.css").toExternalForm());

            HeroSelectionController controller = loader.getController();
            controller.setApp(this);
            controller.initializeSelection(numberOfPlayers);

            primaryStage.setTitle("Choose Your Hero");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            log.error("Error while loading HeroSelection scene", e);
        }
    }

    public void showPlayerSelection() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/game/tinyepicdungeonsadventures/ui/playerselection/PlayerSelection.fxml"));
            Scene scene = new Scene(loader.load(), 1940, 1080);

            PlayerSelectionController controller = loader.getController();
            controller.setApp(this);

            primaryStage.setTitle("Select Number of Players");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            log.error("Error while loading PlayerSelection scene", e);
        }
    }

    public void showGameScreen(List<Hero> selectedHeroes) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hr/game/tinyepicdungeonsadventures/ui/game/Game.fxml"));
            Scene scene = new Scene(loader.load(), 1920, 1080);

            GameController controller = loader.getController();
            controller.setApp(this);

            ChatManager chatManager = new ChatManager(
                    (TextArea) scene.lookup("#chatTextArea"),
                    (TextField) scene.lookup("#chatInputField"),
                    (Button) scene.lookup("#sendMessageButton")
            );

            controller.setChatManager(chatManager);

            controller.initializeGame(selectedHeroes);

            primaryStage.setTitle("Tiny Epic Dungeon Adventures - Game");
            primaryStage.setScene(scene);

            primaryStage.setOnCloseRequest(e -> Platform.exit());

            primaryStage.show();
        } catch (Exception e) {
            log.error("Error while loading Game scene", e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}