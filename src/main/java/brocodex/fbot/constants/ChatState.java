package brocodex.fbot.constants;

import lombok.Getter;

public enum ChatState {
    WAITING_FOR_USERNAME("Waiting for username input"),
    WAITING_FOR_BUDGET("Waiting for budget input"),
    WAITING_FOR_TRANSACTION("Waiting for transaction input"),
    TRANSACTION_REPORT_FILTERS("Waiting for report filters input"),
    READY("Bot is ready for work"),
    ADMIN_MODE("Iit admin mode");

    @Getter
    private final String description;

    ChatState(String description) {
        this.description = description;
    }
}
