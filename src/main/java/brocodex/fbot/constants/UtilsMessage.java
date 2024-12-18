package brocodex.fbot.constants;

import lombok.Getter;

@Getter
public enum UtilsMessage {
    ERROR("Something went wrong. Please type /help for assistance"),
    INVALID_FORMAT("I don't understand your message. Please type again");

    private String descripiton;

    UtilsMessage(String descripiton) {
        this.descripiton = descripiton;
    }
}
