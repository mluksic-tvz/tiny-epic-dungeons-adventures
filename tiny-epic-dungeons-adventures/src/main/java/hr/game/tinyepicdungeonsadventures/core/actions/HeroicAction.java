package hr.game.tinyepicdungeonsadventures.core.actions;

import hr.game.tinyepicdungeonsadventures.core.GameState;
import hr.game.tinyepicdungeonsadventures.model.Monster;
import hr.game.tinyepicdungeonsadventures.model.Player;
import hr.game.tinyepicdungeonsadventures.model.Room;
import hr.game.tinyepicdungeonsadventures.model.Spell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class HeroicAction extends Action {
    private static final Logger log = LogManager.getLogger(HeroicAction.class);
    private static final int BASE_DAMAGE = 1;

    @Override
    public void execute(GameState state, Player player) {
        // Get the latest room where player finished movement action
        Room room = state.getCurrentRoom(player);

        if (room == null) {
            log.warn("{} has no current room set—skipping heroic action.", player.getHero().getName());
            return;
        }

        // Try to cast a spell with available mana
        castSpell(player, room);
        // If no available spells or mana to cast them, perform basic attack
        attack(player, room);
    }

    private void castSpell(Player player, Room room) {
        List<Spell> spells = player.getHero().getSpells();

        if (spells.isEmpty()) {
            return;
        }

        Spell spell = spells.getFirst();

        if (player.getHero().getMana() < spell.getCost()) {
            log.info("{} lacks mana ({}/{}) for {}.", player.getHero().getName(), player.getHero().getMana(), spell.getCost(), spell.getName());
            return;
        }

        if (!room.hasMonsters()) {
            log.info("{} has no monsters to cast {} on.", player.getHero().getName(), spell.getName());
            return;
        }

        Monster monster = room.getMonsters().getFirst();
        player.getHero().castSpell(spell, monster);
        monster.takeDamage(spell.getPower());
        log.info("{} casts {} (power {}) on {} (HP now {}).", player.getHero().getName(), spell.getName(), spell.getPower(), monster.getName(), monster.getHealth());

        if (!monster.isAlive()) {
            room.removeDefeatedMonster();
            log.info("{} is defeated by a spell!", monster.getName());
        }
    }

    private void attack(Player player, Room room) {
        if (!room.hasMonsters()) {
            log.info("{} has no monsters to attack.", player.getHero().getName());
            return;
        }

        Monster monster = room.getMonsters().getFirst();
        monster.takeDamage(BASE_DAMAGE);
        log.info("{} attacks {} for {} damage (HP now {}).", player.getHero().getName(), monster.getName(), BASE_DAMAGE, monster.getHealth());

        if (!monster.isAlive()) {
            room.removeDefeatedMonster();
            log.info("{} is slain!", monster.getName());
        }
    }
}