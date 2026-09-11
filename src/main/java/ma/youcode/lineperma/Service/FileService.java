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
}