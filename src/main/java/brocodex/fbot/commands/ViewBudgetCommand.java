package brocodex.fbot.commands;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.constants.CommandMessages;
import brocodex.fbot.service.BudgetService;
import brocodex.fbot.service.ChatStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ViewBudgetCommand implements Command {
    @Autowired
    private ChatStateService stateService;

    @Autowired
    private BudgetService budgetService;

    @Override
    public SendMessage apply(Update update) {
        long chatId = update.getMessage().getChatId();
        long userId = update.getMessage().getFrom().getId();

        return budgetService.showBudget(userId, chatId);
    }
}
