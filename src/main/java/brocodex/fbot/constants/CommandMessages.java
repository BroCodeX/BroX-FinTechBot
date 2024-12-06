package brocodex.fbot.constants;

import lombok.Getter;

public enum CommandMessages {
    START_MESSAGE("Welcome to the Finance Bot! "),
    HELP_MESSAGE("Available commands: \n" +
            "/start - Start the bot \n" +
            "/help - Show available commands \n" +
            "/add_transaction - Add a new transaction \n" +
            "/view_transactions - View your transactions from the period \n" +
            "/edit_budget - Edit your budget \n" +
            "/view_budget - View your current budget \n" +
            "/get_report - Make a financial report  \n" +
            "/stop - Stop the bot \n"),
    WAIT_TRANSACTION_AMOUNT("Please enter the transaction amount");

    @Getter
    private final String description;

    CommandMessages(String description) {
        this.description = description;
    }
}
