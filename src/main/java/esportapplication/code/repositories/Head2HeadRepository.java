package esportapplication.code.repositories;

import esportapplication.code.models.Head2Head;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface Head2HeadRepository extends JpaRepository<Head2Head,Long> {

    List<Head2Head> findAllByFirstOpponent(String firstOpponent);
    Head2Head findByFirstOpponentAndSecondOpponent(String firstOpponent, String secondOpponent);

    List<Head2Head> findAllByLeagueName(String leagueName);
}
