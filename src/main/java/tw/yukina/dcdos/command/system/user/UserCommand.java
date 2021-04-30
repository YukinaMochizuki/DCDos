package tw.yukina.dcdos.command.system.user;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import tw.yukina.dcdos.command.AbstractAssistantCommand;
import tw.yukina.dcdos.constants.Role;
import tw.yukina.dcdos.repository.UserCacheRepository;
import tw.yukina.dcdos.repository.UserRepository;

@Component
@Command(name = "/user", subcommands = {CommandLine.HelpCommand.class, addUser.class, findUser.class,
        listUser.class, delUser.class})
@Getter
public class UserCommand extends AbstractAssistantCommand {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCacheRepository userCacheRepository;

    @Override
    public String getCommandName() {
        return "user";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.ADMIN};
    }
}
