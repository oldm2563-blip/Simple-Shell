package ma.youcode.lineperma.ui;
import java.util.Scanner;

import ma.youcode.lineperma.Model.User;
import ma.youcode.lineperma.Service.UserService;


public class Consoleapp {

    Scanner scanner = new Scanner(System.in);
    FileService fille = new FileService();
    UserService user = new UserService();
    public void demarrer(){
        fille.starup();
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
            
            if (choice.isEmpty()) continue;
            
            
            String[] command = choice.split("\\s+");
            


            switch (command[0].toLowerCase().trim()) {
                case "help":
                    System.out.println("Available commands: touch, nano, cat, ls, chmod, logout");
                    break;

                case "touch":
                    if (command.length < 2) {
                        System.out.println("Usage: touch <filename>");
                        break;
                    }
                    fille.touch(command[1], user.getName());
                    break;

                case "nano":
                    if (command.length < 2) {
                        System.out.println("Usage: nano <filename>");
                        break;
                    }
                    fille.nano(command[1], user.getName());
                    break;

                case "cat":
                    if (command.length < 2) {
                        System.out.println("Usage: cat <filename>");
                        break;
                    }
                    fille.cat(command[1], user.getName());
                    break;

                case "ls":
                    fille.ls();
                    break;

                case "chmod":
                    if (command.length < 3) {
                        System.out.println("Usage: chmod <filename> <permissions>");
                        break;
                    }
                    fille.chmod(command[1], command[2], user.getName());
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