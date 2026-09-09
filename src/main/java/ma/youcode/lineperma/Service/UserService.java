package ma.youcode.lineperma.Service;

import java.io.FileWriter; 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import ma.youcode.lineperma.Model.User;
import org.mindrot.jbcrypt.BCrypt;


public class UserService {

    Scanner scanner = new Scanner(System.in);

    public Map<String, User> loadUsersToMap() {

        Map<String, User> userMap = new HashMap<>();
        Path path = Paths.get("C:\\java-bootcamp\\simple-shell\\src\\main\\resources\\Users.txt");

        if (Files.exists(path)) {
        } else {
            try (FileWriter writer = new FileWriter(path.toFile(), true)) {
            } catch (IOException e) {
                System.out.println("Could not create/write file");
            }
        }

        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    String hashedPassword = parts[1].trim();
                    
                    userMap.put(username, new User(username, hashedPassword));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return userMap;
    }

    public void CreateUser(){
        Map<String, User> userMap = loadUsersToMap();
        String filePath = "C:\\java-bootcamp\\simple-shell\\src\\main\\resources\\Users.txt ";
        
        System.out.print("Enter username: ");
        String name = scanner.nextLine().trim();

        if (userMap.containsKey(name)) {
            System.out.println("Error: Username '" + name + "' is already taken!");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));



        try (FileWriter writer = new FileWriter(filePath, true)) {
                writer.write(name + ":" + hashedPassword + System.lineSeparator());
                System.out.println("User registered successfully.");
            }
             catch (IOException e) {
            System.out.println("Could not write to file: " + e.getMessage());
        }
    
}

    public User login(){
        Map<String, User> userMap = loadUsersToMap();
        System.out.print("Enter username: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if(userMap.containsKey(name)){
            User existingUser = userMap.get(name);
            if(BCrypt.checkpw(password, existingUser.getPassword())){
                System.out.println("user Logged in");
                return existingUser;
            }
           System.out.println("User info wrong.");
            return null;
        }System.out.println("User info wrong.");
        return null;
}
}
