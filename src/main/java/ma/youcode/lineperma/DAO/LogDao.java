
package ma.youcode.lineperma.DAO;

import ma.youcode.lineperma.Model.Logs;

import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogDao extends AbstractDao<Logs> {

    public void save(Logs log) {
        String sql = "INSERT INTO logs (log_date, log_time, user_id, action, file_id, success) VALUES (?, ?, ?, ?, ?, ?)";
        try{
            Connection conn = getConnection();
        try(PreparedStatement stat = conn.prepareStatement(sql)){
                stat.setString(1, log.getDate().toString());
                stat.setString(2, log.getTime().toString());
                stat.setInt(3, log.getUserId());
                stat.setString(4, log.getAction());
                stat.setInt(5, log.getfile_i());
                stat.setString(6, log.getResult());

                stat.executeUpdate();
            }
        }catch(SQLException e){
            System.out.println("Error :" + e);
        }
    }
    public Logs findById(int id) {
        return null;
    }

    public void delete(int id) {
        throw new UnsupportedOperationException("Delete operation is not supported for users.");
    }


    // Nombre total d'actions
    public int getTotalActions() {
        String sql = "SELECT COUNT(*) AS log_count FROM logs";
        int result = 0;
        try{
            Connection conn = getConnection();
        try(PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet rs = stat.executeQuery()){
                if (rs.next()) {
                    result = rs.getInt("log_count");
                }
            }
        }catch(SQLException e){
            System.out.println("Error :" + e);
        }
        return result;
    }

    // Nombre d'accès refusés
    public int getDeniedAccesses() {
        String sql = "SELECT COUNT(*) AS log_count FROM logs WHERE success = 'DENIED'";
        int result = 0;
        try{
            Connection conn = getConnection();
        try(PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet rs = stat.executeQuery()){
                if (rs.next()) {
                    result = rs.getInt("log_count");
                }
            }
        }catch(SQLException e){
            System.out.println("Error :" + e);
        }
        return result;
    }

    // Utilisateurs distincts
    public int getDistinctUsers() {
        String sql = "SELECT COUNT(DISTINCT user_id) AS distinct_users FROM logs";
        int result = 0;
        try{
            Connection conn = getConnection();
        try(PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet rs = stat.executeQuery()){
                if (rs.next()) {
                    result = rs.getInt("distinct_users");
                }
            }
        }catch(SQLException e){
            System.out.println("Error :" + e);
        }
        return result;
    }

    // Actions par utilisateur
    public Map<String, Integer> getActionsByUser() {
        String sql = "SELECT l.user_id, u.name, COUNT(*) AS actions FROM logs l JOIN users u ON l.user_id = u.id group by l.user_id, u.name";
        Map<String, Integer> theMap = new HashMap<>();

        try{
            Connection conn = getConnection();
        try(PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet rs = stat.executeQuery()){
                while (rs.next()) {
                    theMap.put(rs.getString("name"), rs.getInt("actions"));
                }
            }
        }catch(SQLException e){
            System.out.println("Error :" + e);
        }
        return theMap;
    }

    // Top 3 des fichiers consultés
    public Map<String, Integer> getTop3Files() {
        String sql = "SELECT f.fileName, COUNT(*) AS actions FROM logs l JOIN files f ON f.id = l.file_id GROUP BY f.fileName ORDER BY actions DESC LIMIT 3";
        Map<String, Integer> theMap = new HashMap<>();
        try{
            Connection conn = getConnection();
        try(PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet rs = stat.executeQuery()){
                while (rs.next()) {
                    theMap.put(rs.getString("fileName"), rs.getInt("actions"));
                }
            }
        }catch(SQLException e){
            System.out.println("Error :" + e);
        }
        return theMap;
    }

    // Accès refusés d'un utilisateur
    public List<Logs> getDeniedAccessesByUser(int userId) {
        String sql = "SELECT l.*, f.fileName, u.name FROM logs l JOIN files f ON f.id = l.file_id JOIN users u ON u.id = l.user_id WHERE user_id = ? AND success = 'DENIED'";
        List<Logs> logs = new ArrayList<>();
        try{
            Connection conn = getConnection();
        try (PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setInt(1, userId);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                Logs log = new Logs(
                        rs.getInt("id"),
                        LocalDate.parse(rs.getString("log_date")),
                        LocalTime.parse(rs.getString("log_time")),
                        rs.getString("name"),
                        rs.getString("action"),
                        rs.getString("fileName"),
                        rs.getString("success")
                );
                logs.add(log);
            }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return logs;
    }

    // Utilisateur le plus actif
    public Map<String, Integer> getMostActiveUser() {

        String sql = "SELECT u.name, COUNT(*) AS actions FROM logs l JOIN users u ON l.user_id = u.id GROUP BY l.user_id, u.name ORDER BY actions DESC LIMIT 1";
        Map<String, Integer> theMap = new HashMap<>();
        try{
            Connection conn = getConnection();
        try (PreparedStatement stat = conn.prepareStatement(sql);
             ResultSet rs = stat.executeQuery()) {
            if (rs.next()) {
                theMap.put(
                        rs.getString("name"),
                        rs.getInt("actions")
                );
            }
        }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return theMap;
    }


    // Répartition des actions par type
    public Map<String, Integer> getActionsByType() {

        String sql = "SELECT action, COUNT(*) AS actions FROM logs GROUP BY action";
        Map<String, Integer> theMap = new HashMap<>();
        try{
            Connection conn = getConnection();
        try (PreparedStatement stat = conn.prepareStatement(sql);
             ResultSet rs = stat.executeQuery()) {
            while (rs.next()) {
                theMap.put(
                        rs.getString("action"),
                        rs.getInt("actions")
                );
            }
        }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return theMap;
    }
}