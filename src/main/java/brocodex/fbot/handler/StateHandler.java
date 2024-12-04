package brocodex.fbot.handler;

import brocodex.fbot.service.ChatStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class StateHandler {
    @Autowired
    private ChatStateService chatStateService;

    private TelegramClient telegramClient;

    public void handleState(Update update) {

    }
}
