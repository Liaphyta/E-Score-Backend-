package esportapplication.code.repositories;

import esportapplication.code.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByName(String name);
}
