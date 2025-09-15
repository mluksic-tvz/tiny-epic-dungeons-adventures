package hr.game.tinyepicdungeonsadventures.ui;

import hr.game.tinyepicdungeonsadventures.TinyEpicDungeonsAdventures;
import hr.game.tinyepicdungeonsadventures.chat.ChatManager;
import hr.game.tinyepicdungeonsadventures.core.*;
import hr.game.tinyepicdungeonsadventures.model.*;
import hr.game.tinyepicdungeonsadventures.utils.DialogUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GameController {

    @Setter
    private TinyEpicDungeonsAdventures app;

    @Setter
    private ChatManager chatManager;

    private GameEngine gameEngine;
    private GameTurnManager turnManager;
    private DungeonDrawer dungeonDrawer;

    @FXML private Label heroNameLabel;
    @FXML private ProgressBar healthProgressBar;
    @FXML private Label healthValueLabel;
    @FXML private ProgressBar manaProgressBar;
    @FXML private Label manaValueLabel;
    @FXML private ListView<Item> inventoryListView;
    @FXML private Text turnIndicatorText;
    @FXML private GridPane dungeonGridPane;
    @FXML private Label torchStateLabel;
    @FXML private Button moveButton;
    @FXML private Button heroicActionButton;
    @FXML private Button endTurnButton;

    private Monster selectedMonsterTarget = null;
    private Label selectedMonsterLabel = null;

    public void initializeGame(List<Hero> selectedHeroes) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < selectedHeroes.size(); i++) {
            Player player = new Player("Player " + (i + 1));
            player.setHero(selectedHeroes.get(i));
            players.add(player);
        }

        Dungeon dungeon = DungeonFactory.createTestDungeon();

        this.turnManager = new GameTurnManager();
        this.gameEngine = new GameEngine(players, dungeon);
        this.dungeonDrawer = new DungeonDrawer(dungeonGridPane, this);
        this.chatManager.initializeChat();
        gameEngine.startGame();

        log.info("Game started with {} players!", players.size());

        inventoryListView.setOnMouseClicked(event -> onInventoryItemClicked());
        updateUI();
    }

    @FXML
    private void onMoveClicked() {
        GameState state = gameEngine.getState();
        Player currentPlayer = state.getCurrentPlayer();
        log.info("{} attempts movement action.", currentPlayer.getHero().getName());

        turnManager.performMovementAction(state, currentPlayer);

        updateUI();
        moveButton.setDisable(true);
    }

    @FXML
    private void onHeroicActionClicked() {
        GameState state = gameEngine.getState();
        Player currentPlayer = state.getCurrentPlayer();

        if (selectedMonsterTarget != null) {
            log.info("{} attempts heroic action on {}.", currentPlayer.getHero().getName(), selectedMonsterTarget.getName());
            turnManager.performHeroicAction(state, currentPlayer, selectedMonsterTarget);
            heroicActionButton.setDisable(true);
        } else {
            log.warn("Action button clicked, but no target is selected.");
            DialogUtils.showDialog(Alert.AlertType.WARNING, "No Target", "Please select a monster to perform an action.", "");
        }
        updateUI();
    }

    @FXML
    private void onEndTurnClicked() {
        GameState state = gameEngine.getState();
        Player currentPlayer = state.getCurrentPlayer();
        log.info("{} ends their turn.", currentPlayer.getHero().getName());

        turnManager.endPlayerTurn(state, currentPlayer);

        state.checkForLossCondition();

        if (state.isGameOver()) {
            updateUI();
            endTurnButton.setDisable(true);
            moveButton.setDisable(true);
            heroicActionButton.setDisable(true);
            DialogUtils.showDialog(Alert.AlertType.INFORMATION, "Game Over", "The heroes have been defeated or the torch has burned out!", "");
            return;
        }

        gameEngine.advanceTurn();
        resetTurnUI();
        updateUI();
    }

    private void updateUI() {
        GameState currentState = gameEngine.getState();
        Player currentPlayer = currentState.getCurrentPlayer();

        updatePlayerInfo(currentPlayer);
        updateTorchTrack(currentState);
        dungeonDrawer.render(currentState);

        turnIndicatorText.setText("Turn: " + currentPlayer.getId() + " (" + currentPlayer.getHero().getName() + ")");
    }

    private void updatePlayerInfo(Player currentPlayer) {
        Hero hero = currentPlayer.getHero();
        heroNameLabel.setText("Hero: " + hero.getName());
        healthProgressBar.setProgress((double) hero.getHealth() / hero.getMaxHealth());
        healthValueLabel.setText(hero.getHealth() + " / " + hero.getMaxHealth());
        manaProgressBar.setProgress((double) hero.getMana() / hero.getMaxMana());
        manaValueLabel.setText(hero.getMana() + " / " + hero.getMaxMana());
        inventoryListView.getItems().clear();
        inventoryListView.getItems().addAll(currentPlayer.getInventory().getItems());
        inventoryListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }

    public void onInventoryItemClicked() {
        Item selectedItem = inventoryListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            return;
        }

        if (selectedItem.isHealingPotion()) {
            Player currentPlayer = gameEngine.getState().getCurrentPlayer();

            if (currentPlayer.getInventory().removeItem(selectedItem)) {
                int heal = selectedItem.getHealAmount();
                currentPlayer.getHero().heal(heal);
                log.info("{} uses {} and heals for {} HP.", currentPlayer.getHero().getName(), selectedItem.getName(), heal);
                updateUI();
            }
        } else {
            log.info("{} is not a usable item in this context.", selectedItem.getName());
        }
    }

    private void updateTorchTrack(GameState state) {
        int currentPosition = state.getTorchPosition();
        torchStateLabel.setText(String.format("Torch: %d / 5", currentPosition));
    }

    public void onMonsterClicked(Monster monster, Label monsterLabel) {
        clearTargetSelection();

        selectedMonsterTarget = monster;
        selectedMonsterLabel = monsterLabel;
        log.info("Selected target: {}", monster.getName());

        selectedMonsterLabel.setStyle("-fx-text-fill: #ff6b6b; -fx-font-weight: bold; -fx-border-color: red; -fx-border-width: 1;");
        heroicActionButton.setDisable(false);
    }

    public void onLootClicked(Item item) {
        GameState state = gameEngine.getState();
        Player currentPlayer = state.getCurrentPlayer();
        Room currentRoom = state.getCurrentRoom(currentPlayer);

        if (currentRoom != null && currentRoom.getItems().contains(item)) {
            currentRoom.takeItem(item);
            currentPlayer.getInventory().addItem(item);
            log.info("{} picks up loot: {}", currentPlayer.getHero().getName(), item.getName());
            updateUI();
        }
    }

    public void clearTargetSelection() {
        selectedMonsterTarget = null;
        if (selectedMonsterLabel != null) {
            selectedMonsterLabel.setStyle("-fx-text-fill: #ff6b6b; -fx-font-weight: bold;");
        }
        selectedMonsterLabel = null;
    }

    private void resetTurnUI() {
        moveButton.setDisable(false);
        heroicActionButton.setDisable(true);
        clearTargetSelection();
    }
}
