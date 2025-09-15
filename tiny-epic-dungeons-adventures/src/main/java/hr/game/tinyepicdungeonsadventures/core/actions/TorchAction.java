package hr.game.tinyepicdungeonsadventures.core.actions;

import hr.game.tinyepicdungeonsadventures.core.GameState;
import hr.game.tinyepicdungeonsadventures.model.Monster;
import hr.game.tinyepicdungeonsadventures.model.MonsterType;
import hr.game.tinyepicdungeonsadventures.model.Player;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TorchAction extends Action {
    private static final int MAX_TORCH = 5;

    @Override
    public void execute(GameState state, Player player) {
        state.advanceTorch();
        int position = state.getTorchPosition();
        log.info("Torch moves to position {}.", position);

        switch (position) {
            case 1 -> {
                state.getPlayers().forEach(p -> p.getHero().takeDamage(1));
                log.info("Trap triggered: all heroes take 1 damage!");
            }
            case 2 -> {
                Monster goblin = new Monster(MonsterType.GOBLIN);
                state.getDungeon().addMonsterToEntrance(goblin);
                log.info("A Goblin appears at the dungeon entrance!");
            }
            case 3 -> {
                Monster orc = new Monster(MonsterType.ORC);
                state.getDungeon().addMonsterToEntrance(orc);
                log.info("An Orc appears at the dungeon entrance!");
            }
            case 4 -> {
                Monster skeleton = new Monster(MonsterType.SKELETON);
                state.getDungeon().addMonsterToEntrance(skeleton);
                log.info("A Skeleton appears at the dungeon entrance!");
            }
            case MAX_TORCH -> {
                state.endGame();
                log.info("Torch has burned out – heroes lose the game!");
            }
            default -> log.info("No special torch effects this turn.");
        }
    }
}