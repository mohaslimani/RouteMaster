import java.io.File;
import java.util.ArrayList;

public class folderExplorer {
    static String getPath(){
        try {
            String path = new File(Main.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()).getPath();

            File directory = new File(path).getParentFile();
            return directory.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    static ArrayList<String> getFoldersNames(String path){
        ArrayList<String> folderNames = new ArrayList<String>();
        File[] files = new File(path).listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    System.out.println(files[i].getName());
                    folderNames.add(files[i].getName());
                }
        }
        return folderNames;
    } 
}
