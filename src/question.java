public class question {
    String name;
    String imageName;
    //image image;
    String questionText;
    String audioName;
    //audio audio;
    boolean[] correctAnswers;

    public question(String name, String imageName, String questionText, String audioName, boolean[] correctAnswers) {
        this.name = name;
        this.imageName = imageName;
        this.questionText = questionText;
        this.audioName = audioName;
        this.correctAnswers = correctAnswers;
    }
    public boolean[] getcorrectAnswers() {
        return correctAnswers;
    }

    // getAllQuestions
}
