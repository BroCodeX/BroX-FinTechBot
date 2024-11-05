package brocodex.fbot.dto.transaction.report;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TransactionFilterDTO {
    private LocalDate startDate; // Начальная дата фильтрации
    private LocalDate endDate; // Конечная дата фильтрации
    private String operationType; // тип операции доход / расход
    private String category;
}
