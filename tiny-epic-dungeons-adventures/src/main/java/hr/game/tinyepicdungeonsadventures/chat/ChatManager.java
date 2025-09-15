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

/**
 * Manages the chat functionality for Game UI screen.
 * This class encapsulates the logic for connecting to the chat service,
 * handling user input, and updating the chat view. It is designed to be
 * instantiated by a controller and given the UI components it needs to manage.
 */
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

    /**
     * Initializes the chat functionality.
     * This includes setting the action for the send button, attempting to connect
     * to the remote chat service, and starting a timeline to periodically
     * refresh chat messages. If the connection fails, it will log the error
     * and disable the chat UI components gracefully.
     */
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

    /**
     * Handles the send message button's action.
     * It retrieves the text from the input field, creates a background task
     * to send the message via the remote service, and handles the success
     * or failure of that task on the JavaFX Application Thread.
     */
    private void handleSendMessage() {
        String message = chatInputField.getText();

        if (message == null || message.isBlank())
            return;

        SendMessageTask sendMessageTask = new SendMessageTask(chatRemoteService, message);
        sendMessageTask.setOnSucceeded(e -> Platform.runLater(chatInputField::clear));
        sendMessageTask.setOnFailed(e -> {
            log.error("Failed to send chat message", sendMessageTask.getException());
            DialogUtils.showDialog(Alert.AlertType.ERROR, "Chat Error", "Could not send message.", "");
        });

        new Thread(sendMessageTask).start();
    }
}
