package ma.youcode.lineperma.Service;

import ma.youcode.lineperma.Model.Logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class LogsService {
    List<Logs> LogsList = new ArrayList<>();
    File filepath = new File("C:\\java-bootcamp\\simple-shell\\src\\main\\resources\\access.log");
    Scanner scanner = new Scanner(System.in);

    public void Startup(){
        if(!filepath.exists()){
            try (FileWriter writer = new FileWriter(filepath, true)){
            } catch (Exception e) {
                System.out.println("didnt work");
            }
        }

        try {
            List<String> lines = Files.readAllLines(filepath.toPath());
            lines.forEach(line -> {
                String[] parts = line.split(";", 6);
                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                LogsList.add(new Logs(date, time, parts[2], parts[3], parts[4], parts[5]));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addLog(String username, String filename, String action, String result){
        try(FileWriter Writer = new FileWriter(filepath , true)) {
            Writer.write(LocalDate.now().toString() + ";" + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + ";" + username + ";" + action + ";" + filename + ";" + result + System.lineSeparator());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        LogsList.add(new Logs(LocalDate.now(), LocalTime.now(), username, action, filename, result));
    }

    public void stats(){
        while (true){
            System.out.println("1) Total number of actions\n" +
                    "2) Number of denied access\n" +
                    "3) Distinct users\n" +
                    "4) Actions per user\n" +
                    "5) Top 3 consulted files\n" +
                    "6) Denied access for a user\n" +
                    "7) Most active user\n" +
                    "8) Breakdown of actions by type\n" +
                    "0) Quit"
            );
            System.out.print("choice :");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> System.out.println("Total actions: " + printTotalActions());
                case 2 -> System.out.println("Denied access count: " + printDeniedAccessCount());
                case 3 -> System.out.println("Distinct users: " + printDistinctUsers());
                case 4 -> printActionsPerUser().forEach((user, count) -> System.out.println(user + ": " + count));
                case 5 -> printTopFiles();
                case 6 -> printUserDeniedAccess();
                case 7 -> printMostActiveUser();
                case 8 -> printActionDistribution();
                case 0 -> {
                    System.out.println("Returning to prompt...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }

    }

    public long printTotalActions(){
        return LogsList.stream().count();
    }

    public long printDeniedAccessCount(){
        return LogsList.stream().filter(log -> log.getResult().equals("Refused")).count();
    }

    public List<String> printDistinctUsers(){
         return LogsList.stream().map(log -> log.getUser()).distinct().toList() ;
    }

    public Map<String, Long> printActionsPerUser(){
        return LogsList.stream().collect(Collectors.groupingBy(log -> log.getUser(), Collectors.counting()));
    }

    public void printTopFiles(){
        Map<String, Long> need = LogsList.stream().collect(Collectors.groupingBy(log -> log.getFile(), Collectors.counting()));
        need.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(3).forEach(entry -> System.out.println(entry.getKey() + " :" + entry.getValue()));
    }

    public void printUserDeniedAccess(){
        System.out.println("Choose A name :");
        String name = scanner.nextLine();
        long count = LogsList.stream().filter(log -> log.getUser().equals(name)).filter(log -> log.getResult().equals("Refused")).count();
        System.out.println("Denied access for " + name + ": " + count);
    }

    public void printMostActiveUser(){
        LogsList.stream().collect(Collectors.groupingBy(log -> log.getUser(), Collectors.counting())).entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).findFirst().ifPresent(entry -> System.out.println("Most active user: " + entry.getKey() + " (" + entry.getValue() + " actions)"));
    }

    public void printActionDistribution(){
        LogsList.stream().collect(Collectors.groupingBy(log -> log.getAction(), Collectors.counting())).forEach((action, count) -> System.out.println(action + " : " + count));
    }

}
