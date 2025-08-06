import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class correctionViewController {
    layoutController layoutController = new layoutController();
    List<Questions> questList;
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

    @FXML
    public void setCorrection(List<Questions> questionsList) {
        this.questList = questionsList;
        for (Questions qst : questionsList) {
            System.out.println("Question: " + qst.getPath());
            StackPane card = new StackPane(myText(qst.getPath().split("/")[3]));
            card.setOnMouseClicked(event -> {
                try {
                    correctionClick(event, "selectedCorrectionView", qst, questionsList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            card.getStyleClass().add("card");
            card.getStyleClass().add("cardGrp");
            FlowPane.setMargin(card, new Insets(10));
            if (qst.checkScore())
                card.setStyle("-fx-background-image: url('images/green.png');-fx-background-size: contain;-fx-pref-width: 100px;");
            else
                card.setStyle("-fx-background-image: url('images/red.png');-fx-background-size: contain;-fx-pref-width: 100px;");
            myFlowPane.getChildren().add(card);
        }
    }

    @FXML
    public void correctionClick(MouseEvent event, String name, Questions qst, List<Questions> questionsList) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + name + ".fxml"));
            Parent newRoot = loader.load();
    
            selectedCorrectionViewController controller = loader.getController();
            controller.setAnswers(qst, questionsList);
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            double width = stage.getWidth();
            double height = stage.getHeight();
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(newRoot);
            stage.setScene(newScene);
            stage.setTitle(qst.getPath().split("/")[3]);
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
}
