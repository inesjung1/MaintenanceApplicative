package type;

import java.util.UUID;

public class EventId {
    private final String value;

    public EventId() {
        this.value = UUID.randomUUID().toString();
    }

    public EventId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}

