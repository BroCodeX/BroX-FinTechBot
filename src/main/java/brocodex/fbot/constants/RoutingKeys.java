package brocodex.fbot.constants;

import lombok.Getter;

@Getter
public enum RoutingKeys {
    TRANS_CREATED("transactions.created"),
    BUDGET_CREATED("budget.created");

    private final String key;

    RoutingKeys(String key) {
        this.key = key;
    }
}
