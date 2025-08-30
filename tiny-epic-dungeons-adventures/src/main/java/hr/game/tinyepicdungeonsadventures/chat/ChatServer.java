package hr.game.tinyepicdungeonsadventures.chat;

import hr.game.tinyepicdungeonsadventures.jndi.ConfigurationKey;
import hr.game.tinyepicdungeonsadventures.jndi.ConfigurationReader;
import lombok.extern.slf4j.Slf4j;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

@Slf4j
public class ChatServer {

    private static final int RANDOM_PORT_HINT = 0;

    public static void main(String[] args) {
        try {
            String host = ConfigurationReader.getStringValue(ConfigurationKey.RMI_HOST);
            int port = ConfigurationReader.getIntegerValue(ConfigurationKey.RMI_PORT);
            String chatName = ConfigurationReader.getStringValue(ConfigurationKey.SERVICE_CHAT_NAME);

            Registry registry = LocateRegistry.createRegistry(port);
            ChatRemoteService chatRemoteService = new ChatRemoteServiceImpl();
            ChatRemoteService skeleton = (ChatRemoteService) UnicastRemoteObject.exportObject(chatRemoteService,
                    RANDOM_PORT_HINT);
            registry.rebind(ChatRemoteService.REMOTE_OBJECT_NAME, skeleton);
            log.info("Chat service bound on rmi://{}:{}/{}", host, port, chatName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
