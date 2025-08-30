package hr.game.tinyepicdungeonsadventures.core;

import hr.game.tinyepicdungeonsadventures.model.Dungeon;
import hr.game.tinyepicdungeonsadventures.model.Item;
import hr.game.tinyepicdungeonsadventures.model.Player;
import hr.game.tinyepicdungeonsadventures.model.Room;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class GameState {

    private static final int MAX_TORCH = 5;

    @Getter
    private final List<Player> players;

    @Getter
    private final Dungeon dungeon;

    @Getter
    private int torchPosition = 0;

    @Getter
    private boolean isGameOver = false;

    private int currentPlayerIndex = 0;
    private final Map<Player, Room> currentRoomMap = new HashMap<>();

    public GameState(List<Player> players, Dungeon dungeon) {
        this.players = players;
        this.dungeon = dungeon;
    }

    public void initialize() {
        torchPosition = 0;
        log.info("Torch reset to position {}", torchPosition);

        dungeon.shuffleAndPrepareCardDecks();
        log.info("Shuffled and prepared room & loot decks");

        for (Player player : players) {
            player.getInventory().clear();
            Item lootItem = dungeon.drawLoot();
            if (lootItem != null) {
                player.getInventory().addItem(lootItem);
                log.info("{} draws starting loot: {}",
                        player.getHero().getName(), lootItem.getName());
            }
        }

        for (int i = 0; i < 3; i++) {
            Room room = dungeon.drawRoom();
            if (room != null) {
                room.reveal();
                log.info("Revealed starting room: {}", room.getId());
            }
        }
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Player getNextPlayer(Player current) {
        int playerIndex = players.indexOf(current);
        return players.get((playerIndex + 1) % players.size());
    }

    public void advanceTurn() {
        if (isGameOver) {
            log.warn("Game is already over, cannot advance turn");
            return;
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        log.info("Turn advanced to player index {}", currentPlayerIndex);
    }

    public void advanceTorch() {
        if (isGameOver) {
            log.warn("Game is already over, cannot advance torch");
            return;
        }
        torchPosition = (torchPosition + 1) % (MAX_TORCH + 1);
        log.info("Torch advanced to position {}", torchPosition);
    }

    public void endGame() {
        isGameOver = true;
        log.info("Game over! Heroes have lost.");
    }

    public void setCurrentRoom(Player player, Room room) {
        currentRoomMap.put(player, room);
    }

    public Room getCurrentRoom(Player player) {
        return currentRoomMap.get(player);
    }
}
