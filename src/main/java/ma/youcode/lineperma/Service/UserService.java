package ma.youcode.lineperma.Service;

import java.util.Scanner;

import ma.youcode.lineperma.DAO.UserDao;
import ma.youcode.lineperma.Model.User;
import org.mindrot.jbcrypt.BCrypt;


public class UserService {

    Scanner scanner = new Scanner(System.in);

    public void CreateUser(){
        System.out.print("Enter username: ");
        String name = scanner.nextLine();
        User userr = new UserDao().findByUsername(name);
        if (userr != null) {
            System.out.println("exists");
            return ;
        }
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

        User user = new User(name, hashedPassword);
        UserDao newUser = new UserDao();
        newUser.save(user);
    }

    public User login(){
        System.out.print("Enter username: ");
        String name = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = new UserDao().findByUsername(name);
        if (user == null) {
            System.out.println("Login Wasn't a success");
            return null;
        }
        if (!BCrypt.checkpw(password, user.getPassword())) {
            System.out.println("Login Wasn't a success");
            return null;
        }
        System.out.println("Noice");
        return user;
    }
}
