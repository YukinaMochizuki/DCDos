package tw.yukina.dcdos.command.system.user;

import picocli.CommandLine.Command;
import tw.yukina.dcdos.command.AbstractSubCommand;
import tw.yukina.dcdos.entity.account.User;

import java.util.List;

@Command(name = "list", description = "List all users")
public class listUser extends AbstractSubCommand implements Runnable{
    @Override
    public void run() {
        List<User> users = parentCommand.getUserRepository().findAll();
        if(users.isEmpty()) parentCommand.sendMessageToChatId("Can not find any users in database");
        else users.forEach(user -> parentCommand.sendMessageToChatId(user.getName()));
    }
}
