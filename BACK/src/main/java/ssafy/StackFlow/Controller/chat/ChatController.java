package ssafy.StackFlow.Controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ssafy.StackFlow.Domain.chat.ChatMessage;
import ssafy.StackFlow.Service.chat.ChatService;

import java.security.Principal;


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

    @GetMapping("/chat")
    public String getChatPage(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("currentUserId", principal.getName());
        }
        return "chat";
    }
}