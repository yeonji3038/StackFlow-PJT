package ssafy.StackFlow.Domain.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.user.Signup;

import java.util.List;

@Entity
@Getter @Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId; // 채팅방 ID
    private String name; // 채팅방 이름
    private boolean isGroup; // 단체 채팅 여부

    @ElementCollection
    private List<Long> storeIds; // 여러 매장 ID를 저장

    @ManyToMany
    @JoinTable(
            name = "chatroom_participants",
            joinColumns = @JoinColumn(name = "chatroom_id"),
            inverseJoinColumns = @JoinColumn(name = "signup_id")
    )
    private List<Signup> participants; // 단체 채팅 참여자(지점들) 목록

    @ManyToOne
    @JoinColumn(name = "store_id") // 채팅방과 매장 연결
    private Store store; // 어떤 매장의 채팅방인지
}
