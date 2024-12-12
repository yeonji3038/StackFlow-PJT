package ssafy.StackFlow.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
  
}