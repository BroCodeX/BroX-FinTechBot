package brocodex.fbot.specification;

import brocodex.fbot.dto.transaction.report.TransactionFilterDTO;
import brocodex.fbot.model.transaction.Transaction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionSpec {
    public Specification<Transaction> build(TransactionFilterDTO filter) {
        return withStartDate(filter.getStartDate())
                .and(withEndDate(filter.getEndDate()))
                .and(withOperationType(filter.getOperationType()))
                .and(withCategory(filter.getCategory()));
    }

    public Specification<Transaction> withStartDate(LocalDateTime startDate) {
        return ((root, query, cb) ->
                startDate == null ? cb.conjunction() :
                cb.greaterThanOrEqualTo(root.get("transactionDate"), startDate));
    }

    public Specification<Transaction> withEndDate(LocalDateTime endDate) {
        return ((root, query, cb) ->
                endDate == null ? cb.conjunction() :
                cb.lessThanOrEqualTo(root.get("transactionDate"), endDate));
    }

    public Specification<Transaction> withOperationType(String operationType) {
        return ((root, query, cb) -> {
            if (operationType == null || operationType.isBlank()) {
                return cb.conjunction();
            } else if(operationType.equals("all")) {
                return cb.conjunction();
            } else {
                return cb.equal(root.get("type"), operationType);
            }
        });
    }

    public Specification<Transaction> withCategory(String category) {
        return ((root, query, cb) -> {
            if(category == null || category.isBlank()) {
                return cb.conjunction();
            } else {
                return cb.equal(cb.lower(root.get("category").get("slug")), category.toLowerCase());
            }
        });

    }
}
