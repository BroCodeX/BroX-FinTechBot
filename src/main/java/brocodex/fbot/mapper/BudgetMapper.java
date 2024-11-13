package brocodex.fbot.mapper;

import brocodex.fbot.dto.budget.BudgetDTO;
import brocodex.fbot.model.Budget;
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
public abstract class BudgetMapper {
    abstract Budget map(BudgetDTO dto);
    abstract BudgetDTO map(Budget budget);
}
