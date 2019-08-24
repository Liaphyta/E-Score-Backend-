package esportapplication.code.controllers;

import esportapplication.code.models.Match;
import esportapplication.code.models.Tournament;
import esportapplication.code.models.User;
import esportapplication.code.repositories.UserRepository;
import esportapplication.code.services.BasicHTTPApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dota2")
@CrossOrigin(value="http://localhost:4200", maxAge = 36000)
public class Dota2Controller {

    @Autowired
    BasicHTTPApiService basicHTTPApiService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/upcomingMatches")
    public List<Match> showUpcomingMatches(){
        List<Match> matches=basicHTTPApiService.getMatches("upcoming","dota2");
        return matches;
    }
//    @GetMapping("/test")
//    public User getUsers(){
//        return userRepository.getByUsername("Alek");
//    }
    @GetMapping("/previousMatches")
    public List<Match> showPreviousMatches(){
        List<Match> matches=basicHTTPApiService.getMatches("previous","dota2");
        return matches;
    }
    @GetMapping("/tournaments")
    public List<Tournament> showTournaments(){
        List<Tournament> tournaments=basicHTTPApiService.getTournaments("dota2");
        return tournaments;
    }

}
