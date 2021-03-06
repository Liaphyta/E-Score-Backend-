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

import java.io.IOException;
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
    public List<Match> showUpcomingMatches() throws IOException {
        return basicHTTPApiService.getMatches1("upcoming","dota2");
    }
//    @GetMapping("/test")
//    public User getUsers(){
//        return userRepository.getByUsername("Alek");
//    }
    @GetMapping("/previousMatches")
    public List<Match> showPreviousMatches() throws IOException {
        return basicHTTPApiService.getMatches1("previous","dota2");
    }
    @GetMapping("/tournaments")
    public List<Tournament> showTournaments(){
        return basicHTTPApiService.getTournaments("dota2");
    }

}
