import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.Action;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class selectedCorrectionViewController {
    layoutController layoutController = new layoutController();
    List<Questions> questionsList;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox cardContainer;
    @FXML
    private Pane card1, card2, card3;
    @FXML
    private Label questionNumberLabel;
    @FXML
    private Pane questionNumber;
    @FXML
    private Label countdownLabel;
    @FXML
    private Pane countdown;
    @FXML
    private Button one, two, three, four, selectedTxt;
    @FXML
    private Pane audioIn;

    public void initialize() {
        cardContainer.prefWidthProperty().bind(card2.widthProperty());
        cardContainer.prefHeightProperty().bind(card2.heightProperty());

        int buttonCount = cardContainer.getChildren().size();
        for (Node node : cardContainer.getChildren()) {
            if (node instanceof Button btn) {
                btn.prefHeightProperty().bind(cardContainer.heightProperty().divide(buttonCount));
                btn.setMinHeight(0);
                btn.styleProperty().bind(
                Bindings.createStringBinding(() -> {
                    double fontSize = (cardContainer.getHeight() / cardContainer.getChildren().size()) * 0.35;
                    if (fontSize < 8) fontSize = 16;
                    return String.format("-fx-font-size: %.1fpx;", fontSize);
                }, cardContainer.heightProperty(), cardContainer.getChildren()));
            }
        }
        questionNumberLabel.layoutXProperty().bind(questionNumber.widthProperty().subtract(questionNumberLabel.widthProperty()).divide(2));
        questionNumberLabel.layoutYProperty().bind(questionNumber.heightProperty().subtract(questionNumberLabel.heightProperty()).divide(2));

        questionNumber.styleProperty().bind(
            Bindings.createStringBinding(() -> {
                double fontSize = questionNumber.getHeight() * 0.4;
                if (fontSize < 8) fontSize = 4;
                return String.format("-fx-font-size: %.1fpx;", fontSize);
            }, questionNumber.heightProperty()));

        countdownLabel.prefWidthProperty().bind(countdown.widthProperty());
        countdownLabel.prefHeightProperty().bind(countdown.heightProperty());

        countdown.styleProperty().bind(
            Bindings.createStringBinding(() -> {
                double fontSize = countdown.getHeight() * 0.4;
                if (fontSize < 8) fontSize = 4;
                return String.format("-fx-font-size: %.1fpx;", fontSize);
            }, countdown.heightProperty()));
        // Bind the ImageView width to the ScrollPane viewport width
        // imageView.fitWidthProperty().bind(scrollPane.viewportBoundsProperty().map(bounds -> bounds.getWidth()));
        // imageView.setPreserveRatio(true);
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        // this method changes the scene to the main layout without losing the size and position of the window
        layoutController.changeSceneFromTo(event, "dorousView.fxml", "دروس");
    }

    @FXML
    private void onConfirm(ActionEvent event) throws IOException {
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

    public void setAnswers(Questions qst, List<Questions> questionsList){
        this.questionsList = questionsList;
        System.out.println(qst.getPath());
        if (qst.getSolutionImage() != null)
            addImage(qst.getSolutionImage());
        else
            addImage(qst.getImage());

        if (qst.getSolutionAudio() != null)
            startAudio(qst.getSolutionAudio());
        else
            audioNotFound();

        if (qst.getRealAnswers()[0])
            one.getStyleClass().add("greenBackground");
        if (qst.getRealAnswers()[1])
            two.getStyleClass().add("greenBackground");
        if (qst.getRealAnswers()[2])
            three.getStyleClass().add("greenBackground");
        if (qst.getRealAnswers()[3])
            four.getStyleClass().add("greenBackground");

        if (qst.getClientAnswers()[0])
            selectedTxt.setText(selectedTxt.getText().replaceFirst("___", "1"));
        if (qst.getClientAnswers()[1])
            selectedTxt.setText(selectedTxt.getText().replaceFirst("___", "2"));
        if (qst.getClientAnswers()[2])
            selectedTxt.setText(selectedTxt.getText().replaceFirst("___", "3"));
        if (qst.getClientAnswers()[3])
            selectedTxt.setText(selectedTxt.getText().replaceFirst("___", "4"));
    }

    private void audioNotFound() {
        audioIn.setStyle(
                "-fx-background-image: url('/images/audioOut.png');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: contain;" +
                "-fx-background-position: center center;"
            );
    }

    private void startAudio(String audioPath) {
        // set icon plus plus
        File mp3File = new File(audioPath);
        Media media = new Media(mp3File.toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.play();
        audioIn.setStyle(
                "-fx-background-image: url('/images/audioIn.png');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: contain;" +
                "-fx-background-position: center center;"
            );
    }

    public void addImage(String imagePath) {
        // card1.setStyle("-fx-background-image: url('file:///" + imagePath.replace("\\", "/") + "'); -fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-position: center center;");
        Image newImage = new Image("file:///" + imagePath.replace("\\", "/"));
        ImageView imageView = new ImageView(newImage);
        imageView.setPreserveRatio(true);
        Rectangle clip = new Rectangle();
        clip.setArcWidth(20);  // corner roundness
        clip.setArcHeight(20);
        imageView.setClip(clip);
        card1.getChildren().add(imageView);

        card1.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
            double paneWidth = newBounds.getWidth();
            double paneHeight = newBounds.getHeight();

            double imageWidth = newImage.getWidth();
            double imageHeight = newImage.getHeight();

            double widthRatio = paneWidth / imageWidth;
            double heightRatio = paneHeight / imageHeight;
            double scale = Math.min(widthRatio, heightRatio) * 0.9; // contain

            double fitWidth = imageWidth * scale;
            double fitHeight = imageHeight * scale;

            imageView.setFitWidth(fitWidth);
            imageView.setFitHeight(fitHeight);

            // Center the image
            imageView.setLayoutX((paneWidth - fitWidth) / 2);
            imageView.setLayoutY((paneHeight - fitHeight) / 2);

            // Update clip size and position
            clip.setWidth(fitWidth);
            clip.setHeight(fitHeight);
        });
    }
}
