package brocodex.fbot.controller.bot;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.dto.transaction.TransactionDTO;
import brocodex.fbot.service.TransactionService;
import brocodex.fbot.service.handler.CallbackHandlerService;
import brocodex.fbot.service.handler.ResponseHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Controller
public class TransactionsController {
    private TransactionDTO dto;

    private final ResponseHandlerService responseHandler;

    private final CallbackHandlerService callbackHandler;

    @Autowired
    private TransactionService service;

    @Autowired
    public TransactionsController(ResponseHandlerService responseHandlerService,
                                  CallbackHandlerService callbackHandler) {
        this.responseHandler = responseHandlerService;
        this.callbackHandler = callbackHandler;
    }

    public void replyToAddTransaction(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Please enter the transaction amount.");

        responseHandler.getSender().execute(sendMessage);
        responseHandler.updateChatState(chatId, ChatState.WAITING_FOR_TRANSACTION_AMOUNT);
        dto = new TransactionDTO();
    }

    public void addTransactionAmount(Long chatId, Message message) {
        try {
            dto.setAmount(Double.parseDouble(message.getText()));

            responseHandler.updateChatState(chatId, ChatState.WAITING_FOR_TRANSACTION_TYPE);
            sendTransactionTypeButtons(chatId);
        } catch (NumberFormatException ex) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Invalid amount. Please enter a valid number.");
        }
    }

    public void sendTransactionTypeButtons(Long chatId) {
        InlineKeyboardButton incomeButton = new InlineKeyboardButton();
        incomeButton.setText("income");
        incomeButton.setCallbackData("transaction_type_income");

        InlineKeyboardButton expenseButton = new InlineKeyboardButton();
        incomeButton.setText("expense");
        incomeButton.setCallbackData("transaction_type_expense");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(List.of(List.of(incomeButton), List.of(expenseButton)));

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Please choose the type of transaction.");
        sendMessage.setReplyMarkup(markup);
        try {
            responseHandler.getSender().execute(sendMessage);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addTransactionType(Long chatId, String type) {
        try {
            dto.setType(type);
            replyToAddTransactionDescription(chatId);
            responseHandler.updateChatState(chatId, ChatState.WAITING_FOR_TRANSACTION_DESCRIPTION);
        } catch (NumberFormatException ex) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Invalid type of transaction. Please choose income or expense");
        }
    }

    public void replyToAddTransactionDescription(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Please enter the transaction description.");

        responseHandler.getSender().execute(sendMessage);
    }

    public void addTransactionDescription(Long chatId, Message message) {
        try {
            dto.setDescription(message.getText());
            replyToAddTransactionCategory(chatId);
            responseHandler.updateChatState(chatId, ChatState.WAITING_FOR_TRANSACTION_CATEGORY);
        } catch (NumberFormatException ex) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Sorry, something went wrong. Please retype the description");
        }
    }

    public void replyToAddTransactionCategory(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Please enter the transaction's category.");

        responseHandler.getSender().execute(sendMessage);
    }

    public void addTransactionCategory(Long chatId, Message message) {
        try {
            dto.setCategoryName(message.getText());
            replyTransactionSuccess(chatId);
        } catch (NumberFormatException ex) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Sorry, something went wrong. Please retype the description");
        }
    }

    public void replyTransactionSuccess(Long chatId) {
        var transaction = service.createTransaction(dto);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        StringBuilder builder = new StringBuilder();
        builder.append("Transaction success\n");
        builder.append("amount: ").append(transaction.getAmount()).append("\n");
        builder.append("type: ").append(transaction.getType()).append("\n");
        builder.append("description: ").append(transaction.getDescription()).append("\n");
        builder.append("category: ").append(transaction.getCategoryName()).append("\n");
        sendMessage.setText(builder.toString());

        responseHandler.getSender().execute(sendMessage);
        responseHandler.updateChatState(chatId, ChatState.READY);

        dto = null;
    }

    public void viewTransactions() {

    }

}
