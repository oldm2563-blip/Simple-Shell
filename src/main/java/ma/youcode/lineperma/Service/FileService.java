package ma.youcode.lineperma.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import ma.youcode.lineperma.DAO.FileDao;
import ma.youcode.lineperma.Model.Filen;
import ma.youcode.lineperma.Model.User;

public class FileService {

    Scanner scanner = new Scanner(System.in);
    FileDao conf = new FileDao();
    Map<String, Filen> fileMap = new HashMap<>();
    LogsService log;


    public FileService(LogsService log){
        this.log = log;
        fileMap = conf.getAll();
    }


    public void touch(String name, User user){
        String[] per = {"-", "-", "-"};
        Filen file = new Filen(name, "", user.getId(), user.getName(), per);
        
        fileMap.put(name, file);
        conf.save(file);
        
        System.out.println(name + " was created");
    }

    public void ls() {
        if (fileMap.isEmpty()) {
            System.out.println("No files found.");
            return;
        }

        for (Map.Entry<String, Filen> entry : fileMap.entrySet()) {
            Filen file = entry.getValue();
            
            System.out.printf("%s\t%s\t%s%n", 
                file.getpermissioString(), 
                file.getOwnerUsername(), 
                file.getFile()
            );
        }
    }

    public void nano(String name, User username){  

        Filen file = fileMap.get(name);

        if (!username.getName().equals(file.getOwnerUsername()) && !file.getpermissioString().contains("w")) {
            System.out.println("u dont have the RIGHTS");
            log.newLog(file, "WRITE", "DENIED", username.getId());
            return ;
        }

        StringBuilder sb = new StringBuilder();
        while(true){
            String text = scanner.nextLine();
            
            if (text.contains("EOF")) {
                String contentBeforeEOF = text.substring(0, text.indexOf("EOF"));
                if (!contentBeforeEOF.isEmpty()) {
                    sb.append(contentBeforeEOF);
                }
                break;
            }
            sb.append(text).append(System.lineSeparator());
        }
        System.out.println(sb);

        log.newLog(file, "WRITE", "OK", username.getId());
        
        conf.updateContent(sb.toString(), file);

    }

    public void cat(String name, User username){
        Filen file = fileMap.get(name);

        if (!username.getName().equals(file.getOwnerUsername()) && !file.getpermissioString().contains("r")) {
            System.out.println("u dont have the RIGHTS");
            log.newLog(file, "READ", "DENIED", username.getId());
            return ;
        }
        if (file.getContent().isEmpty()) {
            System.out.println("(Empty txt file)");
            return ;
        }
        System.out.println(file.getContent());
        log.newLog(file, "READ", "OK", username.getId());
    }

    public void chmod(String name, String per, User username){
        Filen file = fileMap.get(name);
        if (!username.getName().equals(file.getOwnerUsername()) && !file.getpermissioString().contains("w")) {
            System.out.println("u dont have the RIGHTS");
            log.newLog(file, "SET PERSSION", "DENIED", username.getId());
            return ;
        }
        conf.updatePermission(per, file);
        System.out.println("Noice");
        log.newLog(file, "SET PERSSION", "OK", username.getId());
    } 

}