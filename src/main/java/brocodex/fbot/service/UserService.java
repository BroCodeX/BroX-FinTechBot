package brocodex.fbot.service;

import brocodex.fbot.dto.user.UserDTO;
import brocodex.fbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<UserDTO> getAllUsers(int limit) {
        return null;
    }

    public UserDTO createUser(UserDTO dto) {
        return null;
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
