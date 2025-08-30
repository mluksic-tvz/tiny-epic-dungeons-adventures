package hr.game.tinyepicdungeonsadventures.chat;

import hr.game.tinyepicdungeonsadventures.exception.ChatException;
import hr.game.tinyepicdungeonsadventures.jndi.ConfigurationKey;
import hr.game.tinyepicdungeonsadventures.jndi.ConfigurationReader;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatClient {
    private ChatClient() {}

    public static ChatRemoteService connect() {
        try {
            String host = ConfigurationReader.getStringValue(ConfigurationKey.RMI_HOST);
            int port = ConfigurationReader.getIntegerValue(ConfigurationKey.RMI_PORT);
            Registry registry = LocateRegistry.getRegistry(host, port);
            return (ChatRemoteService) registry.lookup(ChatRemoteService.REMOTE_OBJECT_NAME);
        } catch (Exception e) {
            throw new ChatException("Cannot connect to chat service", e);
        }
    }
}
