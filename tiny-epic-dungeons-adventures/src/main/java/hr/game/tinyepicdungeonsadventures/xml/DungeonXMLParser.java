package hr.game.tinyepicdungeonsadventures.xml;

import hr.game.tinyepicdungeonsadventures.exception.DungeonXMLParserException;
import hr.game.tinyepicdungeonsadventures.model.character.monster.Monster;
import hr.game.tinyepicdungeonsadventures.model.character.monster.MonsterType;
import hr.game.tinyepicdungeonsadventures.model.dungeon.Dungeon;
import hr.game.tinyepicdungeonsadventures.model.dungeon.Room;
import hr.game.tinyepicdungeonsadventures.model.items.HealingPotion;
import hr.game.tinyepicdungeonsadventures.model.items.Item;
import hr.game.tinyepicdungeonsadventures.model.items.LootItem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for parsing dungeon data from an XML file.
 * <p>
 * This class uses the Java DOM parser to read an XML configuration file and
 * construct a {@link Dungeon} object, along with all its associated rooms, monsters,
 * and items. It provides a single public method to load a dungeon from a file,
 * abstracting the parsing logic.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DungeonXMLParser {

    public static Dungeon loadDungeonFromXML(String fileName) {
        try (InputStream inputStream = DungeonXMLParser.class.getClassLoader().getResourceAsStream(fileName)) {

            if (inputStream == null) {
                throw new IllegalArgumentException("File not found! " + fileName);
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);
            doc.getDocumentElement().normalize();

            Element dungeonElement = doc.getDocumentElement();

            Element lootPoolElement = (Element) dungeonElement.getElementsByTagName("lootPool").item(0);
            List<Item> lootPool = parseItemsFromParent(lootPoolElement);

            List<Room> regularRooms = parseRooms(dungeonElement);
            Room bossLair = parseBossLair(dungeonElement);

            return new Dungeon(regularRooms, bossLair, lootPool);
        } catch (Exception ex) {
            throw new DungeonXMLParserException("Failed to parse dungeon XML: " + fileName, ex);
        }
    }

    private static List<Room> parseRooms(Element parentElement) {
        List<Room> rooms = new ArrayList<>();
        Element roomPoolElement = (Element) parentElement.getElementsByTagName("roomPool").item(0);
        NodeList roomNodes = roomPoolElement.getElementsByTagName("room");

        for (int i = 0; i < roomNodes.getLength(); i++) {
            if (roomNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element roomElement = (Element) roomNodes.item(i);
                String id = roomElement.getAttribute("id");
                List<Monster> monsters = parseMonsters(roomElement);
                List<Item> items = parseItemsFromParent(roomElement);
                rooms.add(new Room(id, monsters, items, false));
            }
        }

        return rooms;
    }

    private static Room parseBossLair(Element parentElement) {
        Element lairElement = (Element) parentElement.getElementsByTagName("bossLair").item(0);
        String id = lairElement.getAttribute("id");
        List<Monster> monsters = parseMonsters(lairElement);
        return new Room(id, monsters, new ArrayList<>(), false);
    }

    private static List<Monster> parseMonsters(Element parentElement) {
        List<Monster> monsters = new ArrayList<>();
        NodeList monsterNodes = parentElement.getElementsByTagName("monster");

        for (int i = 0; i < monsterNodes.getLength(); i++) {
            Element monsterElement = (Element) monsterNodes.item(i);
            MonsterType type = MonsterType.valueOf(monsterElement.getAttribute("type"));
            monsters.add(new Monster(type));
        }

        return monsters;
    }

    private static List<Item> parseItemsFromParent(Element parentElement) {
        List<Item> items = new ArrayList<>();
        NodeList itemNodes = parentElement.getElementsByTagName("item");

        for (int i = 0; i < itemNodes.getLength(); i++) {
            Element itemElement = (Element) itemNodes.item(i);
            String type = itemElement.getAttribute("type");
            String name = itemElement.getAttribute("name");

            if ("potion".equals(type)) {
                int heal = getIntAttribute(itemElement, "heal");
                items.add(new HealingPotion(name, heal));
            } else {
                int attack = getIntAttribute(itemElement, "attack");
                int defense = getIntAttribute(itemElement, "defense");
                items.add(new LootItem(name, attack, defense));
            }
        }
        return items;
    }

    private static int getIntAttribute(Element element, String attributeName) {
        String value = element.getAttribute(attributeName);

        if (value.isEmpty())
            return 0;

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException _) {
            return 0;
        }
    }
}
