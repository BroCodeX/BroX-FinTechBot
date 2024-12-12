package brocodex.fbot.controller.bot;

import brocodex.fbot.constants.CommandMessages;
import brocodex.fbot.dto.budget.BudgetDTO;
import brocodex.fbot.service.BudgetService;
import brocodex.fbot.service.ChatStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class BudgetController {
    @Autowired
    private BudgetService service;

    @Autowired
    private ChatStateService chatStateService;

    public SendMessage setBudget(Long chatId, String amount, Long userId) {
        try {
            BudgetDTO dto = new BudgetDTO();
            dto.setAmount(Double.parseDouble(amount));
            dto.setTelegramId(userId);

            var budget = service.createBudget(dto);

            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Your budget is set to: " + budget.getAmount() + "\n" + "\n" +
                            CommandMessages.HELP_MESSAGE.getDescription())
                    .build();
            sendMessage.setChatId(chatId);
            chatStateService.setChatState(chatId, null);

            return sendMessage;
        } catch (NumberFormatException ex) {
            return SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Your message is incorrect. Please enter a valid data for your budget")
                    .build();
        }
    }

    public void editBudget() {

    }
}
