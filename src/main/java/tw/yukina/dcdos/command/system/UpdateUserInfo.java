package tw.yukina.dcdos.command.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import tw.yukina.dcdos.command.AbstractAssistantCommand;
import tw.yukina.dcdos.constants.Role;
import tw.yukina.dcdos.entity.account.User;
import tw.yukina.dcdos.manager.telegram.TelegramUserInfoManager;
import tw.yukina.dcdos.repository.UserCacheRepository;
import tw.yukina.dcdos.repository.UserRepository;

@Component
@Command(name = "/updateUserInfo", description = "Get your telegram user id")
public class UpdateUserInfo extends AbstractAssistantCommand implements Runnable{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCacheRepository userCacheRepository;

    @Autowired
    private TelegramUserInfoManager telegramUserInfoManager;

    @Override
    public void run() {
        User user = userRepository.findByTelegramUserId(getChatId());
        if(user != null) {
            sendMessageToChatId("Updating your user info");
            telegramUserInfoManager.updateCache(user);
            sendMessageToChatId("Your user info is " + userCacheRepository.findByUser(user).toString());
        }
    }

    @Override
    public String getCommandName() {
        return "updateUserInfo";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.GUEST};
    }
}
