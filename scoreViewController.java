import java.io.IOException;
import java.util.List;

import javax.swing.Action;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class scoreViewController {
    @FXML
    private Text scoreText;
    @FXML
    private VBox scoreBg;
    @FXML
    private StackPane centerPane;
    layoutController layoutController = new layoutController();
    List<Questions> questionsList;

    @FXML
    private void goToCorrection(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/correctionView.fxml"));
        Parent newRoot = loader.load();

        correctionViewController controller = loader.getController();
        controller.setCorrection(questionsList);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        double width = stage.getWidth();
        double height = stage.getHeight();
        double x = stage.getX();
        double y = stage.getY();
        Scene newScene = new Scene(newRoot);
        stage.setScene(newScene);
        stage.setTitle("Score");
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setX(x);
        stage.setY(y);
        stage.show();
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        layoutController.changeSceneFromTo(event , "mainLayout.fxml", "PermisMaroc");
    }

    @FXML
    private void goToGroups(ActionEvent event) throws IOException {
        layoutController.goToGroups(event);
    }

    @FXML
    public void initialize() {
        scoreText.styleProperty().bind(scoreBg.heightProperty().multiply(0.4).asString("-fx-font-size: %.0fpx;"));
        centerPane.translateYProperty().bind(scoreBg.heightProperty().multiply(0.09));
    }

    public void setScore(int score, List<Questions> questionsList) {
        this.questionsList = questionsList;
        scoreText.setText(String.valueOf(score));
    }
}
