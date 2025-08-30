package hr.game.tinyepicdungeonsadventures.chat;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatRemoteServiceImpl implements ChatRemoteService {

    private final List<String> chatMessages;

    public ChatRemoteServiceImpl() {
        chatMessages = new CopyOnWriteArrayList<>();
    }

    @Override
    public void sendChatMessage(String message) throws RemoteException {
        chatMessages.add(Objects.requireNonNullElse(message, ""));
    }

    @Override
    public List<String> getAllChatMessages() throws RemoteException {
        return List.copyOf(chatMessages);
    }
}
