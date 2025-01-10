package brocodex.fbot.mapper;

import brocodex.fbot.dto.transaction.TransactionDTO;
import brocodex.fbot.model.transaction.Transaction;
import jakarta.transaction.Transactional;
import org.mapstruct.*;

@Mapper(
        uses = {ReferenceMapper.class},
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public abstract class TransactionMapper {
    @Mapping(source = "telegramId", target = "user", qualifiedByName = "toUserEntity")
    @Mapping(source = "category", target = "category", qualifiedByName = "toTransactionCategory")
    @Mapping(source = "budget", target = "budget")
    public abstract Transaction map(TransactionDTO dto);

    @Mapping(source = "category.slug", target = "category")
    @Mapping(source = "budget.id", target = "budget")
    public abstract TransactionDTO map(Transaction transaction);
}
