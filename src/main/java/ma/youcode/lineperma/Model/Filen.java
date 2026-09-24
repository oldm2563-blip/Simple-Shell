package ma.youcode.lineperma.Model;

public class Filen {
    private int id;
    private String file;
    private String content;
    private int userId;
    private String ownerUsername;
    private String[] permission = {"-", "-", "-"};

    public Filen(String file, String content, int userId, String ownerUsername, String[] permission) {
        this.file = file;
        this.content = content;
        this.userId = userId;
        this.ownerUsername = ownerUsername;
        this.permission = permission;
    }

    public Filen(int id, String file, String content, int userId, String ownerUsername, String[] permission) {
        this.id = id;
        this.file = file;
        this.content = content;
        this.userId = userId;
        this.ownerUsername = ownerUsername;
        this.permission = permission;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFile() { return file; }
    public void setFile(String file) { this.file = file; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getOwnerUsername() { return ownerUsername; }
    public void setOwnerUsername(String ownerUsername) { this.ownerUsername = ownerUsername; }

    public String[] getPermission() { return permission; }
    public void setPermission(String[] permission) { this.permission = permission; }

    public String getpermissioString() {
        return String.join("", permission);
    }
}