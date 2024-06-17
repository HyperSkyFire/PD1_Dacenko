package Tests;

import questions.Question;
import questions.QuestionData;


/**
 * @since 9:50_11.06.2024
 * @author Daniils_Dacenko_PR-21
 */
public class Test {

    int id;
    String name;
    int[] questionidlist;
    Question[] questionlist;
    int pointcount;
    int time;
    int attemptcount;

    public Test(int id, String name, int[] questionidlist, int pointcount, int time, int attemptcount) {
        this.id = id;
        this.name = name;
        this.questionidlist = questionidlist;
        this.pointcount = pointcount;
        this.time = time;
        this.attemptcount = attemptcount;
        this.questionlist = new Question[questionidlist.length];
        for (int i = 0; i < questionlist.length; i++) {
            questionlist[i] = QuestionData.getQuestion(questionidlist[i]);
        }
    }

    public int[] getQuestionidlist() {
        return questionidlist;
    }

    public int getAttemptcount() {
        return attemptcount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public int getPointcount() {
        return pointcount;
    }

    public Question[] getQuestionlist() {
        return questionlist;
    }
}