package ma.youcode.lineperma.ui;

import ma.youcode.lineperma.Model.User;
import ma.youcode.lineperma.Service.UserService;


public class Consoleapp {

    Scanner scanner = new Scanner(System.in);
    UserService User = new UserService();
    public void demarrer(){
        
        User.loadUsersToMap();
        System.out.println("=====================Welcome=======================");
        System.out.println("pick a command From the following : SignUp | Login | help | Exit");

        while (true) {
            System.out.print("lineperma>");
            String choice = scanner.nextLine();

            switch (choice.toLowerCase().trim()) {
                case "signup":
                    UserService User2 = new UserService();
                    User2.CreateUser();
                    break;
                case "login":
                    UserService login = new UserService();
                    User check = login.login();
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
}