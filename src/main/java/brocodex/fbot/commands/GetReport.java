package brocodex.fbot.commands;

import brocodex.fbot.dto.mq.MQDTO;
import brocodex.fbot.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class GetReport implements Command {

    @Autowired
    private ReportService reportService;

    @Override
    public SendMessage apply(MQDTO dto) {
        long chatId = dto.getChatId();

        return reportService.welcomeMessage(chatId);
    }
}
