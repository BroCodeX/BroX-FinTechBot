package brocodex.fbot.dto.report;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportDTO {
    private Long telegramId;
    private Double totalIncome; // Доход за период
    private Double totalExpense; // Расход за период
    private String categoryName; // категория, опционально
}
