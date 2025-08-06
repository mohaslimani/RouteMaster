import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import javafx.util.Duration;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collections;

public class qcmViewController {
    String path;
    List<Questions> questionsList;
    int currentQuestionIndex = 1;
    int score = 0;
    @FXML
    private Pane card1, card2, card3;
    ImageView imageView;
    @FXML
    private Button one, two, three, four, selectedTxt;
    boolean cOne, cTwo, cThree, cFour;
    @FXML
    private Label questionNumberLabel;
    @FXML
    private Pane questionNumber;
    @FXML
    private Label countdownLabel;
    @FXML
    private Pane countdown;
    @FXML
    private Button confirm;
    Timeline timeline;
    @FXML
    private Pane audioIn;

    private void startCountdown(Runnable onFinish) {
        final int[] secondsLeft = {30};
        countdownLabel.setText("30");

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            secondsLeft[0]--;
            countdownLabel.setText(secondsLeft[0] + "");
            if (secondsLeft[0] <= 0) {
                onFinish.run();
            }
        }));

        timeline.setCycleCount(30); // Run for 30 seconds
        timeline.play();            // Start the countdown
    }

    @FXML
    private void updateQuestionNumber(int questionIndex) {
        questionNumberLabel.setText(" سؤال رقم : " + questionIndex);
        if (questionsList.get(questionIndex - 1).getAudio() != null) {
            audioIn.setStyle(
                "-fx-background-image: url('/images/audioIn.png');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: contain;" +
                "-fx-background-position: center center;"
            );
        } else {
            audioIn.setStyle(
                "-fx-background-image: url('/images/audioOut.png');" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: contain;" +
                "-fx-background-position: center center;"
            );
        }   
    }

    @FXML
    private void checkOne(ActionEvent event) {
        cOne = !cOne;
        if (cOne) {
            one.getStyleClass().remove("grayBackground");
            one.getStyleClass().add("greenBackground");
            selectedTxt.setText(selectedTxt.getText().replaceFirst("___", "1"));
        } else {
            one.getStyleClass().remove("greenBackground");
            one.getStyleClass().add("grayBackground");
            selectedTxt.setText(selectedTxt.getText().replaceFirst("1", "___"));
        }
    }

    @FXML
    private void checkTwo(ActionEvent event) {
        cTwo = !cTwo;
        if (cTwo) {
            two.getStyleClass().remove("grayBackground");
            two.getStyleClass().add("greenBackground");
            selectedTxt.setText(selectedTxt.getText().replaceFirst("___", "2"));
        } else {
            two.getStyleClass().remove("greenBackground");
            two.getStyleClass().add("grayBackground");
            selectedTxt.setText(selectedTxt.getText().replaceFirst("2", "___"));
        }
    }

    @FXML
    private void checkThree(ActionEvent event) {
        cThree = !cThree;
        if (cThree) {
            three.getStyleClass().remove("grayBackground");
            three.getStyleClass().add("greenBackground");
            selectedTxt.setText(selectedTxt.getText().replaceFirst("___", "3"));
        } else {
            three.getStyleClass().remove("greenBackground");
            three.getStyleClass().add("grayBackground");
            selectedTxt.setText(selectedTxt.getText().replaceFirst("3", "___"));
        }
    }

    @FXML
    private void checkFour(ActionEvent event) {
        cFour = !cFour;
        if (cFour) {
            four.getStyleClass().remove("grayBackground");
            four.getStyleClass().add("greenBackground");
            selectedTxt.setText(selectedTxt.getText().replaceFirst("___", "4"));
        } else {
            four.getStyleClass().remove("greenBackground");
            four.getStyleClass().add("grayBackground");
            selectedTxt.setText(selectedTxt.getText().replaceFirst("4", "___"));
        }
    }

    @FXML
    public void onConfirm(ActionEvent event) {
        questionsList.get(currentQuestionIndex - 1).setClientAnswers(new boolean[]{cOne, cTwo, cThree, cFour});
        System.out.println(currentQuestionIndex);
        System.out.println(questionsList.get(currentQuestionIndex - 1).getImage());
        if (questionsList.get(currentQuestionIndex - 1).checkScore()) {
            score++;
        }
        if (currentQuestionIndex >= questionsList.size()) {
            System.out.println("score; " + score);
            stopAudio();
            // change the view 
            moveToScore(event, "scoreView", score, questionsList);
            return ;
        }
        resetChecks();
        System.out.println("this is it:: " + questionsList.get(currentQuestionIndex - 1).getClientAnswers()[0]);
        imageView.setImage(new Image("file:///" + questionsList.get(currentQuestionIndex).getImage().replace("\\", "/")));
        startAudio(questionsList.get(currentQuestionIndex).getAudio());
        currentQuestionIndex++;
        updateQuestionNumber(currentQuestionIndex);
    }

    @FXML
    public void moveToScore(ActionEvent event, String name, int score, List<Questions> questionsList) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + name + ".fxml"));
            Parent newRoot = loader.load();
    
            scoreViewController controller = loader.getController();
            controller.setScore(score, questionsList);
    
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
            //lets make this the function that will show 
            //show and load controller of 
        } catch (IOException e) {
            e.printStackTrace();}
    }

    @FXML
    public void resetChecks() {
        this.cOne = false;
        this.cTwo = false;
        this.cThree = false;
        this.cFour = false;
        one.getStyleClass().remove("greenBackground");
        two.getStyleClass().remove("greenBackground");
        three.getStyleClass().remove("greenBackground");
        four.getStyleClass().remove("greenBackground");
        selectedTxt.setText("___    ___    ___    ___");
    }

    @FXML
    private void onCorrect(ActionEvent event) {
        resetChecks();
    }

    public List<Questions> getQuestionsPaths(String path) throws Exception {
        File[] folder = new File(path).listFiles();
        List<Questions> questionsList = new ArrayList<Questions>();
        
        for (File file : folder) {
            System.out.println(path + "/" + file.getName());
            if (file.isDirectory()) {
                questionsList.add(getPaths(path + "/" + file.getName()));
            }    
        }
        return questionsList;
    }

    public void addImage(String imagePath) {
        // card1.setStyle("-fx-background-image: url('file:///" + imagePath.replace("\\", "/") + "'); -fx-background-size: contain; -fx-background-repeat: no-repeat; -fx-background-position: center center;");
        Image newImage = new Image("file:///" + imagePath.replace("\\", "/"));
        imageView = new ImageView(newImage);
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

    MediaPlayer player;

    public void stopAudio() {
        if (player != null) {
            MediaPlayer.Status status = player.getStatus();
            if (status == MediaPlayer.Status.PLAYING || status == MediaPlayer.Status.PAUSED) {
                player.stop();
            }
            player.dispose();
        }
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
        countdownLabel.setText("30");
    }

    public void startAudio(String audioPath) {
        stopAudio();
        try {
            File mp3File = new File(audioPath);
            Media media = new Media(mp3File.toURI().toString());
            player = new MediaPlayer(media);
            player.play();
            player.setOnEndOfMedia(() -> {startCountdown(() -> { confirm.fire(); });});
        } catch (Exception e) {
            startCountdown(() -> { confirm.fire(); });
            e.printStackTrace();
        }
    }
    
    public void setQuestion(String path) {
        try {
            questionsList = getQuestionsPaths(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addImage(questionsList.get(0).getImage());
        startAudio(questionsList.get(0).getAudio());
        updateQuestionNumber(1);
        // and statically add icons, image, audio, solution audio, solution image
        // create a method that will take the list and put the first question in the view
        // meaning get the ids of each element and put them one by one,
    }

    public Questions getPaths(String path) throws Exception {
        Questions question = new Questions(path);
        File files[] = new File(path).listFiles();
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
            if ((file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".gif")) && !file.getName().startsWith("solution")) {
                question.setImage(file.getAbsolutePath());
            } else if (file.getName().endsWith(".mp3") && !file.getName().startsWith("solution")) {
                question.setAudio(file.getAbsolutePath());
            } else if (file.getName().endsWith(".json")) {
                List<String> boolist = layoutController.getListFromJson("isCorrect", file.getAbsolutePath());
                Collections.reverse(boolist);
                boolean[] boolArray = new boolean[4];
                for (int i = 0; i < boolist.size(); i++) {
                    boolArray[i] = Boolean.parseBoolean(boolist.get(i));
                    System.out.println(boolArray[i]);
                }
                question.setRealAnswers(boolArray);
            } else if (file.getName().endsWith("solution.mp3")) {
                question.setSolutionAudio(file.getAbsolutePath());
            } else if (file.getName().endsWith("solution.png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".gif")) {
                question.setSolutionImage(file.getAbsolutePath());
            }
        }
        return question;
    }

    @FXML
    private VBox cardContainer;

    @FXML
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
    }
}

