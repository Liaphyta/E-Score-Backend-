package esportapplication.code.controllers;

import esportapplication.code.models.Match;
import esportapplication.code.models.Tournament;
import esportapplication.code.services.BasicHTTPApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Match>> showUpcomingMatches() throws IOException {
      return new ResponseEntity<>(basicHTTPApiService.getMatches("upcoming","lol"), HttpStatus.OK);
    }
    @GetMapping("/previousMatches")
    public ResponseEntity<List<Match>> showPreviousMatches() throws IOException {
      return new ResponseEntity<>(basicHTTPApiService.getMatches("previous","lol"),HttpStatus.OK);
    }
    @GetMapping("/tournaments")
    public ResponseEntity<List<Tournament>> showTournaments(){

      return new ResponseEntity<>(basicHTTPApiService.getTournaments("lol"),HttpStatus.OK);
    }
    @GetMapping("/champions")
    public ResponseEntity<String> getChampions(){
      return new ResponseEntity<>(basicHTTPApiService.getChampions(),HttpStatus.OK);
  }
    @GetMapping("/champions/{id}")
    public ResponseEntity<String> getChampion(@PathVariable Long id){
      return new ResponseEntity<>(basicHTTPApiService.getChampion(id),HttpStatus.OK);
  }
    @GetMapping("/network")
    public ResponseEntity<List<Match>> network() throws IOException {
        return new ResponseEntity<>(basicHTTPApiService.initializeNetwork(),HttpStatus.OK);
    }
}
