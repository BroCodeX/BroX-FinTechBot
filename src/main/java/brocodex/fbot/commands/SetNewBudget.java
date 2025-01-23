package brocodex.fbot.commands;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.constants.CommandMessages;
import brocodex.fbot.dto.mq.MQDTO;
import brocodex.fbot.service.BudgetService;
import brocodex.fbot.service.ChatStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SetNewBudget implements Command {
    @Autowired
    private ChatStateService stateService;

    @Autowired
    private BudgetService budgetService;

    @Override
    public SendMessage apply(MQDTO dto) {
        long chatId = dto.getChatId();

        String text = CommandMessages.WAIT_FOR_NEW_BUDGET.getDescription();

        stateService.setChatState(chatId, ChatState.WAITING_FOR_BUDGET);

        return SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(text)
                .build();
    }
}
