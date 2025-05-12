public class series {
    String      name;
    question[]  questions;
    //here is where the answers are stored
    boolean[][] selectedAnswers;
    int         score = 0;

    // public  getAllSeries

    public void addselectedAnswers(boolean[] selectedAnswers, int index) {
        this.selectedAnswers[index] = selectedAnswers;
    }

    //create a function called check answers to compare questions and selected Answers
    public int calculateScore() {
        for (int i = 0; i < questions.length; i++) {
            boolean[] correctAnswers = questions[i].getcorrectAnswers();
            boolean[] selectedAnswers = this.selectedAnswers[i];
            if (myCompare4(correctAnswers, selectedAnswers))
                score++;
        }
        return score;
    }

    private boolean myCompare4(boolean[] correctAnswers, boolean[] selectedAnswers) {
        if (correctAnswers.length != selectedAnswers.length) {
            return false;
        }
        for (int i = 0; i < correctAnswers.length; i++) {
            if (correctAnswers[i] != selectedAnswers[i]) {
                return false;
            }
        }
        return true;
    }

}
