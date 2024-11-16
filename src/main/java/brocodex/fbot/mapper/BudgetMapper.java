package brocodex.fbot.mapper;

import brocodex.fbot.dto.budget.BudgetDTO;
import brocodex.fbot.model.Budget;
import org.mapstruct.*;

@Mapper(
        uses = {ReferenceMapper.class},
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public abstract class BudgetMapper {
//    @Mapping(source = "telegramId", target = "user.telegramId")
    @Mapping(source = "telegramId", target = "user")
    public abstract Budget map(BudgetDTO dto);

    public abstract BudgetDTO map(Budget budget);
}
