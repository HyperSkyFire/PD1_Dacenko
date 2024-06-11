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
    String since;
    String until;
    int time;

    public Test(int id, String name, int[] questionidlist, int pointcount, String since, String until, int time) {
        this.id = id;
        this.name = name;
        this.questionidlist = questionidlist;
        this.pointcount = pointcount;
        this.since = since;
        this.until = until;
        this.time = time;
        this.questionlist = new Question[questionidlist.length];
        for (int i = 0; i < questionlist.length; i++) {
            questionlist[i] = QuestionData.getQuestion(questionidlist[i]);
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
}