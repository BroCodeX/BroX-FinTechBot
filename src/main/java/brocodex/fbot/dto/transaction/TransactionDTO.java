package brocodex.fbot.dto.transaction;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TransactionDTO {
    private Long id;
    private Long telegramId;
    private Double amount; // сумма
    private String description; // описание в виде заметки
    private String type; // тип операции: income или expense
    private LocalDateTime transactionDate; // дата транзакции
    private String categoryName; // название категории
}
