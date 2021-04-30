package tw.yukina.dcdos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.yukina.dcdos.constants.Role;
import tw.yukina.dcdos.entity.account.User;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
    User findByGitLabUserId(int username);
    User findByTelegramUserId(int username);
    Set<User> findAllByRole(Role role);
}