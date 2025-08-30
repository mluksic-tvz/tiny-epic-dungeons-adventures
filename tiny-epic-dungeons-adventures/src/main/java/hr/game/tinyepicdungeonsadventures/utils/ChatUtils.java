package hr.game.tinyepicdungeonsadventures.utils;

import hr.game.tinyepicdungeonsadventures.TinyEpicDungeonsAdventures;
import hr.game.tinyepicdungeonsadventures.chat.ChatRemoteService;
import hr.game.tinyepicdungeonsadventures.exception.ChatException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.rmi.RemoteException;
import java.util.List;

import static hr.game.tinyepicdungeonsadventures.TinyEpicDungeonsAdventures.applicationConfiguration;

public class ChatUtils {

    private ChatUtils() {}

    public static void sendChatMessage(String chatMessage, ChatRemoteService chatRemoteService) {
        try {
            chatRemoteService.sendChatMessage(applicationConfiguration.getPlayerType().name()
                    + ": " + chatMessage);
        } catch (RemoteException e) {
            throw new ChatException("Error while sending a chat message!", e);
        }
    }

    public static Timeline getChatTimeline(ChatRemoteService chatRemoteService, TextArea chatTextArea) {
        Timeline chatMessagesTimeline = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent event) -> {
            try {
                List<String> chatMessages = chatRemoteService.getAllChatMessages();
                String chatMessagesString = String.join("\n", chatMessages);
                chatTextArea.setText(chatMessagesString);
            } catch (RemoteException e) {
                throw new ChatException("An error occured while creating the timeline for chat!", e);
            }
        }), new KeyFrame(Duration.seconds(1)));
        chatMessagesTimeline.setCycleCount(Animation.INDEFINITE);
        return chatMessagesTimeline;
    }
}
