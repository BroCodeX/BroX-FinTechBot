package brocodex.fbot.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserDTO {
    private Long id;
    private String username;
    private Long telegramId;
    private LocalDate createdAt;
}
