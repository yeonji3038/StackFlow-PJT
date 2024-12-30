package ssafy.StackFlow.Domain.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ssafy.StackFlow.Domain.user.Signup;

@Entity
@Getter @Setter
@Accessors(chain = true)

public class MessageReadStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_message_id")
    private ChatMessage chatMessage; // 읽음 상태가 연결된 메시지

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Signup user; // 읽은 사용자

    private boolean isRead; // 읽음 여부

    // 기본 생성자
    public MessageReadStatus() {
    }

    // 생성자 추가
    public MessageReadStatus(Signup user, ChatMessage chatMessage, boolean isRead) {
        this.user = user;
        this.chatMessage = chatMessage;
        this.isRead = isRead;
    }

    // 명시적으로 setIsRead 메서드 추가  ->  이거 안 하면 setter 오류 뜨는데, 위에서 setter 했는데 왜 뜨는 지 모르겠음..
    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
