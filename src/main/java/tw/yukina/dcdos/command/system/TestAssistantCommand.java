package tw.yukina.dcdos.command.system;

import org.springframework.stereotype.Component;
import tw.yukina.dcdos.command.AbstractAssistantCommand;
import tw.yukina.dcdos.constants.Role;

@Component
public class TestAssistantCommand extends AbstractAssistantCommand implements Runnable{
    @Override
    public String getCommandName() {
        return "test";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.ADMIN};
    }

    @Override
    public void run() {
        sendMessageToChatId("TestMessage");
    }
}
