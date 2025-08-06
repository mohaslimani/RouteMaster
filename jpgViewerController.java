import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

public class jpgViewerController {
    layoutController layoutController = new layoutController();
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ImageView imageView;
    @FXML
    public void initialize() {
        // Bind the ImageView width to the ScrollPane viewport width
        imageView.fitWidthProperty().bind(scrollPane.viewportBoundsProperty().map(bounds -> bounds.getWidth()));
        imageView.setPreserveRatio(true);
    }

    @FXML
    public void setMyImage(String id, String lessonName){
        // Set the image to the ImageView
        imageView.setImage(new javafx.scene.image.Image(getClass().getResourceAsStream("/assets/lessons/"+ lessonName +"/" + id + ".jpg")));
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        // this method changes the scene to the main layout without losing the size and position of the window
        layoutController.changeSceneFromTo(event, "dorousView.fxml", "دروس");
    }
}
