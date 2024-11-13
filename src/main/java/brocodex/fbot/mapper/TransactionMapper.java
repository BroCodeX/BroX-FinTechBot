package brocodex.fbot.mapper;

import brocodex.fbot.dto.transaction.TransactionDTO;
import brocodex.fbot.model.transaction.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public abstract class TransactionMapper {
    abstract Transaction map(TransactionDTO dto);
    abstract TransactionDTO map(Transaction transaction);
}
