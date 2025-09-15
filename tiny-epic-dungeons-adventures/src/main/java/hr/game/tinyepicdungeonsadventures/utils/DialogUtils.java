package hr.game.tinyepicdungeonsadventures.utils;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A utility class for displaying dialogs in UI.
 * <p>
 * This class provides a static method to show an {@link Alert} dialog,
 * ensuring that the operation is performed on the JavaFX application thread.
 * This is crucial for safely updating the UI from any thread.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DialogUtils {

    public static void showDialog(Alert.AlertType alertType, String title, String headerText, String contentText)
    {
        Platform.runLater( () -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(headerText);
            alert.setContentText(contentText);
            alert.showAndWait();
        });
    }
}
