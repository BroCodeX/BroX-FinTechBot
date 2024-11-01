package brocodex.fbot.dto.report;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReportFilterDTO {
    private LocalDate startDate; // Начальная дата фильтрации
    private LocalDate endDate; // Конечная дата фильтрации
    private String categoryName; // категория для фильтрации, опционально
}
