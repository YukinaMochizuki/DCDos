package tw.yukina.dcdos.program;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import tw.yukina.dcdos.session.AbstractStandardOutput;

import java.util.*;

@Getter
@RequiredArgsConstructor
public class Register {
    private final Queue<Map<String, Object>> stdout = new ArrayDeque<>();
    private final List<Map<String, Object>> updateStdout;
    private final Set<AbstractStandardOutput> standardOutputs = new HashSet<>();
    private final Queue<String> stderr = new ArrayDeque<>();
    private final Queue<String> stdin = new ArrayDeque<>();
}
