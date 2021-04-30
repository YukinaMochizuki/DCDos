package tw.yukina.dcdos.manager.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tw.yukina.dcdos.command.AssistantCommand;
import tw.yukina.dcdos.constants.Role;
import tw.yukina.dcdos.entity.account.Group;
import tw.yukina.dcdos.entity.account.User;
import tw.yukina.dcdos.repository.GroupRepository;
import tw.yukina.dcdos.repository.UserRepository;

import java.util.Arrays;

@Component
public class TelegramPermissionManager {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Value("${telegram.permission.master}")
    private int adminUserId;

    public TelegramPermissionManager(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public boolean checkUser(int userId, AssistantCommand assistantCommand){
        User user = userRepository.findByTelegramUserId(userId);

        if(userId == adminUserId)return true;
        if(Arrays.asList(assistantCommand.getPermissions()).contains(Role.GUEST))return true;

        if(user == null)return false;
        else for(Role role: assistantCommand.getPermissions()) if(user.getRole() == role)return true;

        return false;
    }

    public boolean checkGroup(int groupId, AssistantCommand assistantCommand){
        Group group = groupRepository.findByChatId(groupId);

        if(group == null)return false;
        else for(Role role: assistantCommand.getPermissions()) if(role == Role.GROUP)return true;

        return false;
    }
}
