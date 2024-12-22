package ssafy.StackFlow.Controller.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.chat.ChatMessage;
import ssafy.StackFlow.Domain.chat.ChatRoom;
import ssafy.StackFlow.Domain.chat.ChatRoomRequest;
import ssafy.StackFlow.DTO.ChatRoomDTO;
import ssafy.StackFlow.Repository.StoreRepository;
import ssafy.StackFlow.Repository.chat.ChatMessageRepository;
import ssafy.StackFlow.Repository.chat.ChatRoomRepository;
import ssafy.StackFlow.Repository.user.UserRepository;
import ssafy.StackFlow.Service.chat.ChatRoomService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRoomService chatRoomService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 채팅방 생성 (로그인한 사용자의 매장 정보 사용)
    @PostMapping("/createRoom")
    public ResponseEntity<?> createChatRoom(@RequestBody ChatRoomRequest request,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized: User is not authenticated.");
        }

        try {
            // 채팅방 생성 로직을 ChatRoomService에 위임
            ChatRoom createdChatRoom = chatRoomService.createRoom(request, userDetails.getUsername());

            // 저장된 채팅방을 DTO로 변환하여 반환
            ChatRoomDTO chatRoomDTO = new ChatRoomDTO(createdChatRoom);
            return ResponseEntity.ok(chatRoomDTO);

        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    // 채팅방 목록 (ChatRoomDTO로 변환하여 반환)
    @GetMapping("/rooms")
    public List<ChatRoomDTO> getAllRooms() {
        return chatRoomRepository.findAll().stream()
                .map(ChatRoomDTO::new) // ChatRoomDTO 생성자로 변환
                .collect(Collectors.toList());
    }

    // 채팅방 메시지 가져오기
    @GetMapping("/room/{roomId}/messages")
    public List<ChatMessage> getMessagesByRoom(@PathVariable String roomId) {
        return chatMessageRepository.findByRoomId(roomId);
    }

    // 스토어 목록 가져오기
    @GetMapping("/stores")
    public ResponseEntity<String> getAllStores() {
        try {
            // Store 목록 가져오기
            List<Store> stores = storeRepository.findAll();

            List<Map<String, Object>> storeList = stores.stream().map(store -> {
                Map<String, Object> storeMap = new HashMap<>();
                storeMap.put("id", store.getId());
                storeMap.put("storeName", store.getStoreName());
                storeMap.put("storeCode", store.getStoreCode());
                return storeMap;
            }).collect(Collectors.toList());

            // Map 리스트 -> JSON 문자열로 변환
            String jsonString = objectMapper.writeValueAsString(storeList);

            return ResponseEntity.ok(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error generating JSON");
        }
    }
}