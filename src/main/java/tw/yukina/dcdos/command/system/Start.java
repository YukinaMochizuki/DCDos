package tw.yukina.dcdos.command.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import tw.yukina.dcdos.command.AbstractAssistantCommand;
import tw.yukina.dcdos.constants.Role;
import tw.yukina.dcdos.manager.telegram.TelegramUserInfoManager;

@Component
@Command(name = "start", description = "Update your user info")
public class Start extends AbstractAssistantCommand implements Runnable{

    @Autowired
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    private TelegramUserInfoManager telegramUserInfoManager;

    @Override
    public void run() {
        sendMessageToChatId("Your telegram user id is " + getChatId());
        sendMessageToChatId("If you want to get some help, please send /help for me");
    }

    @Override
    public String getCommandName() {
        return "start";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.GUEST};
    }
}
