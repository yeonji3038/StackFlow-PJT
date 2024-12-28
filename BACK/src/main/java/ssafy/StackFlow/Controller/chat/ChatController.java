package ssafy.StackFlow.Controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ssafy.StackFlow.Domain.chat.ChatMessage;
import ssafy.StackFlow.Service.chat.ChatService;

import java.security.Principal;
import java.util.List;


@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @MessageMapping("/room/{roomId}/send")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable Long roomId, @Payload ChatMessage message, Principal principal) {
        if (principal == null) {
            throw new IllegalStateException("로그인되지 않은 사용자입니다.");
        }

        String sender = principal.getName(); // 로그인된 사용자 이름
        message.setSender(sender);
        message.setRoomId(String.valueOf(roomId));
        chatService.saveMessage(message, principal); // 메시지 저장
        return message; // 클라이언트로 메시지 반환
    }

//    // HTTP 요청으로 메시지 저장
//    @PostMapping("/room/{roomId}/send")
//    public void sendMessage(@PathVariable Long roomId, @RequestBody ChatMessage message, Principal principal) {
//        if (principal == null) {
//            throw new IllegalStateException("로그인되지 않은 사용자입니다.");
//        }
//
//        String sender = principal.getName();
//        message.setSender(sender);
//        message.setRoomId(String.valueOf(roomId));
//        chatService.saveMessage(message, principal); // 메시지 저장
//    }

    @GetMapping("/chat")
    public String getChatPage(Model model, Principal principal) {
        if (principal != null) {
            String currentUserId = principal.getName();
            model.addAttribute("currentUserId", currentUserId);
        }
        return "chat";
    }

    // 채팅방의 과거 메시지 조회
    @GetMapping("/room/{roomId}/messages")
    public List<ChatMessage> getMessages(@PathVariable Long roomId) {
        return chatService.getMessagesByRoomId(roomId); // 채팅방 ID로 메시지 목록 조회
    }
}