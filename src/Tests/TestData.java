package Tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import questions.QuestionData;

/**
 * @since 16:00_11.06.2024
 * @author Daniils_Dacenko_PR-21
 */
public class TestData {

    private static Connection con;
    private static Statement stat;
    private static ResultSet rs = null;

    private static ArrayList<Test> testList;

    public static void start() {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/UserDB", "nbuser", "nbuser");
            stat = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stat.executeQuery("SELECT * FROM APP.TESTDATA");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static Test[] getTestList() {
        Test[] testlist = new Test[testList.size()];
        for (int i = 0; i < testlist.length; i++) {
            testlist[i] = testList.get(i);
        }
        return testlist;
    }

    public static void updateTestList() {
        start();
        testList = new ArrayList<Test>();
        try {
            rs.beforeFirst();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String[] buf = rs.getString(3).split(" ");
                int[] questionidlist = new int[buf.length];
                int pointcount = 0;
                for (int i = 0; i < buf.length; i++) {
                    questionidlist[i] = Integer.parseInt(buf[i]);
                    pointcount += QuestionData.getQuestion(questionidlist[i]).getPoints();
                }
                int time = rs.getInt(5);
                int attemptcount = rs.getInt(6);
                Statement stat = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stat.executeUpdate("UPDATE APP.TESTDATA SET POINTCOUNT = " + pointcount + " WHERE ID = " + id);
                testList.add(new Test(id, name, questionidlist, pointcount, time, attemptcount));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteTest(int id) {
        updateTestList();
        try {
            for (int i = 0; i < testList.size(); i++) {
                if (testList.get(i).getId() == id) {
                    stat.execute("DELETE FROM APP.TESTDATA WHERE ID = " + id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateTestAttemptList(int id, int attemptcount) {
        try {
            stat.executeUpdate("UPDATE APP.TESTDATA SET ATTEMPTCOUNT = " + attemptcount + " WHERE ID = " + id);
            updateTestList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Test getTest(int id) {
        updateTestList();
        for (int i = 0; i < testList.size(); i++) {
            if (testList.get(i).getId() == id) {
                return testList.get(i);
            }
        }
        return null;
    }

    public static Test addTest(String name, int[] questionidlist, int time) {
        try {
            String buf = "";
            for (int i = 0; i < questionidlist.length; i++) {
                buf += questionidlist[i] + " ";
            }
            stat.executeUpdate("INSERT INTO APP.TESTDATA (NAME, QUESTIONIDLIST, TIME) VALUES ('" + name + "','" + buf + "'," + time + ")");
            updateTestList();
            return testList.get(testList.size() - 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

/*
CREATE TABLE "TESTDATA"
(
    "ID" INT NOT NULL PRIMARY KEY
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
    "NAME" VARCHAR(50),
    "QUESTIONIDLIST" VARCHAR(150),
    "POINTCOUNT" INT DEFAULT 0,
    "TIME" INT,
    "ATTEMPTCOUNT" INT DEFAULT 0
)
*/
