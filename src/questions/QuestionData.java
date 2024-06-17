/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package questions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @since 21:00_11.06.2024
 * @author Daniils_Dacenko_PR-21
 */
public class QuestionData {

    private static Connection con;
    private static Statement stat;
    private static ResultSet rs = null;

    private static ArrayList<Question> questionList;

    public static void start() {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/UserDB", "nbuser", "nbuser");
            stat = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stat.executeQuery("SELECT * FROM APP.QUESTIONDATA");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateQuestionList() {
        start();
        questionList = new ArrayList<Question>();
        try {
            rs.beforeFirst();
            while (rs.next()) {
                int id = rs.getInt(1);
                String type = rs.getString(2);
                String question = rs.getString(3);
                String[] answerlist = rs.getString(4).split(";");
                int[] correctansweridlist = new int[0];
                String[] buf = rs.getString(5).split(" ");
                correctansweridlist = new int[buf.length];
                for (int i = 0; i < buf.length; i++) {
                    correctansweridlist[i] = Integer.parseInt(buf[i]);
                }
                int points = rs.getInt(6);
                questionList.add(new Question(id, type, question, answerlist, correctansweridlist, points));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteQuestion(int id) {
        try {
            for (int i = 0; i < questionList.size(); i++) {
                if (questionList.get(i).getId() == id) {
                    stat.execute("DELETE FROM APP.QUESTIONDATA WHERE ID = " + id);
                }
            }
            updateQuestionList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Question getQuestion(int id) {
        updateQuestionList();
        for (int i = 0; i < questionList.size(); i++) {
            if (questionList.get(i).getId() == id) {
                return questionList.get(i);
            }
        }
        return null;
    }

    public static void updateQuestion(int id, String type, String question, String[] answerlist, int[] correctansweridlist, int points) {
        try {
            String buf1 = "";
            for (int i = 0; i < answerlist.length; i++) {
                buf1 += answerlist[i] + ";";
            }
            String buf2 = "";
            for (int i = 0; i < correctansweridlist.length; i++) {
                buf2 += correctansweridlist[i] + " ";
            }
            stat.executeUpdate("UPDATE APP.QUESTIONDATA SET TYPE = '" + type + "', QUESTION = '" + question + "', ANSWERLIST = '" + buf1 + "', CORRECTANSWERIDLIST = '" + buf2 + "', POINTS = " + points + " WHERE ID = " + id);
            updateQuestionList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Question addQuestion(String type, String question, String[] answerlist, int[] correctansweridlist, int points) {
        try {
            String buf1 = "";
            for (int i = 0; i < answerlist.length; i++) {
                buf1 += answerlist[i] + ";";
            }
            String buf2 = "";
            for (int i = 0; i < correctansweridlist.length; i++) {
                buf2 += correctansweridlist[i] + " ";
            }
            stat.executeUpdate("INSERT INTO APP.QUESTIONDATA (TYPE, QUESTION, ANSWERLIST, CORRECTANSWERIDLIST, POINTS) VALUES ('" + type + "','" + question + "','" + buf1 + "','" + buf2 + "'," + points + ")");
            updateQuestionList();
            return questionList.get(questionList.size() - 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
/*
CREATE TABLE "QUESTIONDATA"
(
    "ID" INT NOT NULL PRIMARY KEY
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
    "TYPE" VARCHAR(20),
    "QUESTION" VARCHAR(100),
    "ANSWERLIST" VARCHAR(400),
    "CORRECTANSWERIDLIST" VARCHAR(20),
    "POINTS" INT
)
 */
