import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CursantConspectModel {

    private String theme;
    private String rank;
    private String pib;
    private String date;
    private String pidpysDate;
    //06.04.18
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

    public String getPidpysDate() {
        return pidpysDate;
    }

    @Override
    public String toString() {
        return "CursantConspectModel{" +
                "theme='" + theme + '\'' +
                ", rank='" + rank + '\'' +
                ", pib='" + pib + '\'' +
                ", date='" + date + '\'' +
                ", pidpysDate='" + pidpysDate + '\'' +
                '}';
    }

    public CursantConspectModel(String theme, String rank, String pib, String date) throws ParseException {
        this.theme = theme;
        this.rank = rank;
        System.out.println(pib);
        this.pib = pib.split(" ")[1].substring(0,1).toUpperCase()+"."+pib.split(" ")[2].substring(0,1).toUpperCase()+". " +pib.split(" ")[0];
        this.date = date;
       Calendar calendar = new GregorianCalendar();
       calendar.setTime(dateFormat.parse(date));
       calendar.roll(Calendar.DAY_OF_MONTH,-1);
       calendar.get(Calendar.DAY_OF_MONTH);
        this.pidpysDate = dateFormat.format(calendar.getTime());

    }

    public String getTheme() {

        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
