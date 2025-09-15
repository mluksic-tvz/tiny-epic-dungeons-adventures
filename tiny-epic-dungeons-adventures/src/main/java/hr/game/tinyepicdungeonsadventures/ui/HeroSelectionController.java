package hr.game.tinyepicdungeonsadventures.ui;

import hr.game.tinyepicdungeonsadventures.TinyEpicDungeonsAdventures;
import hr.game.tinyepicdungeonsadventures.model.character.hero.Hero;
import hr.game.tinyepicdungeonsadventures.model.character.hero.Spell;
import hr.game.tinyepicdungeonsadventures.utils.DialogUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the HeroSelection UI.
 * <p>
 * This class manages the UI for players to select their heroes before the game starts.
 * It handles card clicks, tracks selected heroes, and transitions to the main game
 * screen once all players have made their selections.
 */
@Slf4j
public class HeroSelectionController {

    private static final String SELECTED_CARD_STYLE_CLASS = "hero-card-selected";

    @Setter
    private TinyEpicDungeonsAdventures app;

    @FXML
    private ImageView warriorCard;
    @FXML
    private ImageView rangerCard;
    @FXML
    private ImageView mageCard;
    @FXML
    private ImageView clericCard;
    @FXML
    private Button applyButton;
    @FXML
    private Button backButton;

    private int numberOfPlayersToSelect;
    private List<Hero> selectedHeroes = new ArrayList<>();
    private ImageView selectedHeroCard = null;

    public void initializeSelection(int numberOfPlayers) {
        this.numberOfPlayersToSelect = numberOfPlayers;
    }

    @FXML
    public void initialize() {
        warriorCard.setOnMouseClicked(this::onCardClicked);
        rangerCard.setOnMouseClicked(this::onCardClicked);
        mageCard.setOnMouseClicked(this::onCardClicked);
        clericCard.setOnMouseClicked(this::onCardClicked);

        applyButton.setOnAction(e -> onApplyClicked());
        backButton.setOnAction(e -> app.showPlayerSelection());
    }

    private void onCardClicked(MouseEvent event) {
        selectCard((ImageView) event.getSource());
    }

    private void selectCard(ImageView card) {
        if (selectedHeroCard != null) {
            selectedHeroCard.getStyleClass().remove(SELECTED_CARD_STYLE_CLASS);
        }

        selectedHeroCard = card;
        selectedHeroCard.getStyleClass().add(SELECTED_CARD_STYLE_CLASS);
    }

    private void onApplyClicked() {
        if (selectedHeroCard == null) {
            DialogUtils.showDialog(Alert.AlertType.WARNING, "Hero Not Selected", "Please select a hero to continue.", "");
            return;
        }

        Hero hero = createHeroFromSelection(selectedHeroCard.getId());
        selectedHeroes.add(hero);

        selectedHeroCard.setDisable(true);
        selectedHeroCard.setOpacity(0.5);

        if (selectedHeroes.size() == numberOfPlayersToSelect) {
            log.info("All heroes selected: {}. Starting game...", selectedHeroes);
            app.showGameScreen(selectedHeroes);

        } else {
            clearSelection();
        }
    }

    private void clearSelection() {
        if (selectedHeroCard != null) {
            selectedHeroCard.getStyleClass().remove(SELECTED_CARD_STYLE_CLASS);
            selectedHeroCard = null;
        }
    }

    private Hero createHeroFromSelection(String cardId) {
        return switch (cardId) {
            case "warriorCard" -> new Hero("Warrior", 8, 2, 3);
            case "rangerCard" -> new Hero("Ranger", 6, 4, 4);
            case "mageCard" -> {
                Hero mage = new Hero("Mage", 6, 8, 2);
                mage.learnSpell(new Spell("Fireball", 3, 2));
                yield mage;
            }
            case "clericCard" -> new Hero("Cleric", 9, 6, 2);
            default -> throw new IllegalStateException("Unknown card ID: " + cardId);
        };
    }
}
