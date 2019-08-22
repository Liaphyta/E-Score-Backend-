package esportapplication.code.services;

import esportapplication.code.models.Match;
import esportapplication.code.models.Tournament;
import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class BasicHTTPApiServiceImpl implements BasicHTTPApiService {
    public List<Match> getMatches(String whichOne,String game) {
        Long from;
        Long to;
        Date date = new Date();
        Calendar cal = Calendar.getInstance();

        if(whichOne.equals("upcoming"))
        {
            from=date.getTime();
            cal.add(Calendar.DATE,10);
            to=cal.getTimeInMillis();
        }
        else
        {
            cal.add(Calendar.DATE,-10);
            from=cal.getTimeInMillis();
            to=date.getTime();
        }
        StringBuilder result = new StringBuilder();
        List<Match> matches=new LinkedList<Match>();
        try {
            String urlHelp = "https://api.gamescorekeeper.com/v1/fixtures?sport="+game+"&from=" + from + "&to=" + to;
            URL url = new URL(urlHelp);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbGVrc2FuZGFyc3RvamtvdjI1NSIsImlzcyI6IkdhbWVTY29yZWtlZXBlciIsImp0aSI6LTM0NjQ0NDQyNTM1NzU2NzIxMDQsImN1c3RvbWVyIjp0cnVlfQ.sOl2abVWQTFgW--tNr1j3UGUab11NUfKIOAtvPLdv2w");
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            JSONObject json=new JSONObject(rd.readLine());
            JSONArray jsonFixtures = json.getJSONArray("fixtures");
            for(int i =0;i<jsonFixtures.length();i++){
                String opponentOneName,opponentTwoName;
                Integer opponentOneID,opponentOneScore,opponentTwoID,opponentTwoScore;
                JSONObject temp=(JSONObject)jsonFixtures.get(i);
                JSONObject competition = temp.getJSONObject("competition");
                String competitionName=competition.getString("name");
                String statusMatch=temp.getString("status");
                Long startTime=(Long)temp.get("scheduledStartTime");
                JSONArray participants = temp.getJSONArray("participants");
                JSONObject opponentOne=(JSONObject)participants.get(0);
                JSONObject opponentTwo=(JSONObject)participants.get(1);
                if(!(opponentOne.isNull("name")|| opponentTwo.isNull("name"))){
                    opponentOneName = opponentOne.getString("name");
                    opponentOneID = (Integer) opponentOne.get("id");
                    opponentOneScore = (Integer) opponentOne.get("score");
                    opponentTwoName = opponentTwo.getString("name");
                    opponentTwoID = (Integer) opponentTwo.get("id");
                    opponentTwoScore = (Integer) opponentTwo.get("score");
                    Match match=new Match(opponentOneName,opponentOneID,opponentOneScore,opponentTwoName,opponentTwoID,opponentTwoScore,new Timestamp(startTime),statusMatch,competitionName);
                    matches.add(match);
                }
            }
            rd.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return matches;
    }


    public List<Tournament>  getTournaments(String game){
        List<Tournament> tournaments=new LinkedList<>();

        try {
            String urlHelp= "https://api.pandascore.co//"+game+"/tournaments/upcoming?token=S3M8ypVclIOtl3Y1OvPKzrDLd_cGP8YxNt1IfqggtZEPo22JXy8";
            URL url = new URL(urlHelp);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONArray jsonArray = new JSONArray(rd.readLine());
            for(int i=0;i<jsonArray.length();i++){
                String name=jsonArray.getJSONObject(i).getString("slug");
                name=name.replace("-"," ");
                name=name.replace("playoffs","");
                name=WordUtils.capitalizeFully(name);
               String beginAt=jsonArray.getJSONObject(i).getString("begin_at");
               String prizePool=jsonArray.getJSONObject(i).optString("prizepool");
               tournaments.add(new Tournament(name,beginAt,prizePool));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tournaments;
    }
}