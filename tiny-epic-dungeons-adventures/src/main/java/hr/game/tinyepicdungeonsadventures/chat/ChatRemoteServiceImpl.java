package hr.game.tinyepicdungeonsadventures.chat;

import lombok.extern.slf4j.Slf4j;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class ChatRemoteServiceImpl implements ChatRemoteService {

    private final List<String> chatMessages;

    public ChatRemoteServiceImpl() {
        chatMessages = new CopyOnWriteArrayList<>();
    }

    @Override
    public void sendChatMessage(String message) throws RemoteException {
        log.info("Received new chat message: '{}'", message);
        chatMessages.add(Objects.requireNonNullElse(message, ""));
    }

    @Override
    public List<String> getAllChatMessages() throws RemoteException {
        return List.copyOf(chatMessages);
    }
}
