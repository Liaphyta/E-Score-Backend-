package esportapplication.code.repositories;

import esportapplication.code.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User getByUsername(String username);
    Page<User> findByGroupsId(Long groupId, Pageable pageable);

    @Query(value =
            "SELECT * FROM APP_PRIVILEGES p WHERE p.ID in (" +
                    "SELECT DISTINCT(pm.PRIVILEGE_ID) FROM GROUPS_PRIVILEGES pm where pm.GROUP_ID in ("+
                    "        SELECT mem.GROUP_ID FROM USERS_GROUPS mem"+
                    "        WHERE mem.USER_ID = :userId))", nativeQuery = true)
    List<Object[]> getPrivilegesByUser(@Param("userId") Long userId);

}
