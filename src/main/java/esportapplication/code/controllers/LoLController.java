package esportapplication.code.controllers;

import esportapplication.code.models.*;
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
    @GetMapping("/network/{league}")
    public ResponseEntity<List<Head2Head>> getFixtures(@PathVariable String league){
    return new ResponseEntity<>(basicHTTPApiService.findAllHead2HeadsBasedOnLeagueName(league),HttpStatus.OK);
    }
  @GetMapping("/network/{league}/edges")
  public ResponseEntity<List<Edge>> getEdges(@PathVariable String league){
    return new ResponseEntity<>(basicHTTPApiService.getAllEdgesForLeague(league),HttpStatus.OK);
  }
  @GetMapping("/network/{league}/nodes")
  public ResponseEntity<List<Node>> getNodes(@PathVariable String league){
    return new ResponseEntity<>(basicHTTPApiService.getAllNodesForLeague(league),HttpStatus.OK);
  }
  @GetMapping("/network/{league}/graph")
  public ResponseEntity<Graph> getGraph(@PathVariable String league){
    return new ResponseEntity<>(basicHTTPApiService.getGraph(league),HttpStatus.OK);
  }
  @GetMapping("/network/{league}/teamz")
  public ResponseEntity<List<String>> getTeams(@PathVariable String league)
  {
    return new ResponseEntity<>(basicHTTPApiService.getTeams(league),HttpStatus.OK);
  }
  @GetMapping("/network/{league}/{team}")
  public ResponseEntity<Graph> getGraphBasedOnLeagueAndTeam(@PathVariable String league,@PathVariable String team)
  {
    return new ResponseEntity<>(basicHTTPApiService.getGraphBasedOnLeagueAndTeam(league,team),HttpStatus.OK);
  }
}
