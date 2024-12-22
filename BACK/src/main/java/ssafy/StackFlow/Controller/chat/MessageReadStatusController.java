package ssafy.StackFlow.Controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.chat.ChatMessage;
import ssafy.StackFlow.Domain.chat.MessageReadStatus;
import ssafy.StackFlow.Repository.chat.ChatMessageRepository;
import ssafy.StackFlow.Repository.chat.MessageReadStatusRepository;
import ssafy.StackFlow.Repository.user.UserRepository;
import ssafy.StackFlow.Domain.user.Signup;

@RestController
@RequestMapping("/chat")
public class MessageReadStatusController {

    @Autowired
    private MessageReadStatusRepository messageReadStatusRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    // 읽음 상태 업데이트 API
    @PostMapping("/updateReadStatus")
    public MessageReadStatus updateReadStatus(@RequestParam Long messageId, @RequestParam Long userId) {
        ChatMessage chatMessage = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid message ID"));

        Signup user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        MessageReadStatus status = new MessageReadStatus();
        status.setChatMessage(chatMessage);
        status.setUser(user);
        status.setIsRead(true); // 읽음으로 설정

        return messageReadStatusRepository.save(status);
    }
}
