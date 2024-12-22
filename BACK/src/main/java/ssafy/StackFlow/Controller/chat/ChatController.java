package ssafy.StackFlow.Controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import ssafy.StackFlow.Domain.chat.ChatMessage;
import ssafy.StackFlow.Service.chat.ChatService;


@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ChatMessage send(ChatMessage message) {
        chatService.saveMessage(message);
        return message;
    }
}