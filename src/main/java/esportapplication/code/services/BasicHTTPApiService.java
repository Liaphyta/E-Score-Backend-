package esportapplication.code.services;

import esportapplication.code.models.*;

import java.io.IOException;
import java.util.List;

public interface BasicHTTPApiService {

    public List<Tournament> getTournaments(String game);
    public List<Match> getMatches(String whichOne,String game) throws IOException;

    public List<Match> initializeNetwork() throws IOException;

    String getChampions();
    void writeHistory(List<Match> matches);
    String getChampion(Long id);
    List<Head2Head> findAllHead2HeadsBasedOnLeagueName(String leagueName);
    List<Edge> getAllEdgesForLeague(String leagueName);
    List<Node> getAllNodesForLeague(String leagueName);
    Graph getGraph(String leagueName);

    List<String> getTeams(String leagueName);

    Graph getGraphBasedOnLeagueAndTeam(String leagueName, String team);
}
