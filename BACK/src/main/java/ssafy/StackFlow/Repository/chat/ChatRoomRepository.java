package ssafy.StackFlow.Repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.chat.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findByRoomId(String roomId);
}
