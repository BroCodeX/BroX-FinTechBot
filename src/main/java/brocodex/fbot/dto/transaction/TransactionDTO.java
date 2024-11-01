package brocodex.fbot.dto.transaction;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TransactionDTO {
    private Long id;
    private Double amount; // сумма
    private String description; // описание в виде заметки
    private String type; // тип операции: income или expense
    private LocalDate transactionDate; // дата транзакции
    private String categoryName; // название категории
}
