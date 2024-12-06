package brocodex.fbot.controller.bot;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.dto.transaction.TransactionDTO;
import brocodex.fbot.factory.KeyboardFactory;
import brocodex.fbot.service.ChatStateService;
import brocodex.fbot.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;


public class TransactionsController {
    private TransactionDTO dto;

    @Autowired
    private ChatStateService chatStateService;

    @Autowired
    private TransactionService service;

    public SendMessage addTransactionAmount(Long chatId, String amount, Long userId) {
        try {
            dto.setAmount(Double.parseDouble(amount));

            chatStateService.setChatState(chatId, ChatState.WAITING_FOR_TRANSACTION_TYPE);

            sendTransactionTypeButtons(chatId);
        } catch (NumberFormatException ex) {
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Invalid amount. Please enter a valid number.")
                    .build();
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
            responseHandler.updateChatState(chatId, ChatState.WAITING_FOR_TRANSACTION_CATEGORY);
            replyToAddTransactionCategory(chatId);
        } catch (NumberFormatException ex) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Sorry, something went wrong. Please retype the description");
        }
    }

    public void replyToAddTransactionCategory(Long chatId) {
        String text = "Please choose the transaction's category.";
        var state = ChatState.WAITING_FOR_TRANSACTION_CATEGORY;
        String type = dto.getType();

        if (type.equals("income")) {
            var variant = KeyboardFactory.getIncomeCategories();
            promptWithKeyboardForState(chatId, text, variant, state);
        } else if(type.equals("expense")) {
            var variant = KeyboardFactory.getExpenseCategories();
            promptWithKeyboardForState(chatId, text, variant, state);
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("You should set only income or expense");
            responseHandler.getSender().execute(sendMessage);
            responseHandler.updateChatState(chatId, ChatState.WAITING_FOR_TRANSACTION_TYPE);
            sendTransactionTypeButtons(chatId);
        }
    }

    public void addTransactionCategory(Long chatId, Message message) {
        try {
            dto.setCategoryName(message.getText());
            replyTransactionSuccess(chatId);
        } catch (NumberFormatException ex) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Sorry, something went wrong. Please retype the description");
            replyToAddTransactionCategory(chatId);
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

    private void promptWithKeyboardForState(long chatId, String text, ReplyKeyboard variant, ChatState state) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(variant);
        responseHandler.getSender().execute(sendMessage);
        responseHandler.updateChatState(chatId, state);
    }

}
