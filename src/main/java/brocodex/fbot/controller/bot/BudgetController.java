package brocodex.fbot.controller.bot;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.dto.budget.BudgetDTO;
import brocodex.fbot.service.BudgetService;
import brocodex.fbot.service.handler.ResponseHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class BudgetController {
    @Autowired
    private BudgetService service;

    private final ResponseHandlerService responseHandler;

    public BudgetController(ResponseHandlerService responseHandlerService) {
        this.responseHandler = responseHandlerService;
    }

    public void setBudget(Long chatId, Message message) {
        try {
            BudgetDTO dto = new BudgetDTO();
            dto.setAmount(Double.parseDouble(message.getText()));
            dto.setTelegramId(message.getFrom().getId());

            var budget = service.createBudget(dto);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Your budget is set to: " + budget.getAmount());

            responseHandler.getSender().execute(sendMessage);
            responseHandler.updateChatState(chatId, ChatState.READY);
        } catch (NumberFormatException ex) {
            SendMessage errorMessage = new SendMessage();
            errorMessage.setChatId(chatId);
            errorMessage.setText("Please enter a valid number for your budget.");
            responseHandler.getSender().execute(errorMessage);
        }
    }

    public void editBudget() {

    }
}
