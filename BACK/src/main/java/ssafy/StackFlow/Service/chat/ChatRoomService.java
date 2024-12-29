package ssafy.StackFlow.Service.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssafy.StackFlow.Domain.Store;
import ssafy.StackFlow.Domain.chat.ChatRoom;
import ssafy.StackFlow.Domain.chat.ChatRoomRequest;
import ssafy.StackFlow.Domain.user.Signup;
import ssafy.StackFlow.Repository.StoreRepository;
import ssafy.StackFlow.Repository.chat.ChatRoomRepository;
import ssafy.StackFlow.Repository.user.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

//    // 채팅방 생성 로직
//    public ChatRoom createRoom(ChatRoomRequest request, String username) {
//        // 사용자 정보 가져오기
//        Signup currentUser = userRepository.findByusername(username)
//                .orElseThrow(() -> new IllegalArgumentException("User가 없습니다."));
//
//        // 사용자의 매장 정보 확인
//        Store userStore = currentUser.getStore();
//        if (userStore == null) {
//            throw new IllegalArgumentException("User와 store가 연결되어 있지 않습니다.");
//        }
//
//        // 상대 매장(들) 정보 확인
//        if (request.getStoreIds().isEmpty()) {
//            throw new IllegalArgumentException("선택된 매장이 없습니다.");
//        }
//
//        // 선택한 매장들에 대해 사용자들이 참여하도록 설정
//        List<Store> participatingStores = storeRepository.findAllById(request.getStoreIds());
//        if (participatingStores.size() != request.getStoreIds().size()) {
//            throw new IllegalArgumentException("store를 찾을 수 없습니다.");
//        }
//
//        // userStore가 이미 participatingStores에 포함되어 있는지 확인하고, 없으면 추가
//        if (!participatingStores.contains(userStore)) {
//            participatingStores.add(userStore);
//        }
//
//        // 채팅방 객체 생성
//        ChatRoom chatRoom = new ChatRoom();
//        chatRoom.setName(request.getName());
//        chatRoom.setGroup(request.isGroup());  // 그룹 채팅 여부
//        chatRoom.setStore(userStore); // 채팅방의 기본 매장 설정
//        chatRoom.setStoreIds(request.getStoreIds()); // 참여할 매장들의 ID 설정
//
//        // 참여자 설정: 각 매장에 해당하는 사용자들을 participants에 추가
//        List<Signup> participants = participatingStores.stream()
//                .flatMap(store -> userRepository.findByStore(store).stream())
//                .distinct()  // 중복 사용자 제거
//                .collect(Collectors.toList());
//        chatRoom.setParticipants(participants);
//
//        // 채팅방 고유 ID 생성
//        chatRoom.setRoomId(UUID.randomUUID().toString()); // 고유 Room ID 생성
//
//        // 채팅방 저장
//        return chatRoomRepository.save(chatRoom);
//    }

    public ChatRoom createRoom(ChatRoomRequest request, String username) {
        // 사용자 정보 가져오기
        Signup currentUser = userRepository.findByusername(username)
                .orElseThrow(() -> new IllegalArgumentException("User가 없습니다."));

        // 사용자의 매장 정보 확인
        Store userStore = currentUser.getStore();
        if (userStore == null) {
            throw new IllegalArgumentException("User와 store가 연결되어 있지 않습니다.");
        }

        // 상대 매장(들) 정보 확인
        if (request.getStoreIds().isEmpty()) {
            throw new IllegalArgumentException("선택된 매장이 없습니다.");
        }

        // 선택한 매장들에 대해 사용자들이 참여하도록 설정
        List<Store> participatingStores = storeRepository.findAllById(request.getStoreIds());
        if (participatingStores.size() != request.getStoreIds().size()) {
            throw new IllegalArgumentException("store를 찾을 수 없습니다.");
        }

        // userStore가 이미 participatingStores에 포함되어 있는지 확인하고, 없으면 추가
        if (!participatingStores.contains(userStore)) {
            participatingStores.add(userStore);
        }

        // 채팅방 객체 생성
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(request.getName());
        chatRoom.setGroup(request.isGroup());  // 그룹 채팅 여부
        chatRoom.setStore(userStore);          // 채팅방의 기본 매장 설정

        // Set<Store>로 participatingStores를 설정
        chatRoom.setStores(new HashSet<>(participatingStores)); // 참여할 매장들 설정

        // 참여자 설정: 각 매장에 해당하는 사용자들을 participants에 추가
        List<Signup> participants = participatingStores.stream()
                .flatMap(store -> userRepository.findByStore(store).stream())
                .distinct()  // 중복 사용자 제거
                .collect(Collectors.toList());
        chatRoom.setParticipants(participants);

        // 채팅방 고유 ID 생성
        chatRoom.setRoomId(UUID.randomUUID().toString()); // 고유 Room ID 생성

        // 채팅방 저장
        return chatRoomRepository.save(chatRoom);
    }


}