import java.io.IOException;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;

public class lessonsController {
    layoutController layoutController = new layoutController();

    private ObservableList<String> getStyleBtn (ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        return clickedButton.getStyleClass();
    }
    @FXML
    private void goBack(ActionEvent event) throws IOException {
        // this method changes the scene to the main layout without losing the size and position of the window
        layoutController.changeSceneFromTo(event, "mainLayout.fxml", "PermisMaroc");
    }
    @FXML
    private void goToSubLessons(ActionEvent event) throws IOException {
        String lessonName = getStyleBtn(event).get(1);
        List<String> subLessons = layoutController.getListFromJson("title_ar", "./assets/lessons/" + lessonName + "/" + lessonName + ".json");
        for (int i = 0; i < subLessons.size(); i++) {
            subLessons.set(i, subLessons.get(i).trim());
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/subLessons.fxml"));
        Parent root = loader.load();
        subLessonsController controller = loader.getController();
        controller.setLessons(lessonName, subLessons);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
