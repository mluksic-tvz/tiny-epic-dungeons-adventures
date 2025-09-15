package hr.game.tinyepicdungeonsadventures.thread;

import hr.game.tinyepicdungeonsadventures.chat.ChatRemoteService;
import hr.game.tinyepicdungeonsadventures.utils.ChatUtils;
import javafx.concurrent.Task;

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
