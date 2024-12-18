package brocodex.fbot.service;

import brocodex.fbot.constants.ChatState;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatStateService {
    private final Map<Long, ChatState> chatStates = new HashMap<>();

    public void setChatState(long chatId, ChatState state) {
        chatStates.put(chatId, state);
    }

    public ChatState getChatState(long chatId) {
        return chatStates.get(chatId);
    }

    public void removeChatState(long chatId) {
        chatStates.remove(chatId);
    }
}
