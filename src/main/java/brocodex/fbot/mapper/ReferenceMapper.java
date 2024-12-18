package brocodex.fbot.mapper;

import brocodex.fbot.model.BaseModel;
import brocodex.fbot.model.User;
import brocodex.fbot.model.transaction.TransactionCategory;
import brocodex.fbot.repository.UserRepository;
import brocodex.fbot.repository.transactions.TransactionCategoryRepository;
import jakarta.persistence.EntityManager;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public class ReferenceMapper {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionCategoryRepository categoryRepository;

    public <T extends BaseModel> T toEntity(Long id, @TargetType Class<T> entityClass) {
        return id != null ? entityManager.find(entityClass, id) : null;
    }

    @Named("toUserEntity")
    public User toUserEntity(Long telegramId) {
        return userRepository.findByTelegramId(telegramId).orElseThrow(() ->
                new NoSuchElementException("This telegram id hasn't found: " + telegramId));
    }

    @Named("toTransactionCategory")
    public TransactionCategory toTransactionCategory(String transactionCategory) {
        return categoryRepository.findBySlug(transactionCategory.trim()).orElseThrow(() ->
                new NoSuchElementException("This category hasn't found: " + transactionCategory));
    }
}
