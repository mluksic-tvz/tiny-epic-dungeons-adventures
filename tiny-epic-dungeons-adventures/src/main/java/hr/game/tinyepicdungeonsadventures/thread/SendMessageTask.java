package hr.game.tinyepicdungeonsadventures.thread;

import hr.game.tinyepicdungeonsadventures.chat.ChatRemoteService;
import hr.game.tinyepicdungeonsadventures.utils.ChatUtils;
import javafx.concurrent.Task;

/**
 * A {@link Task} for sending a chat message in a background thread.
 * <p>
 * This class encapsulates the logic for sending a message using a {@link ChatRemoteService},
 * preventing the UI thread from being blocked. The task can be run on an
 * executor service to perform the network operation asynchronously.
 */
public class SendMessageTask extends Task<Void> {

    private final ChatRemoteService chatRemoteService;
    private final String message;

    public SendMessageTask(ChatRemoteService chatRemoteService, String message) {
        this.chatRemoteService = chatRemoteService;
        this.message = message;
    }

    @Override
    protected Void call() throws Exception {
        ChatUtils.sendChatMessage(message, chatRemoteService);
        return null;
    }
}
