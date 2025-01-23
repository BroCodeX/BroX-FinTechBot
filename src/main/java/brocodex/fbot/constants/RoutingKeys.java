package brocodex.fbot.constants;

import lombok.Getter;

@Getter
public enum RoutingKeys {
    DIRECT("direct.message"),
    CALLBACK("callback.message");

    private final String key;

    RoutingKeys(String key) {
        this.key = key;
    }
}
