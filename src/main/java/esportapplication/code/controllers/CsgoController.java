package esportapplication.code.controllers;

import esportapplication.code.models.Match;
import esportapplication.code.models.Tournament;
import esportapplication.code.services.BasicHTTPApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/csgo")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:4200"}, maxAge = 36000)
public class CsgoController {
    @Autowired
    BasicHTTPApiService basicHTTPApiService;

    @GetMapping("/upcomingMatches")
    public ResponseEntity<List<Match>> showUpcomingMatches() throws IOException {
        return new ResponseEntity<>(basicHTTPApiService.getMatches("upcoming","csgo"), HttpStatus.OK);
    }
    @GetMapping("/previousMatches")
    public ResponseEntity<List<Match>> showPreviousMatches() throws IOException {
        return new ResponseEntity<>(basicHTTPApiService.getMatches("previous","csgo"),HttpStatus.OK);
    }
    @GetMapping("/tournaments")
    public ResponseEntity<List<Tournament>> showTournaments(){
        return new ResponseEntity<>(basicHTTPApiService.getTournaments("csgo"),HttpStatus.OK);
    }
}
