package tw.yukina.dcdos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.yukina.dcdos.entity.account.User;
import tw.yukina.dcdos.entity.account.UserCache;

public interface UserCacheRepository extends JpaRepository<UserCache, Long> {
    UserCache findByUser(User user);
}
