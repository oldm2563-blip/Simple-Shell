package ma.youcode.lineperma.Model;

public class Filen {
    private String file;
    private String username;
    private String[] permission = {"-", "-", "-"};

    public Filen(String file, String username, String[] permission) {
        this.file = file;
        this.username = username;
        this.permission = permission;
    }

}
