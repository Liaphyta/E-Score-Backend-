package esportapplication.code.services;

import esportapplication.code.models.Team;
import esportapplication.code.repositories.TeamsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService{

    @Autowired
    TeamsRepository teamsRepository;


    @Override
    public List<Team> getTeams() {
        return this.teamsRepository.findAll();

    }
}
