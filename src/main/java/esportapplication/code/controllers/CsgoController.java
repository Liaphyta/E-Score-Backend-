package esportapplication.code.controllers;

import esportapplication.code.models.Match;
import esportapplication.code.models.Tournament;
import esportapplication.code.services.BasicHTTPApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/csgo")
@CrossOrigin("http://localhost:4200")
public class CsgoController {
    @Autowired
    BasicHTTPApiService basicHTTPApiService;

    @GetMapping("/upcomingMatches")
    public List<Match> showUpcomingMatches(){
        List<Match> matches=basicHTTPApiService.getMatches("upcoming","csgo");
        return matches;
    }
    @GetMapping("/previousMatches")
    public List<Match> showPreviousMatches(){
        List<Match> matches=basicHTTPApiService.getMatches("previous","csgo");
        return matches;
    }
    @GetMapping("/tournaments")
    public List<Tournament> showTournaments(){
        List<Tournament> tournaments=basicHTTPApiService.getTournaments("csgo");
        return tournaments;
    }
}
