package brocodex.fbot.commands;

import brocodex.fbot.dto.mq.MQDTO;
import brocodex.fbot.service.BudgetService;
import brocodex.fbot.service.ChatStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ViewBudget implements Command {
    @Autowired
    private ChatStateService stateService;

    @Autowired
    private BudgetService budgetService;

    @Override
    public SendMessage apply(MQDTO dto) {
        long chatId = dto.getChatId();
        long userId = dto.getUserId();

        return budgetService.showBudget(userId, chatId);
    }
}
