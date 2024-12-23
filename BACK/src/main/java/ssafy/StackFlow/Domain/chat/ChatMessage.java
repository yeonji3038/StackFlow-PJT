package ssafy.StackFlow.Domain.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     private String roomId; // 채팅방 ID
    private String sender; // 발신자
    private String recipient; // 수신자 (개인 채팅 시 사용)
    private String content; // 채팅 내용
    private LocalDateTime timestamp; // 전송 시간

    private boolean isRead; // 읽음 여부
    private boolean isNotified; // 알림 전송 여부

    @ManyToOne
    private ChatRoom chatRoom; // 메시지가 속한 채팅방 (단체 채팅 시 사용)
}
