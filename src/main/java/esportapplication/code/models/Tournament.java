package esportapplication.code.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Tournament {
    private String name;
    private String beginAt;
    private String prize;

    public Tournament(String name, String beginAt, String prize) {
        this.name = name;
        this.beginAt = beginAt;
        this.prize = prize;
        if(prize.equals(""))
            this.prize="TBA";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(String beginAt) {
        this.beginAt = beginAt;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    @Override
    public String toString() {
        LocalDate date;
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        date = LocalDate.parse(beginAt, inputFormatter);
        String string = name+ "\nBegin at: "+ date.getDayOfMonth() + " "+ date.getMonth() + " " + date.getYear() + "\nPrize pool: "+ prize;

        return string;
    }
}
