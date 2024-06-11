package Tests;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

    public static void updateTestList() {
        start();
        testList = new ArrayList<Test>();
        try {
            rs.beforeFirst();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int[] questionidlist = new int[0];
                String[] buf = rs.getString(3).split(" ");
                questionidlist = new int[buf.length];
                for (int i = 0; i < buf.length; i++) {
                    questionidlist[i] = Integer.parseInt(buf[i]);
                }
                int pointcount = rs.getInt(4);
                String since = rs.getString(5);
                String until = rs.getString(6);
                int time = rs.getInt(7);
                testList.add(new Test(id, name, questionidlist, pointcount, since, until, time));

            }
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

    public static Test addTest(String name, int[] questionidlist, String since, String until, int time) {
        try {
            int id = 1 + testList.get(testList.size() - 1).getId();
            int pointcount = 0;
            stat.executeUpdate("INSERT INTO APP.TESTDATA VALUES (" + id + ",'" + name + "','" + questionidlist + "'," + pointcount + ",'" + since + "','" + until + "'," + time + ")");
            updateTestList();
            return getTest(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
