package brocodex.fbot.constants;

import lombok.Getter;

public enum UtilsMessage {
    ERROR("Something went wrong. Please type /help for assistance");

    @Getter
    private String descripiton;

    UtilsMessage(String descripiton) {
        this.descripiton = descripiton;
    }
}
