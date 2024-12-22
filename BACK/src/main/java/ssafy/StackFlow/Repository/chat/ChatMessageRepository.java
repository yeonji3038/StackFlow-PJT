package ssafy.StackFlow.Repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.chat.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByRoomId(String roomId);
}