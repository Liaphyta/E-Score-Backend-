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
@RequestMapping("/lol")
@CrossOrigin(value = "http://localhost:4200", maxAge = 36000)
public class LoLController {

    final
    BasicHTTPApiService basicHTTPApiService;

  public LoLController(BasicHTTPApiService basicHTTPApiService) {
    this.basicHTTPApiService = basicHTTPApiService;
  }

  @GetMapping("/upcomingMatches")
    public List<Match> showUpcomingMatches(){
       List<Match> matches=basicHTTPApiService.getMatches("upcoming","lol");
       return matches;
    }
    @GetMapping("/previousMatches")
    public List<Match> showPreviousMatches() throws IOException {
        List<Match> matches=basicHTTPApiService.getMatches1("previous","lol");
        return matches;
    }
    @GetMapping("/tournaments")
    public List<Tournament> showTournaments(){
        List<Tournament> tournaments=basicHTTPApiService.getTournaments("lol");
        return tournaments;
    }
}
