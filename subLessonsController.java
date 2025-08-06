import java.io.IOException;
import java.util.List;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;

public class subLessonsController {
    layoutController layoutController = new layoutController();
    String lessonName;
    @FXML
    FlowPane myFlowPane;

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
    public void setLessons(String lessonName, List<String> subLessons) {
        this.lessonName = lessonName;
        int i = 1;
        for (String item : subLessons) {
            int idEffectivelyFinal = i; // Effectively final for lambda expression error
            StackPane card = new StackPane(myText(item));
            card.setOnMouseClicked(event -> layoutController.handleCardClick(event, "jpgViewer", item, idEffectivelyFinal, lessonName));
            card.getStyleClass().add("card");
            FlowPane.setMargin(card, new Insets(10));
            card.setStyle("-fx-background-image: url('assets/lessons/" + lessonName + "/icons/" + i + ".png');");
            myFlowPane.getChildren().add(card);
            i++;
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        // this method changes the scene to the main layout without losing the size and position of the window
        layoutController.changeSceneFromTo(event, "dorousView.fxml", "دروس");
    }
    @FXML
    private void jpgViewer(ActionEvent event) throws IOException {
        // this method changes the scene to the main layout without losing the size and position of the window
        layoutController.changeSceneFromTo(event, "jpgViewer.fxml", "دروس");
    }
}
