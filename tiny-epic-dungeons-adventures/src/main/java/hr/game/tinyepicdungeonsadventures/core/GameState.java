package hr.game.tinyepicdungeonsadventures.core;

import hr.game.tinyepicdungeonsadventures.model.character.monster.MonsterType;
import hr.game.tinyepicdungeonsadventures.model.dungeon.Dungeon;
import hr.game.tinyepicdungeonsadventures.model.dungeon.Room;
import hr.game.tinyepicdungeonsadventures.model.items.Item;
import hr.game.tinyepicdungeonsadventures.model.player.Player;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the complete state of the game.
 * <p>
 * It tracks all essential game data, including the list of players, the dungeon,
 * the current player's turn, the position of the torch, and
 * the overall game status (e.g., in progress, game over, or won). It provides methods
 * for initializing the game, advancing turns, and checking for win/loss conditions.
 */
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

    @Getter
    private boolean victory = false;

    private int currentPlayerIndex = 0;
    private final Map<Player, Room> currentRoomMap = new HashMap<>();

    public GameState(List<Player> players, Dungeon dungeon) {
        this.players = players;
        this.dungeon = dungeon;
    }

    public void initialize() {
        torchPosition = 0;
        log.info("Torch reset to position {}", torchPosition);

        log.info("Dungeon card decks have been prepared and shuffled.");

        for (Player player : players) {
            player.getInventory().clear();
            Item lootItem = dungeon.drawLoot();
            if (lootItem != null) {
                player.getInventory().addItem(lootItem);
                log.info("{} draws starting loot: {}",
                        player.getHero().getName(), lootItem.getName());
            }
        }

        Room entrance = dungeon.drawRoom();

        if (entrance != null) {
            entrance.reveal();
            log.info("Revealed entrance room: {}", entrance.getId());

            for (Player player : players) {
                setCurrentRoom(player, entrance);
                log.info("Player {} starts in {}.", player.getId(), entrance.getId());
            }
        }

        for (int i = 0; i < 2; i++) {
            Room adjacentRoom = dungeon.drawRoom();
            if (adjacentRoom != null) {
                adjacentRoom.reveal();
                log.info("Revealed adjacent room: {}", adjacentRoom.getId());
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

    public void endGame(boolean playerVictory) {

        if (isGameOver)
            return;

        isGameOver = true;
        this.victory = playerVictory;

        if (victory) {
            log.info("Game Over! The heroes won!");
        } else {
            log.info("Game Over! The heroes have been defeated.");
        }
    }

    public void checkForWinCondition() {
        boolean bossExistsAndIsAlive = dungeon.getRooms().stream()
                .flatMap(room -> room.getMonsters().stream())
                .anyMatch(monster -> monster.getType() == MonsterType.BOSS && monster.isAlive());

        if (!bossExistsAndIsAlive) {
            log.info("The Dungeon Boss has been defeated!");
            this.victory = true;
            endGame(true);
        }
    }

    public void checkForLossCondition() {
        boolean allPlayersDefeated = players.stream().allMatch(p -> !p.getHero().isAlive());

        if (allPlayersDefeated) {
            endGame(false);
        }
    }

    public void setCurrentRoom(Player player, Room room) {
        currentRoomMap.put(player, room);
    }

    public Room getCurrentRoom(Player player) {
        return currentRoomMap.get(player);
    }
}
