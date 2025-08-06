import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class groupsViewController {
    layoutController layoutController = new layoutController();
    String groupName;
    List<String> groups;
    @FXML
    FlowPane myFlowPane;

    @FXML
    private Text myText(String item) {
        Text text = new Text(item);
        text.setFill(Color.BLACK);
        text.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        // White glow effect
        DropShadow highlight = new DropShadow();
        highlight.setColor(Color.WHITE);
        highlight.setRadius(5);
        highlight.setSpread(0.8);

        text.setEffect(highlight);
        return text;
    }

    public static List<String> getSubFoldersNames(String folderPath) {
        List<String> folderNames = new ArrayList<>();
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        folderNames.add(file.getName());
                    }
                }
            }
        } else {
            System.out.println("Invalid folder path: " + folderPath);
        }
        Collections.sort(folderNames);

        return folderNames;
    }

    // @FXML
    // public void handleSeriesClick(MouseEvent event, String name, String title) {
    //     try {
    //         FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + name + ".fxml"));
    //         Parent newRoot = loader.load();

    //         groupsViewController controller = loader.getController();
    //         controller.setSeries(title);

    //         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    //         double width = stage.getWidth();
    //         double height = stage.getHeight();
    //         double x = stage.getX();
    //         double y = stage.getY();
    //         Scene newScene = new Scene(newRoot);
    //         stage.setScene(newScene);
    //         stage.setTitle(title);
    //         stage.setWidth(width);
    //         stage.setHeight(height);
    //         stage.setX(x);
    //         stage.setY(y);
    //         stage.show();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    // @FXML
    // public void setSeries(String groupName){
    //     this.groupName = groupName;
    //     System.out.println(this.groupName);
    //     // access the folder and get the folders list
    //     List<String> groups = getSubFoldersNames("assets/" + groupName);
    //     System.out.println("gr: " + groups);
    //     this.groups = groups;
    //     for (String grp : groups) {
    //         StackPane card = new StackPane(myText(grp));
    //         card.setOnMouseClicked(event -> qcmViewController.qcmClick(event, groupName, grp));
    //         card.getStyleClass().add("card");
    //         card.getStyleClass().add("cardGrp");
    //         FlowPane.setMargin(card, new Insets(10));
    //         card.setStyle("-fx-background-image: url('assets/lessons/vehicle/vehicle.png');");
    //         myFlowPane.getChildren().add(card);
    //     }
    // }

    @FXML
    public void setGroup(String groupName){
        this.groupName = groupName;
        System.out.println(this.groupName);
        // access the folder and get the folders list
        List<String> groups = getSubFoldersNames("assets/" + groupName);
        System.out.println("gr: " + groups);
        this.groups = groups;
        for (String grp : groups) {
            StackPane card = new StackPane(myText(grp));
            // card.setOnMouseClicked(event -> handleSeriesClick(event, "groupsView", groupName + "/" + grp));
            card.setOnMouseClicked(event -> {
                try {
                    qcmClick(event, "qcmView", groupName, grp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            card.getStyleClass().add("card");
            card.getStyleClass().add("cardGrp");
            FlowPane.setMargin(card, new Insets(10));
            card.setStyle("-fx-background-image: url('assets/lessons/trafficRules/trafficRules.png');");
            myFlowPane.getChildren().add(card);
        }
    }

    @FXML
    public void qcmClick(MouseEvent event, String name, String groupName, String grp) throws IOException {
        try {
            System.out.println(name + "-" + grp);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + name + ".fxml"));
            Parent newRoot = loader.load();
    
            qcmViewController controller = loader.getController();
            controller.setQuestion("assets/" + groupName + "/" + grp);
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double width = stage.getWidth();
            double height = stage.getHeight();
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(newRoot);
            stage.setScene(newScene);
            stage.setTitle(groupName);
            stage.setWidth(width);
            stage.setHeight(height);
            stage.setX(x);
            stage.setY(y);
            stage.show();
            //lets make this the function that will show 
            //show and load controller of 
        } catch (IOException e) {
            e.printStackTrace();}
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        // this method changes the scene to the main layout without losing the size and position of the window
        layoutController.changeSceneFromTo(event, "mainLayout.fxml", "دروس");
    }

    @FXML
    private void chooseFolder(ActionEvent event) {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedDirectory = chooseFolder(primaryStage);
        if (selectedDirectory != null) {
            // Handle the selected directory as needed
            System.out.println("Selected directory: " + selectedDirectory.getAbsolutePath());
            try {
                FolderCopier.copyFolder(selectedDirectory.toPath(), Paths.get("./assets/custom/"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public File chooseFolder(Stage primaryStage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        
        if (selectedDirectory != null) {
            System.out.println("Folder selected: " + selectedDirectory.getAbsolutePath());
            return selectedDirectory;
        } else {
            System.out.println("No folder selected.");
            return null;
        }
    }
}

class FolderCopier {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of(
        ".mp3", ".jpg", ".png", ".jpeg", ".gif", ".json"
    );

    public static void copyFolder(Path sourceDir, Path targetDir) throws IOException {
        Files.walk(sourceDir).forEach(sourcePath -> {
            try {
                Path relativePath = sourceDir.relativize(sourcePath);
                Path targetPath = targetDir.resolve(relativePath);

                if (Files.isDirectory(sourcePath)) {
                    Files.createDirectories(targetPath);
                } else {
                    String fileName = sourcePath.getFileName().toString().toLowerCase();
                    if (hasAllowedExtension(fileName)) {
                        Files.createDirectories(targetPath.getParent());
                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static boolean hasAllowedExtension(String fileName) {
        return ALLOWED_EXTENSIONS.stream().anyMatch(fileName::endsWith);
    }
}