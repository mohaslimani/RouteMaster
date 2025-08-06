public class Questions {

    String path;
    String image;
    String audio;
    boolean[] clientAnswers;
    boolean[] realAnswers;
    String solutionAudio;
    String solutionImage;

    Questions(String path, String image, String audio, boolean[] clientAnswers, boolean[] realAnswers, String solutionAudio, String solutionImage) {
        this.path = path;
        this.image = image;
        this.clientAnswers = clientAnswers;
        this.realAnswers = realAnswers;
        this.solutionAudio = solutionAudio;
        this.solutionImage = solutionImage;
    }
    Questions (String path) {
        this.path = path;
        this.image = null;
        this.audio = null;
        this.clientAnswers = null;
        this.realAnswers = null;
        this.solutionAudio = null;
        this.solutionImage = null;
    }
    // getters
    public String getPath() {
        return path;
    }
    public String getImage() {
        return image;
    }
    public String getAudio() {
        return audio;
    }
    public boolean[] getClientAnswers() {
        return clientAnswers;
    }
    public boolean[] getRealAnswers() {
        return realAnswers;
    }
    public String getSolutionAudio() {
        return solutionAudio;
    }
    public String getSolutionImage() {
        return solutionImage;
    }
    // setters
    public void setPath(String path) {
        this.path = path;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setAudio(String audio) {
        this.audio = audio;
    }
    public void setClientAnswers(boolean[] clientAnswers) {
        this.clientAnswers = clientAnswers;
    }
    public void setRealAnswers(boolean[] realAnswers) {
        this.realAnswers = realAnswers;
    }
    public void setSolutionAudio(String solutionAudio) {
        this.solutionAudio = solutionAudio;
    }
    public void setSolutionImage(String solutionImage) {
        this.solutionImage = solutionImage;
    }

    boolean checkScore() {
        for (int i = 0; i < clientAnswers.length; i++) {
            System.out.println("Client answer: " + clientAnswers[i] + ", Real answer: " + realAnswers[i]);
            if (clientAnswers[i] != realAnswers[i]) {
                return false;
            }
        }
        return true;
    }

    //lets create methods to load and return files according to the paths,
}
