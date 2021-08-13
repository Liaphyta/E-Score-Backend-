package esportapplication.code.controllers;

import esportapplication.code.models.Match;
import esportapplication.code.models.Tournament;
import esportapplication.code.services.BasicHTTPApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/lol")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:4200"}, maxAge = 36000)
public class LoLController {

    final
    BasicHTTPApiService basicHTTPApiService;

  public LoLController(BasicHTTPApiService basicHTTPApiService) {
    this.basicHTTPApiService = basicHTTPApiService;
  }

  @GetMapping("/upcomingMatches")
    public List<Match> showUpcomingMatches() throws IOException {
       return basicHTTPApiService.getMatches("upcoming","lol");
    }
    @GetMapping("/previousMatches")
    public List<Match> showPreviousMatches() throws IOException {
        return basicHTTPApiService.getMatches("previous","lol");

    }
    @GetMapping("/tournaments")
    public List<Tournament> showTournaments(){
        return basicHTTPApiService.getTournaments("lol");
    }
    @GetMapping("/champions")
    public String getChampions(){return basicHTTPApiService.getChampions();}
    @GetMapping("/champions/{id}")
    public String getChampion(@PathVariable Long id){return basicHTTPApiService.getChampion(id);}
}
