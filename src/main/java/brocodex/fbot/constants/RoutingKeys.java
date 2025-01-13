package brocodex.fbot.constants;

import lombok.Getter;

@Getter
public enum RoutingKeys {
    CREATED("transactions.created");

    private final String key;

    RoutingKeys(String key) {
        this.key = key;
    }
}
