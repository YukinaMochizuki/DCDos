package tw.yukina.dcdos.command.system.user;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tw.yukina.dcdos.command.AbstractSubCommand;
import tw.yukina.dcdos.entity.account.User;

@Command(name = "del", description = "Find users and delete it")
public class delUser extends AbstractSubCommand implements Runnable {

    @Parameters(index = "0", paramLabel = "<name>")
    private String name;

    @Override
    public void run() {
        User user = parentCommand.getUserRepository().findByName(name);
        if(user == null) parentCommand.sendMessageToChatId("Can not find any user named " + name);
        else {
            parentCommand.getUserRepository().delete(user);
            parentCommand.sendMessageToChatId("Delete user " + name);
        }
    }
}
