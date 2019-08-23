package esportapplication.code.models;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

public class Match {
    private String opponentOneName;
    private Integer opponentOneID;
    private Integer opponentOneScore;
    private String opponentTwoName;
    private Integer opponentTwoID;
    private Integer opponentTwoScore;
    private String winner;
    private LocalDateTime time;
    private String status;
    private String leagueName;

    public Match(String opponentOneName, Integer opponentOneID, Integer opponentOneScore, String opponentTwoName, Integer opponentTwoID, Integer opponentTwoScore, Timestamp time, String status, String leagueName) throws ParseException {
        this.opponentOneName = opponentOneName;
        this.opponentOneID = opponentOneID;
        this.opponentOneScore = opponentOneScore;
        this.opponentTwoName = opponentTwoName;
        this.opponentTwoID = opponentTwoID;
        this.opponentTwoScore = opponentTwoScore;
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s", Locale.ENGLISH);
        this.time = LocalDateTime.parse(time.toString(),inputFormatter);
//
        this.status = status;
        this.leagueName = leagueName;

        if(status.equals("Scheduled"))
            winner="Not yet started";
        else if(opponentOneScore>opponentTwoScore){
            winner=opponentOneName;
        }
        else
            winner=opponentTwoName;
    }

    public String getOpponentOneName() {
        return opponentOneName;
    }

    public void setOpponentOneName(String opponentOneName) {
        this.opponentOneName = opponentOneName;
    }

    public Integer getOpponentOneID() {
        return opponentOneID;
    }

    public void setOpponentOneID(Integer opponentOneID) {
        this.opponentOneID = opponentOneID;
    }

    public String getOpponentTwoName() {
        return opponentTwoName;
    }

    public void setOpponentTwoName(String opponentTwoName) {
        this.opponentTwoName = opponentTwoName;
    }

    public Integer getOpponentTwoID() {
        return opponentTwoID;
    }

    public void setOpponentTwoID(Integer opponentTwoID) {
        this.opponentTwoID = opponentTwoID;
    }

    public String getWinner() {
        String string=winner;
        if(opponentOneScore>opponentTwoScore)
        string+="("+opponentOneScore+":"+opponentTwoScore+")";
        else if(opponentTwoScore>opponentOneScore)
            string+="("+opponentTwoScore+":"+opponentOneScore+")";

        return string;

    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getTime() {
        String temp=time.getDayOfMonth()+" " + time.getMonth() + " " + time.getYear() + " ";
        if(time.getHour()>=10)
            temp+=time.getHour()+ "h:";
        else
            temp+="0"+time.getHour()+"h:";

        if(time.getMinute()>0)
           return temp+Integer.toString(time.getMinute());
        else
            return temp+Integer.toString(time.getMinute())+"0";
    }

    public void setTime(Timestamp time) throws ParseException {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s", Locale.ENGLISH);
        this.time = LocalDateTime.parse(time.toString(),inputFormatter);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeague(String leagueName) {
        this.leagueName = leagueName;
    }
    public String toString(){
        return opponentOneName + " VS "+ opponentTwoName + "\n" +"WINNER: "+ winner +" RESULT: " +opponentOneScore + ":"+opponentTwoScore+"\n";
    }
}

