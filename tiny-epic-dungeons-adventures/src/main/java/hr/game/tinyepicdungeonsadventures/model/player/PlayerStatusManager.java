package hr.game.tinyepicdungeonsadventures.model.player;

import hr.game.tinyepicdungeonsadventures.model.character.hero.Hero;
import hr.game.tinyepicdungeonsadventures.model.items.Item;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;

public class PlayerStatusManager {

    private final Label heroNameLabel;
    private final ProgressBar healthProgressBar;
    private final Label healthValueLabel;
    private final ProgressBar manaProgressBar;
    private final Label manaValueLabel;
    private final ListView<Item> inventoryListView;

    public PlayerStatusManager(Label heroNameLabel, ProgressBar healthProgressBar, Label healthValueLabel, ProgressBar manaProgressBar, Label manaValueLabel, ListView<Item> inventoryListView) {
        this.heroNameLabel = heroNameLabel;
        this.healthProgressBar = healthProgressBar;
        this.healthValueLabel = healthValueLabel;
        this.manaProgressBar = manaProgressBar;
        this.manaValueLabel = manaValueLabel;
        this.inventoryListView = inventoryListView;
    }

    public void update(Player currentPlayer) {
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
}
