package questions;


/**
 * @since 9:50_11.06.2024
 * @author Daniils_Dacenko_PR-21
 */
public class Question {
    private int id;
    private String type;
    private String question;
    private String[] answerlist;
    private int[] correctanswerlist;
    private int points;

    public Question(int id, String type, String question, String[] answerlist, int[] correctanswerlist, int points) {
        this.id = id;
        this.type = type;
        this.question = question;
        this.answerlist = answerlist;
        this.correctanswerlist = correctanswerlist;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public int[] getCorrectanswerlist() {
        return correctanswerlist;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswerlist() {
        return answerlist;
    }
    
    
}
