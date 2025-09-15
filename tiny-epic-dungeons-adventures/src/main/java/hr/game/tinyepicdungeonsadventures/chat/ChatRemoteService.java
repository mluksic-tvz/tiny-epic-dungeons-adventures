package hr.game.tinyepicdungeonsadventures.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatRemoteService extends Remote {
    int RMI_PORT = 1099;
    String RMI_HOST = "localhost";
    String SERVICE_NAME = "ChatService";
    void sendChatMessage(String message) throws RemoteException;
    List<String> getAllChatMessages() throws RemoteException;
}
