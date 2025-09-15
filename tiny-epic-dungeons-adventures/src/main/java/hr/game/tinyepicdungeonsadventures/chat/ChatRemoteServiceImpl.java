package hr.game.tinyepicdungeonsadventures.chat;

import lombok.extern.slf4j.Slf4j;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The server-side implementation of the {@link ChatRemoteService} interface.
 * This class manages the storage and retrieval of chat messages in a thread-safe manner
 * using a {@link CopyOnWriteArrayList}.
 */
@Slf4j
public class ChatRemoteServiceImpl implements ChatRemoteService {

    private final List<String> chatMessages;

    public ChatRemoteServiceImpl() {
        chatMessages = new CopyOnWriteArrayList<>();
    }

    /**
     * {@inheritDoc}
     * This implementation logs the received message and adds it to the thread-safe list
     * of chat messages.
     */
    @Override
    public void sendChatMessage(String message) throws RemoteException {
        log.info("Received new chat message: '{}'", message);
        chatMessages.add(Objects.requireNonNullElse(message, ""));
    }

    /**
     * {@inheritDoc}
     * This implementation returns an immutable copy of the chat message list to ensure
     * that the server's internal state cannot be modified by the client.
     */
    @Override
    public List<String> getAllChatMessages() throws RemoteException {
        return List.copyOf(chatMessages);
    }
}
