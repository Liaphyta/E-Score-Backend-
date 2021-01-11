package esportapplication.code.services;

import esportapplication.code.models.Match;
import esportapplication.code.models.Tournament;

import java.io.IOException;
import java.util.List;

public interface BasicHTTPApiService {

    public List<Match> getMatches(String whichOne,String game);
    public List<Tournament> getTournaments(String game);
    public List<Match> getMatches1(String whichOne,String game) throws IOException;
}
