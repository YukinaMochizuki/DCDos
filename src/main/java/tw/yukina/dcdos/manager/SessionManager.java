package tw.yukina.dcdos.manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import tw.yukina.dcdos.program.AbstractProgramCode;
import tw.yukina.dcdos.program.ProgramExecutor;
import tw.yukina.dcdos.program.core.Shell;
import tw.yukina.dcdos.session.AbstractSession;
import tw.yukina.dcdos.session.TelegramSession;

@Service
public class SessionManager {

//    Set<AbstractSession> sessions = new HashSet<>();

    private final ApplicationContext applicationContext;

    private final ProgramManager programManager;

    private AbstractSession abstractSession;

    @Value("${telegram.permission.master}")
    private int adminUserId;

    public SessionManager(ApplicationContext applicationContext, ProgramManager programManager) {
        this.applicationContext = applicationContext;
        this.programManager = programManager;
    }

    public void newSession(){
        TelegramSession telegramSession = applicationContext.getBean(TelegramSession.class);
        telegramSession.setChat_id(adminUserId);

        AbstractProgramCode programCode = applicationContext.getBean(Shell.class);

        abstractSession = telegramSession;
        abstractSession.setActiveProgramExecutor(new ProgramExecutor(abstractSession.getProgramManager(), programCode, abstractSession));
        abstractSession.setActive(true);
        abstractSession.start();
    }

    public void input(String input){
        abstractSession.stdinPut(input);
    }
}
