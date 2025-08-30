package hr.game.tinyepicdungeonsadventures.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatRemoteService extends Remote {
    String REMOTE_OBJECT_NAME = "hr.game.tinyepicdungeonsadventures.chat.service";
    void sendChatMessage(String message) throws RemoteException;
    List<String> getAllChatMessages() throws RemoteException;
}
