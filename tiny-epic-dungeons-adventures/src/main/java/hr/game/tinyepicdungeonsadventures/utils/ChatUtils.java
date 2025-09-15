package hr.game.tinyepicdungeonsadventures.utils;

import hr.game.tinyepicdungeonsadventures.chat.ChatRemoteService;
import hr.game.tinyepicdungeonsadventures.exception.ChatException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.rmi.RemoteException;
import java.util.List;

import static hr.game.tinyepicdungeonsadventures.TinyEpicDungeonsAdventures.applicationConfiguration;

/**
 * A utility class for handling chat-related operations.
 * <p>
 * This class provides static methods for sending chat messages via a remote
 * service and for creating a JavaFX timeline to periodically fetch and display
 * chat messages in a UI component.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatUtils {

    /**
     * Sends a chat message to the remote chat service.
     * <p>
     * The message is prefixed with the current player's type (e.g., "PLAYER_ONE: ").
     *
     * @param chatMessage       The message to be sent.
     * @param chatRemoteService The remote service instance used to send the message.
     * @throws ChatException if a {@link RemoteException} occurs during the process.
     */
    public static void sendChatMessage(String chatMessage, ChatRemoteService chatRemoteService) {
        try {
            chatRemoteService.sendChatMessage(applicationConfiguration.getPlayerType().name()
                    + ": " + chatMessage);
        } catch (RemoteException ex) {
            throw new ChatException("Error while sending a chat message!", ex);
        }
    }

    /**
     * Creates and returns a JavaFX {@link Timeline} for updating the chat UI.
     * <p>
     * The timeline runs indefinitely, and it fetches all chat
     * messages from the remote service and then updates the
     * provided {@link TextArea} with the latest messages.
     *
     * @param chatRemoteService The remote service instance to fetch messages from.
     * @param chatTextArea      The {@link TextArea} UI component to display the messages.
     * @return A {@link Timeline} configured to update the chat log.
     * @throws ChatException if a {@link RemoteException} occurs during timeline creation.
     */
    public static Timeline getChatTimeline(ChatRemoteService chatRemoteService, TextArea chatTextArea) {
        Timeline chatMessagesTimeline = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent event) -> {
            try {
                List<String> chatMessages = chatRemoteService.getAllChatMessages();
                String chatMessagesString = String.join("\n", chatMessages);
                chatTextArea.setText(chatMessagesString);
            } catch (RemoteException ex) {
                throw new ChatException("An error occured while creating the timeline for chat!", ex);
            }
        }), new KeyFrame(Duration.seconds(1)));
        chatMessagesTimeline.setCycleCount(Animation.INDEFINITE);
        return chatMessagesTimeline;
    }
}
