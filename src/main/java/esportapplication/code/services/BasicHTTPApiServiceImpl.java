package esportapplication.code.services;

import esportapplication.code.models.Match;
import esportapplication.code.models.Tournament;
import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

    public List<Match> getMatches(String whichOne,String game) throws IOException {
      List<Match> matches=new ArrayList<>();
      String urlToHit="";
      if(whichOne.equals("upcoming"))
      urlToHit= "https://api.pandascore.co/"+game+"/matches/upcoming?token=S3M8ypVclIOtl3Y1OvPKzrDLd_cGP8YxNt1IfqggtZEPo22JXy8";
      else
      urlToHit="https://api.pandascore.co/"+game+"/matches/past?token=S3M8ypVclIOtl3Y1OvPKzrDLd_cGP8YxNt1IfqggtZEPo22JXy8";
      URL url = new URL(urlToHit);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      JSONArray jsonArray = new JSONArray(rd.readLine());
      for(int i=0;i<jsonArray.length();i++){
        String name=jsonArray.getJSONObject(i).getString("slug");
        JSONArray opponents=jsonArray.getJSONObject(i).getJSONArray("opponents");
        try {
            if(opponents.length()>0)
            {


          String opponentOneName=opponents.getJSONObject(0).getJSONObject("opponent").getString("name");
            Integer opponentOneId=opponents.getJSONObject(0).getJSONObject("opponent").getInt("id");
            JSONArray results=jsonArray.getJSONObject(i).getJSONArray("results");
            String opponentTwoName="";
            Integer opponentTwoId=0;
            Integer opponentTwoScore=0;
          if(opponents.length()==2)
          {
             opponentTwoName=opponents.getJSONObject(1).getJSONObject("opponent").getString("name");
             opponentTwoId=opponents.getJSONObject(1).getJSONObject("opponent").getInt("id");
              opponentTwoScore=results.getJSONObject(1).getInt("score");
          }
          Integer opponentOneScore=results.getJSONObject(0).getInt("score");
          Date time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(jsonArray.getJSONObject(i).getString("original_scheduled_at"));
          String status=jsonArray.getJSONObject(i).getString("status");
          String leagueName=jsonArray.getJSONObject(i).getJSONObject("league").getString("name");
          if(!opponentTwoName.equals(""))
          matches.add(new Match(opponentOneName,opponentOneId,opponentOneScore,
                  opponentTwoName,opponentTwoId,opponentTwoScore,time,status,leagueName));

        }
        }catch (ParseException e) {
          e.printStackTrace();
        }
      }
      return matches;
    }

    @Override
    public String getChampions() {
        String urlPageOne= "https://api.pandascore.co/lol/champions?token=S3M8ypVclIOtl3Y1OvPKzrDLd_cGP8YxNt1IfqggtZEPo22JXy8&page[size]=100&page[number]=1";
        String urlPageTwo= "https://api.pandascore.co/lol/champions?token=S3M8ypVclIOtl3Y1OvPKzrDLd_cGP8YxNt1IfqggtZEPo22JXy8&page[size]=100&page[number]=2";
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
        String urlTo= "https://api.pandascore.co/lol/champions/"+id+"?token=S3M8ypVclIOtl3Y1OvPKzrDLd_cGP8YxNt1IfqggtZEPo22JXy8";
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

    public List<Tournament>  getTournaments(String game){
        List<Tournament> tournaments=new LinkedList<>();

        try {
            String urlHelp= "https://api.pandascore.co/"+game+"/tournaments/upcoming?token=S3M8ypVclIOtl3Y1OvPKzrDLd_cGP8YxNt1IfqggtZEPo22JXy8";
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
