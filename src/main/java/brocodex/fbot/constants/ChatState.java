package brocodex.fbot.constants;

import lombok.Getter;

@Getter
public enum ChatState {
    WAITING_FOR_BUDGET("Waiting for budget input"),
    WAITING_FOR_TRANSACTION_AMOUNT("Waiting for transaction amount input"),
    WAITING_FOR_TRANSACTION_TYPE("Waiting for transaction type input"),
    WAITING_FOR_TRANSACTION_DESCRIPTION("Waiting for transaction description input"),
    WAITING_FOR_TRANSACTION_CATEGORY("Waiting for transaction description category"),
    WAITING_FOR_DATA_FILTERS("Waiting for choose report's data filters"),
    WAITING_FOR_TYPE_FILTERS("Waiting for choose report's type filters");

    private final String description;

    ChatState(String description) {
        this.description = description;
    }
}
