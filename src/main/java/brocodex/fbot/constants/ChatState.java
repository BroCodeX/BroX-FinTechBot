package brocodex.fbot.constants;

import lombok.Getter;

public enum ChatState {
    WAITING_FOR_BUDGET("Waiting for budget input"),
    WAITING_FOR_TRANSACTION_AMOUNT("Waiting for transaction amount input"),
    WAITING_FOR_TRANSACTION_TYPE("Waiting for transaction type input"),
    WAITING_FOR_TRANSACTION_DESCRIPTION("Waiting for transaction description input"),
    WAITING_FOR_TRANSACTION_CATEGORY("Waiting for transaction description category"),
    TRANSACTION_REPORT_FILTERS("Waiting for report filters input"),
    READY("Bot is ready for work"),
    ADMIN_MODE("Iit admin mode");

    @Getter
    private final String description;

    ChatState(String description) {
        this.description = description;
    }
}
