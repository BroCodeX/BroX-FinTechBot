package brocodex.fbot.dto.transaction.report;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TransactionReportDTO {
    private Long telegramId;
    private Double totalIncome; // Доход за период
    private Double totalExpense; // Расход за период
    private String operationType; // тип операции доход / расход
    private List<String> categoriesName; // категории, если устанавливались
}
