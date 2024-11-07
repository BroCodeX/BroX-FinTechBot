package brocodex.fbot.dto.transaction.report;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionFilterDTO {
    private LocalDateTime startDate; // Начальная дата фильтрации
    private LocalDateTime endDate; // Конечная дата фильтрации
    private String operationType; // тип операции доход / расход
    private String category;
}
