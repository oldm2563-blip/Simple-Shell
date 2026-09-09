package ma.youcode.lineperma.ui;
import java.util.Scanner;

import ma.youcode.lineperma.Model.User;
import ma.youcode.lineperma.Service.UserService;


public class Consoleapp {

    Scanner scanner = new Scanner(System.in);
    UserService user = new UserService();
    public void demarrer(){
        
        user.loadUsersToMap();
        System.out.println("=====================Welcome=======================");
        System.out.println("pick a command From the following : SignUp | Login | help | Exit");

        while (true) {
            System.out.print("lineperma>");
            String choice = scanner.nextLine();

            switch (choice.toLowerCase().trim()) {
                case "signup":
                    user.CreateUser();
                    break;
                case "login":
                    User check = user.login();
                    if (check != null) {
                        Loggedin(check);
                    }
                    break;
                case "help":
                    System.out.print("Not avaible");
                    break;
                case "exit":
                        System.out.print("Goodbye");
                        scanner.close();
                        return;
            
                default:
                    System.out.println("You dont have access to these commands use help if you dont know");
                    break;
            }
        }
    }

    public void Loggedin(User user){
        while (true) {
            String name = user.getName();
            System.out.print("lineperma@" + name + ">" );
            String choice = scanner.nextLine();

            switch (choice.toLowerCase().trim()) {
                case "help":
                    System.out.println("Not avaible");
                    break;
                case "logout":
                        System.out.println("LoggingOut");
                        return;
                default:
                    System.out.println("Doesnt Exist Type 'help' for help");
                    break;
            }
        }
    }
    
}