package ma.youcode.lineperma.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import ma.youcode.lineperma.Model.Filen;

public class FileDao extends AbstractDao<Filen> {
    public void save(Filen file){
        String sql = "INSERT INTO files (fileName, content, owner, permission) VALUES (?, ?, ?, ?)";
        
        try{
            Connection conn = getConnection();
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, file.getFile());
            stmt.setString(2, file.getContent());
            stmt.setInt(3, file.getUserId());
            stmt.setString(4, file.getpermissioString());

            stmt.executeUpdate();
            }
        }catch(SQLException e){
            System.out.println("Error :" + e);
        }
    }
    public Filen findById(int id) {
    String sql = "SELECT f.*, u.name FROM files f LEFT JOIN users u ON f.owner = u.id WHERE f.id = ?";
        try{
            Connection conn = getConnection();
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String fileName = rs.getString("fileName");
                String content = rs.getString("content");
                int ownerId = rs.getInt("owner");
                String ownerUsername = rs.getString("name");
                String permStr = rs.getString("permission");
                String[] permissions = permStr.split("");
                return new Filen(id, fileName, content, ownerId, ownerUsername, permissions);
            }

            System.out.println("File doesn't exist");
            return null;
        }
    }
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }

    System.out.println("There was an issue, try again later");
    return null;
}

    public void delete(int id){

    }

    public Map<String, Filen> getAll(){
        String sql = "SELECT f.*, u.name FROM files f LEFT JOIN users u ON f.owner = u.id";
        Map<String, Filen> fileMap = new HashMap<>();
        try{
            Connection conn = getConnection();
        try(PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String fileName = rs.getString("fileName");
                    String content = rs.getString("content");
                    int ownerId = rs.getInt("owner");
                    String ownerUsername = rs.getString("name");
                    
                    String permStr = rs.getString("permission");
                    String[] permissions = permStr.split("");

                    Filen file = new Filen(id, fileName, content, ownerId, ownerUsername, permissions);
                    fileMap.put(fileName, file);
                }
            }
            }catch(SQLException e){
            System.out.println("Error :" + e);
        }
        System.out.println(fileMap.size());
        return fileMap;
    }

    public void updatePermission(String per, Filen file){
        String sql = "Update files set permission = ? where id = ?";
        try{
            Connection conn = getConnection();
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, per);
                stmt.setInt(2, file.getId());
                stmt.executeUpdate();
                String[] newPer = per.split("", 3);
                file.setPermission(newPer);
            }
            }catch(SQLException e){
            System.out.println("Error :" + e);
        }
    }

    public void updateContent(String content, Filen file){
        String sql = "Update files set content = ? where id = ?";

        try(Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, content);
                stmt.setInt(2, file.getId());
                stmt.executeUpdate();
                
                file.setContent(content);
            }catch(SQLException e){
            System.out.println("Error :" + e);
        }
    }
}
