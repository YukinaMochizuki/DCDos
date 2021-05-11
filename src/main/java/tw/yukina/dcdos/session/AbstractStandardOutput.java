package tw.yukina.dcdos.session;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public abstract class AbstractStandardOutput {

    @Getter
    @Setter
    private String message;

    @Getter
    private final String uuid;

    protected AbstractStandardOutput(String uuid) {
        this.uuid = uuid;
    }

    public abstract void updateMessage(Map<String, Object> messageWithOption);

    public abstract void updateMessage(String message);
}
