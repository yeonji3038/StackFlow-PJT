package ssafy.StackFlow.Service.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssafy.StackFlow.Domain.chat.ChatMessage;
import ssafy.StackFlow.Domain.chat.ChatRoom;
import ssafy.StackFlow.Repository.chat.ChatMessageRepository;
import ssafy.StackFlow.Repository.chat.ChatRoomRepository;


import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ChatService(ChatMessageRepository chatMessageRepository, ChatRoomRepository chatRoomRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    public List<ChatMessage> getMessagesBySender(String sender) {
        return chatMessageRepository.findBySender(sender);
    }

    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }

    public void saveMessage(ChatMessage message, Principal principal) {
        ChatRoom chatRoom = chatRoomRepository.findById(Long.parseLong(message.getRoomId()))
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));

        message.setChatRoom(chatRoom);
        message.setSender(principal.getName());
        message.setTimestamp(LocalDateTime.now());
        message.setReadUsers(new ArrayList<>());

        List<String> participantNames = chatRoom.getParticipantNames();
        String currentUserId = message.getSender(); // 메시지를 보낸 사용자 (현재 로그인 된 유저)

        if (participantNames.size() == 2) { // 개인 채팅
            participantNames.remove(currentUserId);
            if (participantNames.isEmpty()) {
                throw new IllegalArgumentException("채팅방에 다른 참여자가 없습니다.");
            }
            message.setRecipient(participantNames.get(0));
        } else { // 단체 채팅
            List<String> recipients = new ArrayList<>(participantNames);
            recipients.remove(currentUserId); // 본인을 제외한 다른 참여자들
            if (recipients.isEmpty()) {
                throw new IllegalArgumentException("채팅방에 다른 참여자가 없습니다.");
            }
            message.setRecipients(recipients);
        }

        if (message.getRecipient() == null && (message.getRecipients() == null || message.getRecipients().isEmpty())) {
            throw new IllegalArgumentException("Recipient은 null일 수 없습니다.");
        }

        chatMessageRepository.save(message);
    }

    // 채팅방 ID로 메시지 조회
    public List<ChatMessage> getMessagesByRoomId(Long roomId) {
        return chatMessageRepository.findByRoomId(String.valueOf(roomId)); // repository 메서드 호출
    }
}