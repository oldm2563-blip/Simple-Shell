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

