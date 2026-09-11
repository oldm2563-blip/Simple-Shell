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


    public String getFile() { return file; }
    public String getusername() { return username; }
    public String getpermissioString(){
        String per = String.join("", permission);
        return per;
    }

}
