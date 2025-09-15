package hr.game.tinyepicdungeonsadventures.ui;

import hr.game.tinyepicdungeonsadventures.TinyEpicDungeonsAdventures;
import hr.game.tinyepicdungeonsadventures.chat.ChatManager;
import hr.game.tinyepicdungeonsadventures.core.*;
import hr.game.tinyepicdungeonsadventures.model.character.hero.Hero;
import hr.game.tinyepicdungeonsadventures.model.character.monster.Monster;
import hr.game.tinyepicdungeonsadventures.model.dungeon.Dungeon;
import hr.game.tinyepicdungeonsadventures.model.dungeon.Room;
import hr.game.tinyepicdungeonsadventures.model.items.Item;
import hr.game.tinyepicdungeonsadventures.model.player.Player;
import hr.game.tinyepicdungeonsadventures.model.player.PlayerStatusManager;
import hr.game.tinyepicdungeonsadventures.utils.DialogUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class GameController {

    @Setter
    private TinyEpicDungeonsAdventures app;

    @Setter
    private ChatManager chatManager;

    private GameEngine gameEngine;
    private GameTurnManager turnManager;
    private PlayerStatusManager playerStatusManager;
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
        List<Player> players = PlayerFactory.createPlayers(selectedHeroes);
        Dungeon dungeon = DungeonFactory.createDungeon();

        this.turnManager = new GameTurnManager();
        this.gameEngine = new GameEngine(players, dungeon);
        this.dungeonDrawer = new DungeonDrawer(dungeonGridPane, this);
        this.playerStatusManager = new PlayerStatusManager(heroNameLabel, healthProgressBar, healthValueLabel, manaProgressBar, manaValueLabel, inventoryListView);
        this.chatManager.initializeChat();

        gameEngine.startGame();
        log.info("Game started with {} players!", players.size());

        inventoryListView.setOnMouseClicked(event -> onInventoryItemClicked());
        updateUI();
    }

    @FXML
    private void onMoveClicked() {
        if (isCurrentPlayerDefeated())
            return;

        GameState state = gameEngine.getState();
        Player currentPlayer = state.getCurrentPlayer();

        log.info("{} attempts movement action.", currentPlayer.getHero().getName());
        turnManager.performMovementAction(state, currentPlayer);
        updateUI();
        moveButton.setDisable(true);
    }

    @FXML
    private void onHeroicActionClicked() {
        if (isCurrentPlayerDefeated())
            return;

        GameState state = gameEngine.getState();
        Player currentPlayer = state.getCurrentPlayer();

        if (selectedMonsterTarget != null) {
            log.info("{} attempts heroic action on {}.", currentPlayer.getHero().getName(), selectedMonsterTarget.getName());
            turnManager.performHeroicAction(state, currentPlayer, selectedMonsterTarget);
            state.checkForWinCondition();
            heroicActionButton.setDisable(true);
        } else {
            log.warn("Action button clicked, but no target is selected.");
            DialogUtils.showDialog(Alert.AlertType.WARNING, "No Target", "Please select a monster to perform an action.", "");
        }

        if (state.isVictory()) {
            updateUI();
            endTurnButton.setDisable(true);
            moveButton.setDisable(true);
            heroicActionButton.setDisable(true);
            DialogUtils.showDialog(Alert.AlertType.INFORMATION, "VICTORY!", "The heroes have defeated the Dungeon Boss!", "");
            return;
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
        playerStatusManager.update(currentPlayer);
        updateTorchTrack(currentState);
        dungeonDrawer.render(currentState);
        turnIndicatorText.setText("Turn: " + currentPlayer.getId() + " (" + currentPlayer.getHero().getName() + ")");
    }

    public void onInventoryItemClicked() {
        Item selectedItem = inventoryListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null || isCurrentPlayerDefeated())
            return;

        Player currentPlayer = gameEngine.getState().getCurrentPlayer();

        if (selectedItem.isHealingPotion()) {
            if (currentPlayer.getInventory().removeItem(selectedItem)) {
                int heal = selectedItem.getHealAmount();
                currentPlayer.getHero().heal(heal);
                log.info("{} uses {} and heals for {} HP.", currentPlayer.getHero().getName(), selectedItem.getName(), heal);
                updateUI();
            }
        } else if (selectedItem.getAttackBonus() > 0 || selectedItem.getDefenseBonus() > 0) {
            log.info("{} equips {}.", currentPlayer.getHero().getName(), selectedItem.getName());
            currentPlayer.getHero().equip(selectedItem);
            updateUI();
        } else {
            log.info("{} cannot be used or equipped in this context.", selectedItem.getName());
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

        if (selectedMonsterLabel != null)
            selectedMonsterLabel.setStyle("-fx-text-fill: #ff6b6b; -fx-font-weight: bold;");

        selectedMonsterLabel = null;
    }

    private void resetTurnUI() {
        Player newCurrentPlayer = gameEngine.getState().getCurrentPlayer();
        newCurrentPlayer.getHero().regenerateMana(1);
        log.info("{} regenerates 1 mana at the start of their turn. (Now {}/{})", newCurrentPlayer.getHero().getName(), newCurrentPlayer.getHero().getMana(), newCurrentPlayer.getHero().getMaxMana());
        moveButton.setDisable(false);
        heroicActionButton.setDisable(true);
        clearTargetSelection();
    }

    private boolean isCurrentPlayerDefeated() {
        Player currentPlayer = gameEngine.getState().getCurrentPlayer();
        if (!currentPlayer.getHero().isAlive()) {
            log.warn("Cannot perform action, hero {} is defeated.", currentPlayer.getHero().getName());
            return true;
        }
        return false;
    }
}
