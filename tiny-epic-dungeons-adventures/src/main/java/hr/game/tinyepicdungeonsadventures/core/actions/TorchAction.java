package hr.game.tinyepicdungeonsadventures.core.actions;

import hr.game.tinyepicdungeonsadventures.core.GameState;
import hr.game.tinyepicdungeonsadventures.model.Monster;
import hr.game.tinyepicdungeonsadventures.model.MonsterType;
import hr.game.tinyepicdungeonsadventures.model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TorchAction extends Action {
    private static final Logger logger = LogManager.getLogger(TorchAction.class);
    private static final int MAX_TORCH = 5;

    @Override
    public void execute(GameState state, Player player) {
        state.advanceTorch();
        int position = state.getTorchPosition();
        logger.info("Torch moves to position {}.", position);

        switch (position) {
            case 1 -> {
                state.getPlayers().forEach(p -> p.getHero().takeDamage(1));
                logger.info("Trap triggered: all heroes take 1 damage!");
            }
            case 2 -> {
                Monster goblin = new Monster(MonsterType.GOBLIN);
                state.getDungeon().addMonsterToEntrance(goblin);
                logger.info("A Goblin appears at the dungeon entrance!");
            }
            case 3 -> {
                Monster orc = new Monster(MonsterType.ORC);
                state.getDungeon().addMonsterToEntrance(orc);
                logger.info("An Orc appears at the dungeon entrance!");
            }
            case 4 -> {
                Monster skeleton = new Monster(MonsterType.SKELETON);
                state.getDungeon().addMonsterToEntrance(skeleton);
                logger.info("A Skeleton appears at the dungeon entrance!");
            }
            case MAX_TORCH -> {
                state.endGame();
                logger.info("Torch has burned out – heroes lose the game!");
            }
            default -> logger.info("No special torch effects this turn.");
        }
    }
}