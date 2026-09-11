package ma.youcode.lineperma.Service;
public class FileService {
    public void starup(){
        if (!Files.exists(path2)) {
             try (FileWriter writer = new FileWriter(path2.toFile(), true)) {
            } catch (IOException e) {
                System.out.println("Could not create/write file");
            }
        }

        try{
            List<String> lines = Files.readAllLines(path2);
            for(String line : lines){
                String[] parts = line.split(":", 3);
                String fileName = parts[0];
                String userName = parts[1];
                String[] permission = parts[2].split("");
                fileMap.put(fileName, new Filen(fileName, userName, permission));
            }

        }
        catch(IOException e){
            System.out.println("Could not create/write file");
        }

    }

    public void touch(String name, String username){

        File file = new File("C:\\java-bootcamp\\simple-shell\\src\\main\\resources\\Linux\\" + name ); 

        try{
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File created!");
                try (FileWriter writer = new FileWriter(path2.toFile(), true)) {
                    writer.write(name + ":" + username + ":---" + System.lineSeparator());
                }
                String[] perms = {"-", "-", "-"};
                fileMap.put(name, new Filen(name, username, perms));
            }else {System.out.println("File already exists.");}

        }catch(IOException e){
            System.out.println("Could not create/write file");
        }

    }

    public void ls(){
        for(Map.Entry<String, Filen> entry : fileMap.entrySet()){
            Filen file = entry.getValue();
            System.out.println(file.getFile() + " | " + file.getpermissioString() + " | " + file.getusername());
        }
    }

    public void nano(String name, String username){  

        File file = new File("C:\\java-bootcamp\\simple-shell\\src\\main\\resources\\Linux\\" + name ); 
        
        Filen checker = fileMap.get(name);

        if (checker == null) {
        System.out.println("File does not exist: " + name);
        return;
        }

        if(!checker.getusername().equals(username) && !checker.getpermissioString().contains("w")){
            System.out.println("You dont have acces");
            return;
        }

        StringBuilder sb = new StringBuilder();

        try{
            if(!file.exists()){
                file.createNewFile();
                System.out.println("File created!");
            }
        }catch(IOException e){
            System.out.println("Could not create/write file");
            return;
        }

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

        try(FileWriter Writer = new FileWriter(file, true)){
            Writer.write(sb.toString());
            System.out.println("Saved successfully!");
        }catch(IOException e){
            System.out.println("Could not create/write file");
        }
        
    }

    public void cat(String name, String username){
        Filen checker = fileMap.get(name);

        if (checker == null) {
        System.out.println("File does not exist: " + name);
        return;
        }

        if(!checker.getusername().equals(username) && !checker.getpermissioString().contains("r")){
            System.out.println("You dont have acces");
            return;
        }

        Path file = Paths.get("C:\\java-bootcamp\\simple-shell\\src\\main\\resources\\Linux\\" + name );
        try{
            List<String> lines = Files.readAllLines(file);
            for(String line : lines){
                System.out.println(line);
            }

        }catch(IOException e){
            System.out.println("Could not read file: " + e.getMessage());
        }
        
    }

    public void chmod(String name, String newPer, String username){

        Filen fileObj = fileMap.get(name);
        if (fileObj == null) {
            System.out.println("File does not exist: " + name);
            return;
        }

        if (!fileObj.getusername().equals(username)) {
            System.out.println("Permission denied: Only the owner (" + fileObj.getusername() + ") can change permissions.");
            return;
        }

        if (newPer.length() != 3) {
            System.out.println("Invalid format. Permission must be 3 characters (e.g., rwx, rw-, ---)");
            return;
        }

        String[] permsArray = newPer.split("");
        fileObj.setPermission(permsArray);

        System.out.println("Permissions updated to '" + newPer + "' for " + name);


        try (FileWriter writer = new FileWriter(path2.toFile(), false)) {
            for (Filen file : fileMap.values()) {
                String line = file.getFile() + ":" + file.getusername() + ":" + file.getpermissioString();
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error saving permissions to file: " + e.getMessage());
        }
    } 

}