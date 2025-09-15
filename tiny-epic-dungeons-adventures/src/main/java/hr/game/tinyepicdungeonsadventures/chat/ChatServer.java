package hr.game.tinyepicdungeonsadventures.chat;

import hr.game.tinyepicdungeonsadventures.AppConfiguration;
import lombok.extern.slf4j.Slf4j;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Hashtable;

@Slf4j
public class ChatServer {

    public static void main(String[] args) {
        try {
            int port = AppConfiguration.getPort();
            String serviceName = AppConfiguration.getServiceName();
            LocateRegistry.createRegistry(port);
            log.info("RMI registry created on port {}.", port);

            HashMap<String, String> env = new HashMap<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
            env.put(Context.PROVIDER_URL, "rmi://localhost:" + port);
            Context context = new InitialContext(new Hashtable<>(env));
            log.info("JNDI context created.");

            ChatRemoteService chatRemoteService = new ChatRemoteServiceImpl();
            ChatRemoteService skeleton = (ChatRemoteService) UnicastRemoteObject.exportObject(chatRemoteService, 0);

            context.rebind(serviceName, skeleton);
            log.info("Chat server started successfully.");
            log.info("Listening on port: {}", port);
            log.info("Waiting for client connection...");

        } catch (RemoteException | NamingException e) {
            log.error("Error while setting up chat server.", e);
        }
    }
}
