package tw.yukina.dcdos.command.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import tw.yukina.dcdos.command.AbstractAssistantCommand;
import tw.yukina.dcdos.constants.Role;
import tw.yukina.dcdos.manager.SessionManager;

@Component
@Command(name = "session")
public class SessionCommand extends AbstractAssistantCommand implements Runnable{

    @Autowired
    private SessionManager sessionManager;

    @Override
    public String getCommandName() {
        return "session";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.ADMIN};
    }

    @Override
    public void run() {
        sessionManager.newSession();
    }
}
