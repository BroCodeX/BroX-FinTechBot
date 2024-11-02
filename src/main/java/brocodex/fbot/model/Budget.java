package brocodex.fbot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Entity
@Table(name = "budgets")
@Setter
@Getter
public class Budget implements BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Double amount; // весь бюджет

//    private Double monthlyBudget; // месячный бюджет - опционально
//
//    private Double weeklyBudget; // недельный бюджет- опционально

    private Double spentAmount; // потрачено всего

    @CreatedDate
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt;

    @OneToOne(mappedBy = "budget")
    private User user;
}
