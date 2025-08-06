import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.collections.ObservableList;
import javafx.event.*;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

import javax.swing.Action;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class layoutController {

    public void changeSceneFromTo(ActionEvent event, String to, String title) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Save current size and position
        double width = stage.getWidth();
        double height = stage.getHeight();
        double x = stage.getX();
        double y = stage.getY();
        // Load second scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + to));
        Scene secondScene = new Scene(loader.load());
        secondScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        // Set new scene
        stage.setScene(secondScene);
        stage.setTitle(title);
        // Restore size and position
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setX(x);
        stage.setY(y);
    }
    @FXML
    private void handleClick(ActionEvent event) throws IOException {
        // this method changes the scene to another fxml without losing the size and position of the window
        changeSceneFromTo(event, "secondView.fxml", "Second View");
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        // this method changes the scene to the main layout without losing the size and position of the window
        changeSceneFromTo(event, "mainLayout.fxml", "PermisMaroc");
    }
    @FXML
    private void goToDorous(ActionEvent event) throws IOException {
        // this method changes the scene to the dorous layout without losing the size and position of the window
        changeSceneFromTo(event, "dorousView.fxml", "دروس");
    }

    public static List<String> getListFromJson(String name, String jsonFileName) throws IOException {
        FileReader reader = new FileReader(jsonFileName);
        JsonElement element = JsonParser.parseReader(reader);
        JsonArray array = element.getAsJsonArray();

        List<String> names = new ArrayList<>();
        for (JsonElement item : array) {
            JsonObject obj = item.getAsJsonObject();
            names.add(obj.get(name).getAsString());
        }
        reader.close();
        return names;
    }
    @FXML
    public void handleCardClick(MouseEvent event, String name, String title, int id, String lessonName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + name + ".fxml"));
            Parent newRoot = loader.load();

            jpgViewerController controller = loader.getController();
            controller.setMyImage(id + "", lessonName);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene newScene = new Scene(newRoot);
            stage.setScene(newScene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<String> getStyleBtn (ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        return clickedButton.getStyleClass();
    }
    @FXML
    public void goToGroups(ActionEvent event) throws IOException {
        String groupName = getStyleBtn(event).get(1);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/groupsView.fxml"));
        Parent root = loader.load();
        groupsViewController controller = loader.getController();
        controller.setGroup(groupName);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        double width = stage.getWidth();
        double height = stage.getHeight();
        double x = stage.getX();
        double y = stage.getY();
        Scene newScene = new Scene(root);
        stage.setScene(newScene);
        stage.setTitle(groupName);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setX(x);
        stage.setY(y);
        stage.show();
        // changeSceneFromTo(event, "groupsView.fxml", groupName);
    }
}
