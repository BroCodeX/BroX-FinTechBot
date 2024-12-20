package brocodex.fbot.constants;

import lombok.Getter;

@Getter
public enum CommandMessages {
    START_MESSAGE("Welcome to the Finance Bot! "),
    HELP_MESSAGE("""
            Available commands:\s
            ===\s
            /start - Start the bot\s
            /help - Show available commands\s
            /add_transaction - Add a new transaction\s
            /get_report - Make a financial report \s
            /edit_budget - Edit your budget\s
            /view_budget - View your current budget\s
            """),
    WAIT_TRANSACTION_AMOUNT("Please enter the transaction amount"),
    WAIT_FOR_NEW_BUDGET("Please type a new amount of the budget"),
    WAIT_FOR_DATA_FILTER("Please choose a data interval for the report"),
    WAIT_FOR_TYPE_FILTER("Please choose a type of transaction for the report");

    private final String description;

    CommandMessages(String description) {
        this.description = description;
    }
}
