import java.io.File;

public class Main {
    public static void main(String[] args) {
        //create a class for this part of code and call it folder explorer
        //make it a tree and store all the assets in the folder assets into your class called folder explorer
        try {
            String path = folderExplorer.getPath();
            //those are the series
            folderExplorer.getFoldersNames(path);
            //now for every series you gotta find the questions
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
