package hr.game.tinyepicdungeonsadventures.chat;

import hr.game.tinyepicdungeonsadventures.exception.ChatException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Hashtable;

import static hr.game.tinyepicdungeonsadventures.chat.ChatRemoteService.*;

public class ChatClient {

    private ChatClient() {}

    public static ChatRemoteService connect() {
        try {
            HashMap<String, String> env = new HashMap<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
            env.put(Context.PROVIDER_URL, "rmi://" + RMI_HOST + ":" + RMI_PORT);

            Context context = new InitialContext(new Hashtable<>(env));

            Object remoteObject = context.lookup(SERVICE_NAME);

            return (ChatRemoteService) remoteObject;

        } catch (NamingException e) {
            throw new ChatException("Cannot find chat service in JNDI directory. Check if server has been started.", e);
        }
    }
}
