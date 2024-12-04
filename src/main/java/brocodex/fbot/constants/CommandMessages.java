package brocodex.fbot.constants;

import lombok.Getter;

public enum CommandMessages {
    START_MESSAGE("Welcome to the Finance Bot!" +
            "\n" +
            "Please enter your name or type yes if your name is: "),
    HELP_MESSAGE("Available commands: \n" +
            "/start - Start the bot \n" +
            "/help - Show available commands \n" +
            "/add_transaction - Add a new income or expense transaction \n" +
            "/view_transactions - View your transactions from the period \n" +
            "/edit_budget - Edit your budget \n" +
            "/view_budget - View your current budget \n" +
            "/get_report - Make a financial report of your budget and transactions \n" +
            "/stop - Stop the bot \n");

    @Getter
    private final String description;

    CommandMessages(String description) {
        this.description = description;
    }
}
