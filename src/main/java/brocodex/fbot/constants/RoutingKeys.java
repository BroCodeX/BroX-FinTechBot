package brocodex.fbot.constants;

import lombok.Getter;

@Getter
public enum RoutingKeys {
    UPDATE("update.message");

    private final String key;

    RoutingKeys(String key) {
        this.key = key;
    }
}
