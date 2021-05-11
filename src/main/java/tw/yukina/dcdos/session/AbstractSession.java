package tw.yukina.dcdos.session;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tw.yukina.dcdos.constants.ProgramStatus;
import tw.yukina.dcdos.manager.ProgramManager;
import tw.yukina.dcdos.manager.SessionManager;
import tw.yukina.dcdos.program.AbstractProgramCode;
import tw.yukina.dcdos.program.ProgramExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractSession {

    @Getter
    private final List<ProgramExecutor> programExecutors = new ArrayList<>();

    @Getter
    @Setter
    private ProgramExecutor activeProgramExecutor;

    @Getter
    @Setter
    private String name = "";

    @Getter
    private final String uuid = UUID.randomUUID().toString();

    @Getter
    @Setter
    private boolean isActive = false;

    @Getter
    protected final ProgramManager programManager;

    @Getter
    protected final SessionManager sessionManager;

    @Autowired
    private ApplicationContext applicationContext;

    protected AbstractSession(ProgramManager programManager, SessionManager sessionManager) {
        this.programManager = programManager;
        this.sessionManager = sessionManager;
    }

    public abstract void stdoutPrint();

    public abstract void stderrPrint();

    public abstract void stdinPut(String input);

    public abstract void updateStdoutPrint();

    public void start(){
        while (programExecutors.size() > 0 || activeProgramExecutor != null) {

            if(activeProgramExecutor == null) {
                activeProgramExecutor = programExecutors.get(programExecutors.size() - 1);
                programExecutors.remove(programExecutors.size() - 1);
            }

            activeProgramExecutor.getProgramController().setProgramStatus(ProgramStatus.RUNNING);
            activeProgramExecutor.start();
            refresh();

            activeProgramExecutor = null;
        }
    }

    public void fork(Class<? extends AbstractProgramCode> programCodeClass){
        //  TODO
    }

    public void call(Class<? extends AbstractProgramCode> programCodeClass){
        AbstractProgramCode abstractProgramCode = applicationContext.getBean(programCodeClass);

        ProgramExecutor programExecutor = activeProgramExecutor;
        activeProgramExecutor = programExecutorBuilder(abstractProgramCode);
        activeProgramExecutor.getProgramController().setProgramStatus(ProgramStatus.RUNNING);
        activeProgramExecutor.start();
        refresh();

        activeProgramExecutor = programExecutor;
    }

    private ProgramExecutor programExecutorBuilder(AbstractProgramCode abstractProgramCode){
        return new ProgramExecutor(getProgramManager(), abstractProgramCode, this);
    }

    public void refresh(){
        stdoutPrint();
        stderrPrint();
        updateStdoutPrint();
    }
}
