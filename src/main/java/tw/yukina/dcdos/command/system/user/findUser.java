package tw.yukina.dcdos.command.system.user;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tw.yukina.dcdos.command.AbstractSubCommand;
import tw.yukina.dcdos.entity.account.User;
import tw.yukina.dcdos.entity.account.UserCache;

@Command(name = "find", description = "Find users and display user info")
public class findUser extends AbstractSubCommand {

    @Command(name = "name", description = "Display name")
    private void findByName(@Parameters(index = "0", paramLabel = "<name>") String name) {
        User user = parentCommand.getUserRepository().findByName(name);
        if(user == null) parentCommand.sendMessageToChatId("Can not find any user named " + name);
        else displayUserInfo(user);
    }

    @Command(name = "TGid", description = "Telegram user id")
    private void findByTgId(@Parameters(index = "0", paramLabel = "<id>") int id) {
        if(id == 0){
            parentCommand.sendMessageToChatId("Invalid value : parameter cannot be 0");
            return;
        }

        User user = parentCommand.getUserRepository().findByTelegramUserId(id);
        if(user == null) parentCommand.sendMessageToChatId("Can not find any user id is " + id);
        else displayUserInfo(user);
    }

    @Command(name = "GLid", description = "GitLab user id")
    private void findByGitLabId(@Parameters(index = "0", paramLabel = "<id>") int id) {
        if(id == 0){
            parentCommand.sendMessageToChatId("Invalid value : parameter cannot be 0");
            return;
        }

        User user = parentCommand.getUserRepository().findByGitLabUserId(id);
        if(user == null) parentCommand.sendMessageToChatId("Can not find any user GitLab id is " + id);
        else displayUserInfo(user);
    }

    private void displayUserInfo(User user){
        parentCommand.sendMessageToChatId("User Setting is " + user.toString());

        UserCache userCache = parentCommand.getUserCacheRepository().findByUser(user);
        if(userCache != null) parentCommand.sendMessageToChatId("User Profile is " +
                parentCommand.getUserCacheRepository().findByUser(user).toString());
        else parentCommand.sendMessageToChatId("And user profile has not been initialized");
    }
}
