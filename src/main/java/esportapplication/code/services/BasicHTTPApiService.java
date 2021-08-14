package esportapplication.code.services;

import esportapplication.code.models.Match;
import esportapplication.code.models.Tournament;

import java.io.IOException;
import java.util.List;

public interface BasicHTTPApiService {

    public List<Tournament> getTournaments(String game);
    public List<Match> getMatches(String whichOne,String game) throws IOException;

    public List<Match> initializeNetwork() throws IOException;

    String getChampions();
    void writeHistory(List<Match> matches);
    String getChampion(Long id);
}
