package brocodex.fbot.handler;

import brocodex.fbot.constants.ChatState;
//import brocodex.fbot.controller.bot.BudgetController;
//import brocodex.fbot.controller.bot.TransactionsController;
//import brocodex.fbot.controller.bot.UserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ResponseHandler {

    private TelegramClient telegramClient;

    private final Map<Long, ChatState> chatStates = new HashMap<>();
//    private final UserController userController;
//    private final BudgetController budgetController;
//    private final TransactionsController transactionsController;

    public void handleUpdate(Update update, TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String receivedMessage = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            long userId = update.getMessage().getFrom().getId();
            String userName = update.getMessage().getFrom().getUserName();

            botAnswerUtils(chatId, receivedMessage, userName);
        } else if (update.hasCallbackQuery()) {
            String receivedMessage = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            long userId = update.getCallbackQuery().getFrom().getId();
            String userName = update.getMessage().getFrom().getUserName();

            //тут будет callbackHandler
        }
    }

    public void start(Long chatId, String message, String name) {
        StringBuilder builder = new StringBuilder();
        builder.append("Welcome to the Finance Bot!").append("\n");
        builder.append("Please enter your name or type yes if your name is: " + name);

        SendMessage sendMessage = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(builder.toString())
                .build();

        try {
            telegramClient.execute(sendMessage); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void addTransaction(Long chatId, String name) {
    }

    public void viewTransactions(Long chatId, String name) {
    }

    public void editBudget(Long chatId, String name) {
    }

    public void viewBudget(Long chatId, String name) {
    }

    public void getReport(Long chatId, String name) {
    }

    public void help(Long chatId, String name) {
        StringBuilder builder = new StringBuilder();
        builder.append("Available commands: \n");
        builder.append("/start - Start the bot \n");
        builder.append("/help - Show available commands \n");
        builder.append("/add_transaction - Add a new income or expense transaction \n");
        builder.append("/view_transactions - View your transactions from the period \n");
        builder.append("/edit_budget - Edit your budget \n");
        builder.append("/view_budget - View your current budget \n");
        builder.append("/get_report - Make a financial report of your budget and transactions \n");
        builder.append("/stop - Stop the bot \n");

        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(builder.toString())
                .build();

        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }

    public void stop(Long chatId, String name) {
    }

    private void botAnswerUtils(Long chatId, String message, String name) {
        switch (message) {
            case "/start":
                start(chatId, message, name);
                break;
            case "/help":
                help(chatId, name);
                break;
            case "/add_transaction":
                addTransaction(chatId, name);
                break;
            case "/view_transactions":
                viewTransactions(chatId, name);
                break;
            case "/edit_budget":
                editBudget(chatId, name);
                break;
            case "/view_budget":
                viewBudget(chatId, name);
                break;
            case "/get_report":
                getReport(chatId, name);
                break;
            case "/stop":
                stop(chatId, name);
                break;
            default: handleUnexpectedMessage(chatId, message);
        }
    }

    public void handleUnexpectedMessage(Long chatId, String message) {
        log.error("");
        StringBuilder builder = new StringBuilder();
        builder.append("I don't understand your message: ").append(message).append(" \n");
        builder.append("You can type /help for assistance.");
        SendMessage sendMessage = SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(builder.toString())
                .build();
        try {
            telegramClient.execute(sendMessage); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
