package brocodex.fbot.controller.bot;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.dto.transaction.TransactionDTO;
import brocodex.fbot.service.TransactionService;
import brocodex.fbot.service.handler.ResponseHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Controller
public class TransactionsController {
    private TransactionDTO dto;

    private final ResponseHandlerService responseHandler;

    @Autowired
    private TransactionService service;

    @Autowired
    public TransactionsController(ResponseHandlerService responseHandlerService) {
        this.responseHandler = responseHandlerService;
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

            replyToAddTransactionType(chatId);
            responseHandler.updateChatState(chatId, ChatState.WAITING_FOR_TRANSACTION_TYPE);
            addTransactionType(chatId);
        } catch (NumberFormatException ex) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Invalid amount. Please enter a valid number.");
        }
    }

    public void replyToAddTransactionType(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Please enter the transaction type.");

        responseHandler.getSender().execute(sendMessage);
    }

    public void addTransactionType(Long chatId) {
        try {
            dto.setType();
            replyToAddTransactionDescription(chatId);
            responseHandler.updateChatState(chatId, ChatState.WAITING_FOR_TRANSACTION_DESCRIPTION);
        } catch (NumberFormatException ex) {

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
