package brocodex.fbot.model.transaction;

import brocodex.fbot.model.BaseModel;
import brocodex.fbot.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Transaction implements BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double amount; // сумма

    @NotBlank
    private String description; // описание в виде заметки

    @NotBlank
    private String type; // тип операции: income или expense

    @CreatedDate
    private LocalDate transactionDate; // дата транзакции

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private TransactionCategory category;
}