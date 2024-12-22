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
import ssafy.StackFlow.Domain.user.Signup;
import ssafy.StackFlow.DTO.ChatRoomDTO;  // ChatDTO import
import ssafy.StackFlow.Repository.StoreRepository;
import ssafy.StackFlow.Repository.chat.ChatMessageRepository;
import ssafy.StackFlow.Repository.chat.ChatRoomRepository;
import ssafy.StackFlow.Repository.user.UserRepository;

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

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 채팅방 생성 (로그인한 사용자의 매장 정보 사용)
    @PostMapping("/createRoom")
    public ResponseEntity<?> createChatRoom(@RequestParam String name,
                                            @RequestParam boolean isGroup,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized: User is not authenticated.");
        }

        try {
            // 현재 로그인된 사용자 정보 가져오기
            Signup currentUser = userRepository.findByusername(userDetails.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // 사용자의 매장 정보 확인
            Store userStore = currentUser.getStore();
            if (userStore == null) {
                return ResponseEntity.badRequest().body("User is not associated with any store.");
            }

            // 채팅방 생성
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setName(name);
            chatRoom.setGroup(isGroup);
            chatRoom.setStore(userStore); // 로그인한 사용자의 매장 정보 설정
            chatRoom.setRoomId(java.util.UUID.randomUUID().toString()); // 고유 Room ID 생성

            // 매장 ID와 참여자 추가 (예시)
            chatRoom.setStoreIds(List.of(userStore.getId())); // 매장 ID 리스트 추가
            chatRoom.setParticipants(List.of(currentUser));  // 현재 사용자만 참여자로 설정

            // 채팅방 저장
            ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

            // 저장된 채팅방을 DTO로 변환하여 반환
            ChatRoomDTO chatRoomDTO = new ChatRoomDTO(savedChatRoom);
            return ResponseEntity.ok(chatRoomDTO);

        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();  // 콘솔에 예외 로그 출력
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    // 채팅방 목록 (ChatDTO로 변환하여 반환)
    @GetMapping("/rooms")
    public List<ChatRoomDTO> getAllRooms() {
        return chatRoomRepository.findAll().stream()
                .map(chatRoom -> new ChatRoomDTO(chatRoom.getId(), chatRoom.getStore().getStoreName()))
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
                storeMap.put("location", store.getLocation());
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
