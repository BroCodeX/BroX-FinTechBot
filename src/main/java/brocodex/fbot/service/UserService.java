package brocodex.fbot.service;

import brocodex.fbot.dto.user.UserDTO;
import brocodex.fbot.mapper.UserMapper;
import brocodex.fbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    public List<UserDTO> getAllUsers(int limit) {
        return null;
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
}
