package ssafy.StackFlow.Repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.StackFlow.Domain.chat.MessageReadStatus;

import java.util.List;

public interface MessageReadStatusRepository extends JpaRepository<MessageReadStatus, Long> {
    // 메시지 ID로 읽음 상태 조회
    List<MessageReadStatus> findByChatMessageId(Long chatMessageId);

    // 사용자 ID와 메시지 ID로 읽음 상태 조회
    MessageReadStatus findByUserIdAndChatMessageId(Long userId, Long chatMessageId);
}
