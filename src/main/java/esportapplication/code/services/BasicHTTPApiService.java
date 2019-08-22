package esportapplication.code.services;

import esportapplication.code.models.Match;
import esportapplication.code.models.Tournament;

import java.util.List;

public interface BasicHTTPApiService {

    public List<Match> getMatches(String whichOne,String game);
    public List<Tournament> getTournaments(String game);
}
