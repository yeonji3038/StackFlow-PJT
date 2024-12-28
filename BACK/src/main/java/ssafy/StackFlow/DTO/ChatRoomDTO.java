package ssafy.StackFlow.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ssafy.StackFlow.Domain.chat.ChatRoom;
import ssafy.StackFlow.Domain.user.Signup;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class ChatRoomDTO {
    private Long id;                       // 채팅방 ID
    private String roomId;                 // 고유 채팅방 ID
    private String name;                   // 채팅방 이름
    private boolean isGroup;               // 단체 채팅 여부
    private String storeName;              // 매장 이름
    private List<String> participantNames; // 참여자 이름들 (Signup의 이름)

    // ChatRoom 엔티티 객체를 기반으로 DTO 생성
    public ChatRoomDTO(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.roomId = chatRoom.getRoomId();
        this.name = chatRoom.getName();
        this.isGroup = chatRoom.isGroup();

        // store가 null인 경우 처리
        this.storeName = (chatRoom.getStore() != null) ? chatRoom.getStore().getStoreName() : "Store 없음.";

        // 참여자 목록에서 이름만 추출해서 넣음
        this.participantNames = chatRoom.getParticipants().stream()
                .map(Signup::getUsername)
                .collect(Collectors.toList());
    }
}
