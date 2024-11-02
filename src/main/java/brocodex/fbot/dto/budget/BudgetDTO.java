package brocodex.fbot.dto.budget;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class BudgetDTO {
    private Double amount; // текущий бюджет
//    private Double monthlyBudget; // Месячный бюджет, если выставлен
//    private Double weeklyBudget; // Недельный бюджет, если выставлен
    private Double spentAmount; // Потраченная сумма
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
