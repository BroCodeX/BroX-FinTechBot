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
//    @Mapping(source = "telegramId", target = "user")
//    @Mapping(source = "telegramId", target = "user", qualifiedByName = "toUserEntity")
    public abstract Budget map(BudgetDTO dto);

//    @Mapping(source = "user.telegramId", target = "telegramId")
    public abstract BudgetDTO map(Budget budget);
}
