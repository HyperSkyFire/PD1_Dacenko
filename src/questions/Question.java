package questions;


/**
 * @since 9:50_11.06.2024
 * @author Daniils_Dacenko_PR-21
 */
public class Question {
    int id;
    String type;
    String question;
    String[] correctAnswers;
    int[] wrongAnswers;
    int points;

    public Question(int id, String type, String question, String[] correctAnswers, int[] wrongAnswers, int points) {
        this.id = id;
        this.type = type;
        this.question = question;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.points = points;
    }

    public int getId() {
        return id;
    }
    
    
}
