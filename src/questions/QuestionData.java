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

    public static Question getQuestion(int id) {
        updateQuestionList();
        for (int i = 0; i < questionList.size(); i++) {
            if (questionList.get(i).getId() == id) {
                return questionList.get(i);
            }
        }
        return null;
    }
}
