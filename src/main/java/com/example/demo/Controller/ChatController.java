package com.example.demo.Controller;

import com.example.demo.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    public ChatController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Endpoint to handle sending a text message (public chat)
    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        return message;
    }

    // Endpoint to handle sending a file (image, audio, etc.) in public chat
    @MessageMapping("/sendFile")
    @SendTo("/topic/messages")
    public ChatMessage sendFile(ChatMessage message) {
        return message;
    }

    // Endpoint to return the chat view (HTML page) - Rely on Spring Security for protection
    @GetMapping("/chat")
    public String chat() {
        return "chat";  // Your chat page template for public chat
    }

    // Endpoint to handle typing status (public chat)
    @MessageMapping("/typing")
    @SendTo("/topic/typing")
    public String typingStatus(String sender) {
        if (sender != null && !sender.isEmpty()) {
            return sender + " is typing...";
        }
        return "";  // Clear typing status when typing stops
    }
}

