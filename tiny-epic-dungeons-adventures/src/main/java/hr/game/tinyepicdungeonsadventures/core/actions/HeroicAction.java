package hr.game.tinyepicdungeonsadventures.core.actions;

import hr.game.tinyepicdungeonsadventures.core.GameState;
import hr.game.tinyepicdungeonsadventures.model.Monster;
import hr.game.tinyepicdungeonsadventures.model.Player;
import hr.game.tinyepicdungeonsadventures.model.Room;
import hr.game.tinyepicdungeonsadventures.model.Spell;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class HeroicAction extends Action {
    private static final int BASE_DAMAGE = 1;

    @Override
    public void execute(GameState state, Player player) {
        log.warn("Heroic action cannot be called without target! Please select a target.");
    }

    @Override
    public void execute(GameState state, Player player, Monster target) {
        Room currentRoom = state.getCurrentRoom(player);

        if (currentRoom == null || !currentRoom.getMonsters().contains(target)) {
            log.warn("Player {} is trying to attack a target {} which is not in the same room.", player.getHero().getName(), target.getName());
            return;
        }

        boolean spellWasCast = castSpell(player, target);

        if (!spellWasCast) {
            attack(player, target);
        }

        if (target.isAlive()) {
            log.info("{} retaliates!", target.getName());
            player.getHero().takeDamage(target.getType().getAttackPower());
            log.info("The monster {} attacks {} for {} damage (HP now {}).", target.getName(), player.getHero().getName(), target.getType().getAttackPower(), player.getHero().getHealth());
        } else {
            log.info("{} is slain!", target.getName());
            state.getCurrentRoom(player).removeMonster(target);
        }
    }

    private boolean castSpell(Player player, Monster target) {
        List<Spell> spells = player.getHero().getSpells();

        if (spells.isEmpty()) {
            return false;
        }

        Spell spellToCast = spells.getFirst();

        if (player.getHero().getMana() < spellToCast.getCost()) {
            log.info("{} lacks mana ({}/{}) for {}.", player.getHero().getName(), player.getHero().getMana(), spellToCast.getCost(), spellToCast.getName());
            return false;
        }

        player.getHero().castSpell(spellToCast, target);
        log.info("{} casts {} (power {}) on {} (HP now {}).", player.getHero().getName(), spellToCast.getName(), spellToCast.getPower(), target.getName(), target.getHealth());

        return true;
    }

    private void attack(Player player, Monster target) {
        log.info("Not enough mana for a spell, performing a basic attack.");
        target.takeDamage(BASE_DAMAGE);
        log.info("{} attacks {} for {} damage (HP now {}).", player.getHero().getName(), target.getName(), BASE_DAMAGE, target.getHealth());
    }
}