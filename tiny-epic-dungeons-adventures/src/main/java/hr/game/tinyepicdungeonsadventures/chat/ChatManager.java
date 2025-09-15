package hr.game.tinyepicdungeonsadventures.chat;

import hr.game.tinyepicdungeonsadventures.thread.SendMessageTask;
import hr.game.tinyepicdungeonsadventures.utils.ChatUtils;
import hr.game.tinyepicdungeonsadventures.utils.DialogUtils;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatManager {

    private final TextArea chatTextArea;
    private final TextField chatInputField;
    private final Button sendMessageButton;
    private ChatRemoteService chatRemoteService;

    public ChatManager(TextArea chatTextArea, TextField chatInputField, Button sendMessageButton) {
        this.chatTextArea = chatTextArea;
        this.chatInputField = chatInputField;
        this.sendMessageButton = sendMessageButton;
    }

    public void initializeChat() {
        sendMessageButton.setOnAction(e -> handleSendMessage());

        try {
            this.chatRemoteService = ChatClient.connect();
            Timeline chatTimeline = ChatUtils.getChatTimeline(chatRemoteService, chatTextArea);
            chatTimeline.play();
        } catch (Exception ex) {
            log.error("Failed to connect to chat service", ex);
            DialogUtils.showDialog(Alert.AlertType.WARNING, "Chat Unavailable", "Could not connect to the chat service.", "Please ensure the chat server is running.");
            chatInputField.setDisable(true);
            sendMessageButton.setDisable(true);
            chatTextArea.setText("Chat service is not available.");
        }
    }

    private void handleSendMessage() {
        String message = chatInputField.getText();
        if (message == null || message.isBlank()) {
            return;
        }

        SendMessageTask sendMessageTask = new SendMessageTask(chatRemoteService, message);
        sendMessageTask.setOnSucceeded(e -> Platform.runLater(chatInputField::clear));
        sendMessageTask.setOnFailed(e -> {
            log.error("Failed to send chat message", sendMessageTask.getException());
            DialogUtils.showDialog(Alert.AlertType.ERROR, "Chat Error", "Could not send message.", "");
        });
        new Thread(sendMessageTask).start();
    }
}
