package esportapplication.code.controllers;

import esportapplication.code.models.Match;
import esportapplication.code.models.Team;
import esportapplication.code.models.Tournament;
import esportapplication.code.services.BasicHTTPApiService;
import esportapplication.code.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teams")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:4200"}, maxAge = 36000)
public class BasicController {

    @Autowired
    private TeamService teamService;

    @GetMapping()
    public ResponseEntity<List<Team>> getAllTeams(){
        List<Team> teams=teamService.getTeams();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }


}