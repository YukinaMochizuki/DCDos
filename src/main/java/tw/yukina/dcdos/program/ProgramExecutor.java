package tw.yukina.dcdos.program;

import lombok.Getter;
import tw.yukina.dcdos.manager.ProgramManager;
import tw.yukina.dcdos.session.AbstractSession;

import java.util.UUID;

public class ProgramExecutor {

    @Getter
    private final ProgramController programController;

    private final AbstractProgramCode programCode;

    public ProgramExecutor(ProgramManager programManager, AbstractProgramCode programCode, AbstractSession abstractSession) {
        Register register = new Register(abstractSession.getUpdateStdout());

        this.programCode = programCode;
        this.programController = new ProgramController(register, programManager, abstractSession, UUID.randomUUID().toString());

        programCode.setProgramController(programController);
    }

    public void start(){
        programCode.run();
    }
}
