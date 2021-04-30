package tw.yukina.dcdos.program;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;

@Getter
@RequiredArgsConstructor
public class Register {

    private final Class<? extends AbstractProgramCode> programCode;
    private final Object program;
    private final Queue<Map<String, Object>> stdout = new ArrayDeque<>();
    private final Queue<String> stderr = new ArrayDeque<>();
    private final Queue<String> stdin = new ArrayDeque<>();

    @Setter
    private int exitCode = -1;

    @Setter
    private Object memory = null;

}
