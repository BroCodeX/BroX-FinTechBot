package brocodex.fbot.controller.bot;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.dto.transaction.TransactionDTO;
import brocodex.fbot.factory.KeyboardFactory;
import brocodex.fbot.service.ChatStateService;
import brocodex.fbot.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class TransactionsController {
    private TransactionDTO dto;

    @Autowired
    private ChatStateService chatState;

    @Autowired
    private TransactionService service;

    public SendMessage addTransactionAmount(Long chatId, String amount, Long userId) {
        try {
            dto = new TransactionDTO();
            dto.setAmount(Double.parseDouble(amount));
            dto.setTelegramId(userId);

            var markup = KeyboardFactory.getTransactionTypes();

            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(chatId)
                    .replyMarkup(markup)
                    .text("Choose a transaction type: ")
                    .build();

            chatState.setChatState(chatId, ChatState.WAITING_FOR_TRANSACTION_TYPE);

            return sendMessage;
        } catch (NumberFormatException ex) {
            return SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Invalid amount. Please enter a valid amount.")
                    .build();
        }
    }

    public SendMessage addTransactionType(Long chatId, String type) {
        try {
            dto.setType(type);
            var markup = type.equals("income") ? KeyboardFactory.getIncomeCategories()
                    : KeyboardFactory.getExpenseCategories();
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(chatId)
                    .replyMarkup(markup)
                    .text("Please choose a category of the transaction")
                    .build();
            chatState.setChatState(chatId, ChatState.WAITING_FOR_TRANSACTION_CATEGORY);

            return sendMessage;
        } catch (NumberFormatException ex) {
            return SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Invalid type of transaction. Please choose income or expense")
                    .build();
        }
    }

    public SendMessage addTransactionCategory(Long chatId, String message) {
        try {
            dto.setCategory(message);
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Please enter a description of the transaction")
                    .build();

            chatState.setChatState(chatId, ChatState.WAITING_FOR_TRANSACTION_DESCRIPTION);

            return sendMessage;
        } catch (NumberFormatException ex) {
            return SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Sorry, something went wrong. Please choose a category")
                    .build();
        }
    }

    public SendMessage addTransactionDescription(Long chatId, String message) {
        try {
            dto.setDescription(message);
            return transactionSuccess(chatId);
        } catch (NumberFormatException ex) {
            return SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Sorry, something went wrong. Please retype the description")
                    .build();
        }
    }


    public SendMessage transactionSuccess(Long chatId) {
        try {
            var transaction = service.createTransaction(dto);

            StringBuilder builder = new StringBuilder();
            builder.append("Transaction success\n");
            builder.append("amount: ").append(transaction.getAmount()).append("\n");
            builder.append("type: ").append(transaction.getType()).append("\n");
            builder.append("category: ").append(transaction.getCategory()).append("\n");
            builder.append("description: ").append(transaction.getDescription()).append("\n");

            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(builder.toString())
                    .build();

            chatState.setChatState(chatId, null);
            dto = null;

            return sendMessage;
        } catch (RuntimeException ex) {
            chatState.setChatState(chatId, null);
            dto = null;

            return SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(ex.getMessage())
                    .build();
        }
    }

}
