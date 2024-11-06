package brocodex.fbot.dto.transaction.report;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionFilterDTO {
    private LocalDateTime startDate; // Начальная дата фильтрации
    private LocalDateTime endDate; // Конечная дата фильтрации
    private String operationType; // тип операции доход / расход
    private String category;
}
