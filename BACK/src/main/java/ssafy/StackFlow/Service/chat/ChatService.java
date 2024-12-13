package ssafy.StackFlow.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssafy.StackFlow.Domain.ChatMessage;
import ssafy.StackFlow.Repository.ChatMessageRepository;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }

    public void saveMessage(ChatMessage message) {
        chatMessageRepository.save(message);
    }
}