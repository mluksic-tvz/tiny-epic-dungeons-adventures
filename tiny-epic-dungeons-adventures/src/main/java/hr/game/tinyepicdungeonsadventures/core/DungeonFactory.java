package hr.game.tinyepicdungeonsadventures.core;

import hr.game.tinyepicdungeonsadventures.model.dungeon.Dungeon;
import hr.game.tinyepicdungeonsadventures.xml.DungeonXMLParser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A factory class for creating a {@link Dungeon} instance.
 * <p>
 * This class provides a static method to create a dungeon by loading its
 * configuration from an XML file. It follows the Singleton pattern with
 * a private constructor to prevent instantiation, as its purpose is to
 * provide a single point of access for dungeon creation.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DungeonFactory {
    private static final String DUNGEON_CONFIG_FILE = "dungeon.xml";

    public static Dungeon createDungeon() {
        return DungeonXMLParser.loadDungeonFromXML(DUNGEON_CONFIG_FILE);
    }
}
