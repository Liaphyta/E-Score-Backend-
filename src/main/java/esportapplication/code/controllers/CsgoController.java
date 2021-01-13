package esportapplication.code.controllers;

import esportapplication.code.models.Match;
import esportapplication.code.models.Tournament;
import esportapplication.code.services.BasicHTTPApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/csgo")
@CrossOrigin(value="http://localhost:4200",maxAge = 36000)
public class CsgoController {
    @Autowired
    BasicHTTPApiService basicHTTPApiService;

    @GetMapping("/upcomingMatches")
    public List<Match> showUpcomingMatches() throws IOException {
        return basicHTTPApiService.getMatches1("upcoming","csgo");
    }
    @GetMapping("/previousMatches")
    public List<Match> showPreviousMatches() throws IOException {
        return basicHTTPApiService.getMatches1("previous","csgo");
    }
    @GetMapping("/tournaments")
    public List<Tournament> showTournaments(){
        return basicHTTPApiService.getTournaments("csgo");
    }
}
