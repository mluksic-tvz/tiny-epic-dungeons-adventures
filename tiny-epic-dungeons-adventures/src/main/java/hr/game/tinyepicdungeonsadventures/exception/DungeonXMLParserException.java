package hr.game.tinyepicdungeonsadventures.exception;

public class DungeonXMLParserException extends RuntimeException {

    public DungeonXMLParserException(String message) {
        super(message);
    }

    public DungeonXMLParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
