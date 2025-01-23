package brocodex.fbot.dto.mq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MQDTO {
    @JsonProperty("message")
    private String message;

    @JsonProperty("chatId")
    private long chatId;

    @JsonProperty("userId")
    private long userId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("userName")
    private String userName;
}
