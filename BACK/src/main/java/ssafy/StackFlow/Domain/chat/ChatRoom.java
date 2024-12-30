package ssafy.StackFlow.Domain.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.user.Signup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId; // 채팅방 ID
    private String name; // 채팅방 이름
    private boolean isGroup; // 단체 채팅 여부

    ///////////////////////////////////////////////////////////////////////

//    @ElementCollection
//    private List<Long> storeIds; // 여러 매장 ID를 저장

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Store> stores;

    // Store IDs를 추출하는 메서드 추가
    @JsonProperty("storeIds")
    public List<Long> getStoreIds() {
        if (stores == null) {
            return new ArrayList<>();
        }
        return stores.stream().map(Store::getId).toList();
    }


    //////////////////////////////////////////////////////////////////////

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "chatroom_participants",
            joinColumns = @JoinColumn(name = "chatroom_id"),
            inverseJoinColumns = @JoinColumn(name = "signup_id")
    )
    private List<Signup> participants; // 단체 채팅 참여자(지점들) 목록

    @ManyToOne
    @JoinColumn(name = "store_id") // 채팅방과 매장 연결
    private Store store; // 어떤 매장의 채팅방인지

    // 채팅방 참여자들의 이름을 반환하는 메서드
    public List<String> getParticipantNames() {
        return participants.stream()
                .map(Signup::getUsername)
                .collect(Collectors.toList());
    }
}
