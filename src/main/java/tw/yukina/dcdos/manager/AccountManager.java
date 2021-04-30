package tw.yukina.dcdos.manager;

import org.springframework.stereotype.Service;
import tw.yukina.dcdos.constants.Role;
import tw.yukina.dcdos.entity.account.User;
import tw.yukina.dcdos.repository.GroupRepository;
import tw.yukina.dcdos.repository.UserRepository;

import java.util.Set;

@Service
public class AccountManager {

    private UserRepository userRepository;
    private GroupRepository groupRepository;

    public AccountManager(UserRepository userRepository, GroupRepository groupRepository){
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public User getUserByTG(int telegramUserId){
        return userRepository.findByTelegramUserId(telegramUserId);
    }

    public User getUserByGitLab(int gitLabUserId){
        return userRepository.findByGitLabUserId(gitLabUserId);
    }

    public Set<User> getAllUser(Role role){
        return userRepository.findAllByRole(role);
    }
}
