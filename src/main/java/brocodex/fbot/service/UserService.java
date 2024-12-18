package brocodex.fbot.service;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.constants.CommandMessages;
import brocodex.fbot.dto.user.UserDTO;
import brocodex.fbot.mapper.UserMapper;
import brocodex.fbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private ChatStateService stateService;

    public List<UserDTO> getAllUsers(int limit) {
        return null;
    }

    public SendMessage welcomeUser(Update update) {
        long chatId = update.getMessage().getChatId();
        long userId = update.getMessage().getFrom().getId();
        String userName = update.getMessage().getFrom().getUserName();
        String firstName = update.getMessage().getFrom().getFirstName();

        UserDTO dto = new UserDTO();
        dto.setUsername(userName);
        dto.setTelegramId(userId);

        if (isUserPresent(userId)) {
            return SendMessage // Create a message object
                    .builder()
                    .chatId(chatId)
                    .text(CommandMessages.HELP_MESSAGE.getDescription())
                    .build();
        }

        createUser(dto);

        String text = CommandMessages.START_MESSAGE.getDescription() +
                firstName +
                "\n" +
                "=====\n" +
                "Please, set your budget or type /help to show the commands";

        SendMessage sendMessage = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(text)
                .build();

        stateService.setChatState(chatId, ChatState.WAITING_FOR_BUDGET);

        return sendMessage;
    }

    public UserDTO createUser(UserDTO dto) {
        var maybeUser = repository.findByTelegramId(dto.getTelegramId());
        if (maybeUser.isPresent()) {
            throw new IllegalStateException("User with this Telegram ID already exists.");
        }
        var user = mapper.map(dto);
        repository.save(user);
        return mapper.map(user);
    }

    public UserDTO showUser(Long id) {
        return null;
    }

    public UserDTO updateUser(Long id, UserDTO dto) {
        return null;
    }

    public void destroyUser(Long id) {

    }

    public boolean isUserPresent(Long id) {
        return repository.findByTelegramId(id).isPresent();
    }
}
