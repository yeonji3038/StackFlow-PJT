package ssafy.StackFlow.Domain.chat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String roomId; // 채팅방 ID

    // @NotNull
    private String sender; // 발신자

    private String recipient; // 수신자 (개인 채팅 시 사용)

    @ElementCollection
    private List<String> recipients = new ArrayList<>(); // 수신자들 (단체 채팅 시 사용)

    @NotEmpty
    private String content; // 채팅 내용

    @ElementCollection
    @CollectionTable(name = "chat_room_read_users", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "read_user")
    private List<String> readUsers; // 읽은 사용자 목록

    @CreationTimestamp
    private LocalDateTime timestamp; // 전송 시간

    private boolean isRead = false; // 읽음 여부

    private boolean isNotified = false; // 알림 전송 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom; // 메시지가 속한 채팅방 (단체 채팅 시 사용)
}
