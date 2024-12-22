package ssafy.StackFlow.Controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.chat.ChatMessage;
import ssafy.StackFlow.Domain.chat.MessageReadStatus;
import ssafy.StackFlow.Domain.user.Signup;
import ssafy.StackFlow.Repository.chat.ChatMessageRepository;
import ssafy.StackFlow.Repository.chat.ChatRoomRepository;
import ssafy.StackFlow.Repository.chat.MessageReadStatusRepository;
import ssafy.StackFlow.Repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatMessageController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private MessageReadStatusRepository messageReadStatusRepository;

    @Autowired
    private UserRepository userRepository;

    // 메시지 저장 및 알림 푸시 API
    @PostMapping("/sendMessage")
    public ChatMessage sendMessage(@RequestParam String roomId,
                                   @RequestParam String sender,
                                   @RequestParam String content) {
        // 메시지 생성 및 저장
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoomId(roomId);
        chatMessage.setSender(sender);
        chatMessage.setContent(content);
        chatMessage.setTimestamp(LocalDateTime.now());
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        // 채팅방에 참여한 사용자 목록 불러오기
        List<Signup> participants = chatRoomRepository.findByRoomId(roomId).getParticipants();

        // 읽지 않은 사용자에게 알림 푸시
        for (Signup participant : participants) {
            if (!isMessageRead(participant.getId(), savedMessage.getId())) {
                sendNotification(participant);  // 알림 전송
            }
        }

        // WebSocket을 통해 방에 메시지 전송
        messagingTemplate.convertAndSend("/topic/messages/" + roomId, savedMessage);

        return savedMessage;
    }

    // 메시지 읽음 상태 확인
    private boolean isMessageRead(Long userId, Long messageId) {
        Signup user = userRepository.findById(userId).orElse(null); // 사용자 찾기
        ChatMessage chatMessage = chatMessageRepository.findById(messageId).orElse(null); // 메시지 찾기

        if (user != null && chatMessage != null) {
            MessageReadStatus status = messageReadStatusRepository.findByUserIdAndChatMessageId(userId, messageId);
            // 상태가 없으면 메시지가 읽히지 않은 상태로 간주
            return status != null && status.isRead();
        }
        return false;
    }

    // 알림 전송 함수
    private void sendNotification(Signup participant) {
        // 알림을 WebSocket을 통해 클라이언트로 전송
        String notificationMessage = participant.getUsername() + "님, 새로운 메시지가 도착했습니다 !";
        messagingTemplate.convertAndSend("/topic/notifications/" + participant.getId(), notificationMessage);
    }

    // 메시지 읽음 처리 API
    @PostMapping("/markRead")
    public String markMessageAsRead(@RequestParam Long userId, @RequestParam Long messageId) {
        // 사용자와 메시지 조회
        Signup user = userRepository.findById(userId).orElse(null);
        ChatMessage chatMessage = chatMessageRepository.findById(messageId).orElse(null);

        if (user != null && chatMessage != null) {
            // 읽음 상태 체크
            MessageReadStatus status = messageReadStatusRepository.findByUserIdAndChatMessageId(userId, messageId);

            if (status == null) {
                // 없으면 새로 추가
                status = new MessageReadStatus(user, chatMessage, true);
                messageReadStatusRepository.save(status);
                return "Message marked as read.";
            } else if (!status.isRead()) {
                // 이미 존재하지만 읽음 처리되지 않았다면
                status.setIsRead(true);
                messageReadStatusRepository.save(status);
                return "Message marked as read.";
            }

            return "Message is already marked as read.";
        }

        return "Invalid user or message.";
    }

    // 특정 채팅방의 메시지와 읽음 상태 조회 API
    @GetMapping("/chat/message/{roomId}")
    public List<ChatMessage> getMessagesByRoom(@PathVariable String roomId, @RequestParam Long userId) {
        List<ChatMessage> messages = chatMessageRepository.findByRoomId(roomId);

        // 메시지 조회 후 읽음 상태 업데이트
        for (ChatMessage message : messages) {
            // 읽음 상태 확인
            MessageReadStatus status = messageReadStatusRepository.findByUserIdAndChatMessageId(userId, message.getId());

            // 없으면 읽음 상태로 추가
            if (status == null) {
                status = new MessageReadStatus(userRepository.findById(userId).orElse(null), message, true);
                messageReadStatusRepository.save(status);
            }
        }

        return messages;
    }
}