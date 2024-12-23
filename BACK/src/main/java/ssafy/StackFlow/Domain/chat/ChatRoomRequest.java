package ssafy.StackFlow.Domain.chat;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ChatRoomRequest {
    private String name;
    private boolean isGroup;
    private List<Long> storeIds;
}
