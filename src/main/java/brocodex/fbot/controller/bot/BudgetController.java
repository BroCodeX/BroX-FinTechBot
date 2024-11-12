package brocodex.fbot.controller.bot;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.dto.budget.BudgetDTO;
import brocodex.fbot.service.BudgetService;
import brocodex.fbot.service.handler.ResponseHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Controller
public class BudgetController {
    @Autowired
    private BudgetService service;

    private final ResponseHandlerService responseHandler;

    @Autowired
    public BudgetController(ResponseHandlerService responseHandlerService) {
        this.responseHandler = responseHandlerService;
    }

//    public Ability setBudget(Long chatId, Message message) {
//        return Ability.builder().build();
//    }

    public void setBudget(Long chatId, Message message) {
        try {
            BudgetDTO dto = new BudgetDTO();
            dto.setAmount(Double.parseDouble(message.getText()));

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
