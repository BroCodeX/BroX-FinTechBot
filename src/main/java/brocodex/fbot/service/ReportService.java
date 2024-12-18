package brocodex.fbot.service;

import brocodex.fbot.constants.ChatState;
import brocodex.fbot.constants.CommandMessages;
import brocodex.fbot.dto.transaction.report.TransactionReportDTO;
import brocodex.fbot.dto.transaction.report.TransactionFilterDTO;
import brocodex.fbot.factory.KeyboardFactory;
import brocodex.fbot.model.transaction.Transaction;
import brocodex.fbot.repository.transactions.TransactionRepository;
import brocodex.fbot.specification.TransactionSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    TransactionFilterDTO dto;

    @Autowired
    private TransactionSpec spec;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ChatStateService stateService;

    public SendMessage welcomeMessage(Long chatID) {
        var markup = KeyboardFactory.getDataFilters();

        stateService.setChatState(chatID, ChatState.WAITING_FOR_DATA_FILTERS);

        return SendMessage.builder()
                .chatId(chatID)
                .text(CommandMessages.WAIT_FOR_DATA_FILTER.getDescription())
                .replyMarkup(markup)
                .build();
    }

    public SendMessage addDataFilter(Long chatID, Long userID, String data) {
        LocalDateTime startDate = LocalDateTime.parse(data);
        LocalDateTime endDate = LocalDateTime.now();

        dto = new TransactionFilterDTO();

        dto.setTelegramId(userID);
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);

        var markup = KeyboardFactory.getTypeFilters();

        stateService.setChatState(chatID, ChatState.WAITING_FOR_TYPE_FILTERS);

        return SendMessage.builder()
                .chatId(chatID)
                .text(CommandMessages.WAIT_FOR_TYPE_FILTER.getDescription())
                .replyMarkup(markup)
                .build();
    }

    public SendMessage addTypeFilter(Long chatID, String type) {
        dto.setOperationType(type);

        Specification<Transaction> specification =  spec.build(dto);

        List<Transaction> transactionList = transactionRepository.findAll(specification);

        return showReport(transactionList, chatID);
    }

    public SendMessage showReport(List<Transaction> transactionList, Long chatID) {
        dto = null;
        stateService.removeChatState(chatID);
        return SendMessage.builder()
                .chatId(chatID)
                .text("in progress filters")
                .build();
    }
}
