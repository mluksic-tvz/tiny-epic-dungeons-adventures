package hr.game.tinyepicdungeonsadventures.core;

import hr.game.tinyepicdungeonsadventures.model.items.Item;
import hr.game.tinyepicdungeonsadventures.model.character.monster.Monster;
import hr.game.tinyepicdungeonsadventures.model.player.Player;
import hr.game.tinyepicdungeonsadventures.model.dungeon.Room;
import hr.game.tinyepicdungeonsadventures.ui.GameController;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DungeonDrawer {

    private final GridPane dungeonGridPane;
    private final GameController controller;

    public DungeonDrawer(GridPane dungeonGridPane, GameController controller) {
        this.dungeonGridPane = dungeonGridPane;
        this.controller = controller;
    }

    public void render(GameState state) {
        dungeonGridPane.getChildren().clear();
        controller.clearTargetSelection();

        Player currentPlayer = state.getCurrentPlayer();
        Room currentPlayersRoom = state.getCurrentRoom(currentPlayer);

        int col = 0;
        int row = 0;
        for (Room room : state.getDungeon().getRooms()) {
            if (room.isRevealed()) {
                VBox roomBox = createRoomBox(room);
                if (currentPlayersRoom != null && currentPlayersRoom.equals(room)) {
                    roomBox.getStyleClass().add("current-player-room");
                }
                dungeonGridPane.add(roomBox, col, row);
                col++;
                if (col % 5 == 0) { col = 0; row++; }
            }
        }
    }

    private VBox createRoomBox(Room room) {
        VBox roomBox = new VBox(5);
        roomBox.getStyleClass().add("room-box");
        Label roomIdLabel = new Label(room.getId());
        roomIdLabel.setStyle("-fx-text-fill: #fac888; -fx-font-family: 'MedievalSharp'; -fx-font-size: 16px;");
        roomBox.getChildren().add(roomIdLabel);

        for (Monster monster : room.getMonsters()) {
            Label monsterLabel = new Label(monster.getName() + " (HP: " + monster.getHealth() + ")");
            monsterLabel.setStyle("-fx-text-fill: #ff6b6b; -fx-font-weight: bold;");
            monsterLabel.setCursor(Cursor.HAND);
            monsterLabel.setOnMouseClicked(e -> controller.onMonsterClicked(monster, monsterLabel));
            roomBox.getChildren().add(monsterLabel);
        }

        for (Item item : room.getItems()) {
            Label itemLabel = new Label(item.getName());
            itemLabel.setStyle("-fx-text-fill: #ffd700;");
            itemLabel.setCursor(Cursor.HAND);
            itemLabel.setOnMouseClicked(e -> controller.onLootClicked(item));
            roomBox.getChildren().add(itemLabel);
        }

        return roomBox;
    }
}
