package ma.youcode.lineperma.Model;
import java.time.LocalDate;
import java.time.LocalTime;

public class Logs {
    private int id;
    private LocalDate  date;
    private LocalTime time;
    private String username;
    private int userId;
    private String action;
    private String filename;
    private int file_id;
    private String result;

    public Logs(int id, LocalDate date, LocalTime time, String username, String action, String filename, String result) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.username = username;
        this.action = action;
        this.filename = filename;
        this.result = result;
    }
    public Logs(int id, LocalDate date, LocalTime time,  int userId, String action, int file_id, String result) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.userId = userId;
        this.action = action;
        this.file_id = file_id;
        this.result = result;
    }
    public Logs(LocalDate date, LocalTime time,  int userId, String action, int file_id, String result) {
        this.date = date;
        this.time = time;
        this.userId = userId;
        this.action = action;
        this.file_id = file_id;
        this.result = result;
    }

    public int getUserId() {
        return userId;
    }
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getTime() {
        return time;
    }
    public String getAction() {
        return action;
    }
    public int getfile_i() {
        return file_id;
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
    public void setAction(String action) {
        this.action = action;
    }
    public void setfile_i(int file_id
    ) {
        this.file_id = file_id;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getFile() {
        return filename;
    }
    public int getFile_id() {
        return file_id;
    }


}
