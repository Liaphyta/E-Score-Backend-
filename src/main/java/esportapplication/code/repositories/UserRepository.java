package esportapplication.code.repositories;

import esportapplication.code.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User getByUsername(String username);
}
