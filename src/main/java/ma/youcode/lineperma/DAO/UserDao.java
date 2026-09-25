package ma.youcode.lineperma.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ma.youcode.lineperma.Model.User;

public class UserDao extends AbstractDao<User>{


    @Override
    public void save(User user) {
        String str = "INSERT into users (name, password) values (?, ?);";
        try{
            Connection conn = getConnection();
        try(PreparedStatement stat = conn.prepareStatement(str)    
    ){
        stat.setString(1, user.getName());
        stat.setString(2, user.getPassword());
        stat.executeUpdate();
    }
        }catch(SQLException e){
            System.out.println("Error :" + e);
        }

    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Delete operation is not supported for users.");
    }

    public User findByUsername(String username) {
        String sql = "Select * from users WHERE name = ?";
        try{
            Connection conn = getConnection();
        try(PreparedStatement stat = conn.prepareStatement(sql);){
                stat.setString(1, username);
                try(ResultSet rs = stat.executeQuery()){
                    if (rs.next()) {
                        return new User(rs.getInt("id"), rs.getString("name"), rs.getString("password"));
                    }
                    else{
                        return null;
                    }
                }
            }
        }catch(SQLException e){
            System.out.println("Error :" + e);
        }
        return null;
    }
}
