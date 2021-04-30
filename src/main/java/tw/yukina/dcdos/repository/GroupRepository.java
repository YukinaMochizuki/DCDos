package tw.yukina.dcdos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.yukina.dcdos.entity.account.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByChatId(int id);
}
