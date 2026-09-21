package ma.youcode.lineperma.Model;
import java.time.LocalDate;
import java.time.LocalTime;

public class Logs {
    private LocalDate  date;
    private LocalTime time;
    private String user;
    private String action;
    private String file;
    private String result;
    public Logs(LocalDate date, LocalTime time, String user, String action, String file, String result) {
        this.date = date;
        this.time = time;
        this.user = user;
        this.action = action;
        this.file = file;
        this.result = result;
    }
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getTime() {
        return time;
    }
    public String getUser() {
        return user;
    }
    public String getAction() {
        return action;
    }
    public String getFile() {
        return file;
    }
    public String getResult() {
        return result;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public void setFile(String file) {
        this.file = file;
    }
    public void setResult(String result) {
        this.result = result;
    }


}
