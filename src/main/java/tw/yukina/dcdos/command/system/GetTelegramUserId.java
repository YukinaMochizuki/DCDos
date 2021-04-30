package tw.yukina.dcdos.command.system;

import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import tw.yukina.dcdos.command.AbstractAssistantCommand;
import tw.yukina.dcdos.constants.Role;

@Component
@Command(name = "/getTelegramUserId", description = "Get your telegram user id")
public class GetTelegramUserId extends AbstractAssistantCommand implements Runnable {

    @Override
    public String getCommandName() {
        return "getTelegramUserId";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.GUEST};
    }

    @Override
    public void run() {
        sendMessageToChatId("Your telegram user id is " + getChatId());
    }
}