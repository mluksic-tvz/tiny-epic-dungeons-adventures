package hr.game.tinyepicdungeonsadventures.core;

import hr.game.tinyepicdungeonsadventures.model.dungeon.Dungeon;
import hr.game.tinyepicdungeonsadventures.xml.DungeonXMLParser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DungeonFactory {
    private static final String DUNGEON_CONFIG_FILE = "dungeon.xml";

    public static Dungeon createDungeon() {
        return DungeonXMLParser.loadDungeonFromXML(DUNGEON_CONFIG_FILE);
    }
}
