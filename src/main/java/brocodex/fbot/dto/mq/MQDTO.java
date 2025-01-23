package brocodex.fbot.dto.mq;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MQDTO {
    private String message;
    private Long chatId;
    private Long userId;
    private String firstName;
    private String userName;
}
