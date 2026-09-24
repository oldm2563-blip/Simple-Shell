package ma.youcode.lineperma.Service;

import ma.youcode.lineperma.Model.Logs;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import ma.youcode.lineperma.Model.Filen;
import ma.youcode.lineperma.DAO.LogDao;
import ma.youcode.lineperma.DAO.UserDao;


public class LogsService {
    List<Logs> LogsList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    LogDao concon = new LogDao();


    public void newLog(Filen file, String action, String result, int userId){
        concon.save(new Logs(LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.SECONDS), userId, action, file.getId(), result));
    }

//----------------------------------------------------------------------------------------------------------------------

    public void statisticsMenu() { 

    int choice;

    do {
        System.out.println("\n===== LOG STATISTICS =====");
        System.out.println("1. Nombre total d'actions");
        System.out.println("2. Nombre d'accès refusés");
        System.out.println("3. Utilisateurs distincts");
        System.out.println("4. Actions par utilisateur");
        System.out.println("5. Top 3 des fichiers consultés");
        System.out.println("6. Accès refusés d'un utilisateur");
        System.out.println("7. Utilisateur le plus actif");
        System.out.println("8. Répartition des actions par type");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");

        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {

            case 1:
                System.out.println("Total actions : " + concon.getTotalActions());
                break;

            case 2:
                System.out.println("Accès refusés : " + concon.getDeniedAccesses());
                break;

            case 3:
                System.out.println("Utilisateurs distincts : " + concon.getDistinctUsers());
                break;

            case 4:
                System.out.println("\n--- Actions par utilisateur ---");
                concon.getActionsByUser().forEach((user, actions) ->
                        System.out.println(user + " : " + actions)
                );
                break;

            case 5:
                System.out.println("\n--- Top 3 fichiers ---");
                concon.getTop3Files().forEach((file, actions) ->
                        System.out.println(file + " : " + actions)
                );
                break;

                case 6:
                    System.out.print("l'utilisateur : ");
                    String username = scanner.nextLine();

                    int userid = new UserDao().findByUsername(username).getId();

                    System.out.println("\n--- Accès refusés ---");

                    concon.getDeniedAccessesByUser(userid).forEach(log ->
                            System.out.println(
                                    log.getDate() + " " +
                                    log.getTime() + " | " +
                                    log.getAction() + " | " +
                                    log.getFile() + " | " +
                                    log.getResult()
                            )
                    );
                    break;

            case 7:
                System.out.println("\n--- Utilisateur le plus actif ---");
                concon.getMostActiveUser().forEach((user, actions) ->
                        System.out.println(user + " : " + actions)
                );
                break;

            case 8:
                System.out.println("\n--- Actions par type ---");
                concon.getActionsByType().forEach((action, count) ->
                        System.out.println(action + " : " + count)
                );
                break;

            case 0:
                System.out.println("Au revoir !");
                break;

            default:
                System.out.println("Choix invalide.");
            }

        } while (choice != 0);
    }
}
