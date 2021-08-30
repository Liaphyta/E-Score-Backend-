package esportapplication.code.services;

import esportapplication.code.models.*;
import esportapplication.code.repositories.Head2HeadRepository;
import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BasicHTTPApiServiceImpl implements BasicHTTPApiService {

    @Autowired
    Head2HeadRepository head2HeadRepository;

    public List<Match> getMatches(String whichOne, String game) throws IOException {
        List<Match> matches = new ArrayList<>();
        String urlToHit = "";
        if (whichOne.equals("upcoming"))
            urlToHit = "https://api.pandascore.co/" + game + "/matches/upcoming?token=S3M8ypVclIOtl3Y1OvPKzrDLd_cGP8YxNt1IfqggtZEPo22JXy8";
        else
            urlToHit = "https://api.pandascore.co/" + game + "/matches/past?token=S3M8ypVclIOtl3Y1OvPKzrDLd_cGP8YxNt1IfqggtZEPo22JXy8";
        URL url = new URL(urlToHit);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JSONArray jsonArray = new JSONArray(rd.readLine());
        for (int i = 0; i < jsonArray.length(); i++) {
            String name = jsonArray.getJSONObject(i).getString("slug");
            JSONArray opponents = jsonArray.getJSONObject(i).getJSONArray("opponents");
            try {
                if (opponents.length() > 0) {


                    String opponentOneName = opponents.getJSONObject(0).getJSONObject("opponent").getString("name");
                    Integer opponentOneId = opponents.getJSONObject(0).getJSONObject("opponent").getInt("id");
                    JSONArray results = jsonArray.getJSONObject(i).getJSONArray("results");
                    String opponentTwoName = "";
                    Integer opponentTwoId = 0;
                    Integer opponentTwoScore = 0;
                    if (opponents.length() == 2) {
                        opponentTwoName = opponents.getJSONObject(1).getJSONObject("opponent").getString("name");
                        opponentTwoId = opponents.getJSONObject(1).getJSONObject("opponent").getInt("id");
                        opponentTwoScore = results.getJSONObject(1).getInt("score");
                    }
                    Integer opponentOneScore = results.getJSONObject(0).getInt("score");
                    Date time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(jsonArray.getJSONObject(i).getString("original_scheduled_at"));
                    String status = jsonArray.getJSONObject(i).getString("status");
                    String leagueName = jsonArray.getJSONObject(i).getJSONObject("league").getString("name");
                    if (!opponentTwoName.equals(""))
                        matches.add(new Match(opponentOneName, opponentOneId, opponentOneScore,
                                opponentTwoName, opponentTwoId, opponentTwoScore, time, status, leagueName));

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return matches;
    }

    @Override
    public List<Match> initializeNetwork() throws IOException {
        List<Match> matches = new ArrayList<>();
        String urlToHit = "https://api.pandascore.co/lol/matches/past?token=S3M8ypVclIOtl3Y1OvPKzrDLd_cGP8YxNt1IfqggtZEPo22JXy8&per_page=100";
        for (int k = 0; k <= 127; k++)  // until ~2018
        {
            System.out.println(k);
            urlToHit += "&page=" + k;
            URL url = new URL(urlToHit);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONArray jsonArray = new JSONArray(rd.readLine());
            for (int i = 0; i < jsonArray.length(); i++) {
                String name = jsonArray.getJSONObject(i).getString("slug");
                JSONArray opponents = jsonArray.getJSONObject(i).getJSONArray("opponents");
                String leagueName = jsonArray.getJSONObject(i).getJSONObject("league").getString("name");
                if (leagueName.equals("LCK") || leagueName.equals("LCS") || leagueName.equals("LEC") || leagueName.equals("LPL") || leagueName.equals("PCS") ||
                        leagueName.equals("CBLOL") || leagueName.equals("LCL") || leagueName.equals("LJL") || leagueName.equals("LLA") || leagueName.equals("TCL")) {
                    try {
                        if (opponents.length() > 0) {
                            String opponentOneName = opponents.getJSONObject(0).getJSONObject("opponent").getString("name");
                            Integer opponentOneId = opponents.getJSONObject(0).getJSONObject("opponent").getInt("id");
                            JSONArray results = jsonArray.getJSONObject(i).getJSONArray("results");
                            String opponentTwoName = "";
                            Integer opponentTwoId = 0;
                            Integer opponentTwoScore = 0;
                            if (opponents.length() == 2) {
                                opponentTwoName = opponents.getJSONObject(1).getJSONObject("opponent").getString("name");
                                opponentTwoId = opponents.getJSONObject(1).getJSONObject("opponent").getInt("id");
                                opponentTwoScore = results.getJSONObject(1).getInt("score");
                            }
                            Integer opponentOneScore = results.getJSONObject(0).getInt("score");
                            Date time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(jsonArray.getJSONObject(i).getString("original_scheduled_at"));
                            String status = jsonArray.getJSONObject(i).getString("status");

                            if (!opponentTwoName.equals(""))
                                matches.add(new Match(opponentOneName, opponentOneId, opponentOneScore,
                                        opponentTwoName, opponentTwoId, opponentTwoScore, time, status, leagueName));

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        writeHistory(matches);
        return matches;
    }
    public List<Head2Head> findAllHead2HeadsBasedOnLeagueName(String leagueName){
      List<Head2Head> fixtures;
      fixtures=head2HeadRepository.findAllByLeagueName(leagueName);
      return fixtures;
    }

  @Override
  public List<Edge> getAllEdgesForLeague(String leagueName) {
    List<Edge> edges = new ArrayList<>();
    List<Head2Head> head2Heads = head2HeadRepository.findAllByLeagueName(leagueName);
    for(Head2Head head2Head : head2Heads)
    {
      Edge edge = new Edge();
      edge.setFrom(head2Head.getFirstOpponent());
      edge.setTo(head2Head.getSecondOpponent());
      edge.setLabel(head2Head.getScoreFirst()+"-"+head2Head.getScoreSecond());
      edges.add(edge);
    }
    System.out.println("VLAGA");
    return edges;
  }

  @Override
  public List<Node> getAllNodesForLeague(String leagueName) {
    List<Node> nodes = new ArrayList<>();
    List<Head2Head> head2Heads = head2HeadRepository.findAllByLeagueName(leagueName);
    Set<String> names = new HashSet<>();
    for(Head2Head head2Head : head2Heads)
    {
      names.add(head2Head.getFirstOpponent());
      names.add(head2Head.getSecondOpponent());
//      Node node = new Node();
//      node.setId(head2Head.getFirstOpponent());
//      node.setLabel(head2Head.getFirstOpponent());
//      node.setColor("#ffc107");
//      nodes.add(node);
    }
    Iterator<String> itr = names.iterator();
    while(itr.hasNext())
    {
      Node node=new Node();
      String name=itr.next();
      node.setId(name);
      node.setLabel(name);
      node.setColor("#ffc107");
      nodes.add(node);
    }
    return nodes;
  }

  @Override
  public Graph getGraph(String leagueName) {
    Graph graph = new Graph();
    graph.setEdges(getAllEdgesForLeague(leagueName));
    graph.setNodes(getAllNodesForLeague(leagueName));
    return graph;
  }

  @Override
  public List<String> getTeams(String leagueName) {
    List<Head2Head> head2Heads = head2HeadRepository.findAllByLeagueName(leagueName);
    Set<String> names = new HashSet<>();
    for(Head2Head head2Head : head2Heads) {
      names.add(head2Head.getFirstOpponent());
      names.add(head2Head.getSecondOpponent());
    }
    ArrayList<String> output=new ArrayList<>(names);
    return output;
  }

  @Override
  public Graph getGraphBasedOnLeagueAndTeam(String leagueName, String team) {
    Graph graph = new Graph();
    graph.setNodes(getAllNodesForLeague(leagueName));
    List<Head2Head> head2Heads = head2HeadRepository.findAllByLeagueName(leagueName);
    List<Edge> edges = new ArrayList<>();
    for(Head2Head head2Head : head2Heads)
    {
      if(head2Head.getFirstOpponent().contains(team))
      {
        Edge edge = new Edge();
        edge.setFrom(team);
        edge.setTo(head2Head.getSecondOpponent());
        edge.setLabel(head2Head.getScoreFirst()+"-"+head2Head.getScoreSecond());
        edges.add(edge);
      }
      else if(head2Head.getSecondOpponent().contains(team))
      {
        Edge edge = new Edge();
        edge.setFrom(team);
        edge.setTo(head2Head.getFirstOpponent());
        edge.setLabel(head2Head.getScoreSecond()+"-"+head2Head.getScoreFirst());
        edges.add(edge);
      }
    }
    graph.setEdges(edges);
    return graph;
  }

  public void writeHistory(List<Match> matches) {
        for (Match match : matches) {
            Head2Head head2Head =head2HeadRepository.findByFirstOpponentAndSecondOpponent(match.getOpponentOneName(),match.getOpponentTwoName());
            if(head2Head==null)
            {
                head2Head=head2HeadRepository.findByFirstOpponentAndSecondOpponent(match.getOpponentTwoName(),match.getOpponentOneName());
                if(head2Head==null)
                {
                    Head2Head fixture  = new Head2Head();
                    fixture.setFirstOpponent(match.getOpponentOneName());
                    fixture.setSecondOpponent(match.getOpponentTwoName());
                    fixture.setLeagueName(match.getLeagueName());
                    if (match.getOpponentOneName().contains(match.getWinner())) {
                        fixture.setScoreFirst(1);
                        fixture.setScoreSecond(0);
                        System.out.println("VLAGA - "+match.getWinner()+"_"+match.getOpponentOneName()+"_"+match.getOpponentTwoName());
                    }
                    else
                    {
                        fixture.setScoreFirst(0);
                        fixture.setScoreSecond(1);
                    }
                    head2HeadRepository.save(fixture);
                }
                else
                {
                    if(match.getWinner().contains(match.getOpponentTwoName()))
                        head2Head.setScoreFirst(head2Head.getScoreFirst()+1);
                    else
                        head2Head.setScoreSecond(head2Head.getScoreSecond()+1);
                    head2HeadRepository.save(head2Head);

                }
            }
            else
            {
                if(match.getWinner().contains(match.getOpponentOneName()))
                    head2Head.setScoreFirst(head2Head.getScoreFirst()+1);
                else
                    head2Head.setScoreSecond(head2Head.getScoreSecond()+1);
                head2HeadRepository.save(head2Head);
            }
        }
    }

    @Override
    public String getChampions() {
        String urlPageOne = "https://api.pandascore.co/lol/champions?token=S3M8ypVclIOtl3Y1OvPKzrDLd_cGP8YxNt1IfqggtZEPo22JXy8&page[size]=100&page[number]=1";
        String urlPageTwo = "https://api.pandascore.co/lol/champions?token=S3M8ypVclIOtl3Y1OvPKzrDLd_cGP8YxNt1IfqggtZEPo22JXy8&page[size]=100&page[number]=2";
        JSONArray sortedJsonArray = new JSONArray();
        try {
            URL url = new URL(urlPageOne);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONArray jsonArray = new JSONArray(rd.readLine());
            URL url1 = new URL(urlPageTwo);
            HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
            conn1.setRequestMethod("GET");
            BufferedReader rd1 = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
            JSONArray jsonArray1 = new JSONArray(rd1.readLine());
            for (int i = 0; i < jsonArray1.length(); i++) {
                jsonArray.put(jsonArray1.getJSONObject(i));
            }
            List<JSONObject> jsonValues = new ArrayList<JSONObject>();
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonValues.add(jsonArray.getJSONObject(i));
            }
            jsonValues.sort(new Comparator<JSONObject>() {
                private static final String KEY_NAME = "name";

                @Override
                public int compare(JSONObject a, JSONObject b) {
                    String valA = "";
                    String valB = "";

                    try {
                        valA = (String) a.get(KEY_NAME);
                        valB = (String) b.get(KEY_NAME);
                    } catch (JSONException e) {
                        //do something
                    }

                    return valA.compareTo(valB);
                }
            });
            for (int i = 0; i < jsonArray.length(); i++) {
                sortedJsonArray.put(jsonValues.get(i));
            }
            return sortedJsonArray.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "CALL NOT OKAY!";
    }

    @Override
    public String getChampion(Long id) {
        String urlTo = "https://api.pandascore.co/lol/champions/" + id + "?token=S3M8ypVclIOtl3Y1OvPKzrDLd_cGP8YxNt1IfqggtZEPo22JXy8";
        try {
            URL url = new URL(urlTo);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject jsonObject = new JSONObject(rd.readLine());
            return jsonObject.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "CALL NOT OKAY!";
    }

    public List<Tournament> getTournaments(String game) {
        List<Tournament> tournaments = new LinkedList<>();

        try {
            String urlHelp = "https://api.pandascore.co/" + game + "/tournaments/upcoming?token=S3M8ypVclIOtl3Y1OvPKzrDLd_cGP8YxNt1IfqggtZEPo22JXy8";
            URL url = new URL(urlHelp);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONArray jsonArray = new JSONArray(rd.readLine());
            for (int i = 0; i < jsonArray.length(); i++) {
                String name = jsonArray.getJSONObject(i).getString("slug");
                name = name.replace("-", " ");
                name = name.replace("playoffs", "");
                name = WordUtils.capitalizeFully(name);
                String beginAt = jsonArray.getJSONObject(i).getString("begin_at");
                String prizePool = jsonArray.getJSONObject(i).optString("prizepool");
                tournaments.add(new Tournament(name, beginAt, prizePool));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tournaments;
    }
}
