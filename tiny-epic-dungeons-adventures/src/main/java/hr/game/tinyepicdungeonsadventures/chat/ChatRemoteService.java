package hr.game.tinyepicdungeonsadventures.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Defines the remote interface for the chat service.
 * This interface specifies the methods that can be called remotely by a client on the chat server.
 * It must extend {@link Remote} to be used with RMI.
 */
public interface ChatRemoteService extends Remote {
    void sendChatMessage(String message) throws RemoteException;
    List<String> getAllChatMessages() throws RemoteException;
}
