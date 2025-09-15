package hr.game.tinyepicdungeonsadventures.core;

import hr.game.tinyepicdungeonsadventures.model.character.hero.Hero;
import hr.game.tinyepicdungeonsadventures.model.player.Player;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerFactory {

    public static List<Player> createPlayers(List<Hero> selectedHeroes) {
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < selectedHeroes.size(); i++) {
            Player player = new Player("Player " + (i + 1));
            player.setHero(selectedHeroes.get(i));
            players.add(player);
        }

        return players;
    }
}
