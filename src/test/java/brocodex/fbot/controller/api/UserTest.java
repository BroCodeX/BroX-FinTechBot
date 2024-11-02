package brocodex.fbot.controller.api;

import brocodex.fbot.model.User;
import brocodex.fbot.repository.UserRepository;
import brocodex.fbot.utils.ModelsGenerator;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {
    @Autowired
    private ModelsGenerator generator;

    @Autowired
    private UserRepository repository;

    private User user;

    @BeforeEach
    public void prepareData() {
        user = Instancio.of(generator.makeUser()).create();
    }

    @Test
    public void createTest() {

    }
}
