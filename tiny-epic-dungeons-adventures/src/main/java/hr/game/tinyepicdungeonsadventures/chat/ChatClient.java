package hr.game.tinyepicdungeonsadventures.chat;

import hr.game.tinyepicdungeonsadventures.AppConfiguration;
import hr.game.tinyepicdungeonsadventures.exception.ChatException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * A utility class responsible for connecting to the remote chat server using JNDI and RMI.
 * This class is not meant to be instantiated and should be used only via its
 * static {@code connect()} method.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatClient {

    /**
     * Connects to the JNDI/RMI server and retrieves the remote stub for the chat service.
     * This method uses configuration data from the {@link AppConfiguration} class
     * to locate the JNDI service.
     *
     * @return A remote stub of type {@link ChatRemoteService} that can be used to communicate with the server.
     * @throws ChatException if the connection to the JNDI service fails or if the chat service cannot be found in the directory.
     */
    public static ChatRemoteService connect() {
        try {
            String host = AppConfiguration.getHost();
            int port = AppConfiguration.getPort();
            String serviceName = AppConfiguration.getServiceName();

            HashMap<String, String> env = new HashMap<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
            env.put(Context.PROVIDER_URL, "rmi://" + host + ":" + port);

            Context context = new InitialContext(new Hashtable<>(env));

            Object remoteObject = context.lookup(serviceName);

            return (ChatRemoteService) remoteObject;

        } catch (NamingException ex) {
            throw new ChatException("Cannot find chat service in JNDI directory. Check if server has been started.", ex);
        }
    }
}
