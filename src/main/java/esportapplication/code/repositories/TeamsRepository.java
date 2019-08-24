package esportapplication.code.repositories;

import esportapplication.code.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamsRepository extends JpaRepository<Team,Long> {

    List<Team> findAll();

}
