package Users;

import Tests.TestData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @since 9:50_11.06.2024
 * @author Daniils_Dacenko_PR-21
 */
public class UserData {

    private static Connection con;
    private static Statement stat;
    private static ResultSet rs = null;

    private static ArrayList<User> userList;

    public static void start() {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/UserDB", "nbuser", "nbuser");
            stat = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stat.executeQuery("SELECT * FROM APP.USERDATA");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User[] getUserList() {
        User[] userlist = new User[userList.size()];
        for (int i = 0; i < userlist.length; i++) {
            userlist[i] = userList.get(i);
        }
        return userlist;
    }

    public static User[] getStudentList() {
        ArrayList<User> studentList = new ArrayList<User>();
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getRights().equals("Student")) {
                studentList.add(userList.get(i));
            }
        }
        User[] studentlist = new User[studentList.size()];
        for (int i = 0; i < studentlist.length; i++) {
            studentlist[i] = studentList.get(i);
        }
        return studentlist;
    }

    public static void updateUserList() {
        start();
        userList = new ArrayList<User>();
        try {
            rs.beforeFirst();
            while (rs.next()) {
                int id = rs.getInt(1);
                String rights = rs.getString(2);
                String name = rs.getString(3);
                String surname = rs.getString(4);
                String nickname = rs.getString(5);
                String password = rs.getString(6);
                int[] testidlist = new int[0];
                if (rs.getString(7) != null && rs.getString(7).length() != 0) {
                    String[] buf = rs.getString(7).split(" ");
                    testidlist = new int[buf.length];
                    for (int i = 0; i < buf.length; i++) {
                        testidlist[i] = Integer.parseInt(buf[i]);
                    }
                }
                int[] pointlist = new int[0];
                if (rs.getString(8) != null && rs.getString(8).length() != 0) {
                    String[] buf = rs.getString(8).split(" ");
                    pointlist = new int[buf.length];
                    for (int i = 0; i < buf.length; i++) {
                        pointlist[i] = Integer.parseInt(buf[i]);
                    }
                }
                int[] attemptlist = new int[0];
                if (rs.getString(9) != null && rs.getString(9).length() != 0) {
                    String[] buf = rs.getString(9).split(" ");
                    attemptlist = new int[buf.length];
                    for (int i = 0; i < buf.length; i++) {
                        attemptlist[i] = Integer.parseInt(buf[i]);
                    }
                }
                switch (rights) {
                    case "Guest": {
                        userList.add(new Guest(id, rights, name, surname, nickname, password, testidlist, pointlist, attemptlist));
                        break;
                    }
                    case "Student": {
                        userList.add(new Student(id, rights, name, surname, nickname, password, testidlist, pointlist, attemptlist));
                        break;
                    }
                    case "Teacher": {
                        userList.add(new Teacher(id, rights, name, surname, nickname, password, testidlist, pointlist, attemptlist));
                        break;
                    }
                    case "Admin": {
                        userList.add(new Admin(id, rights, name, surname, nickname, password, testidlist, pointlist, attemptlist));
                        break;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int isUserExists(String nickname, String password) {
        int isCorrect = -2;
        for (int i = 0; i < userList.size(); i++) {
            if (nickname.equals(userList.get(i).getNickname())) {
                if (password.equals(userList.get(i).getPassword())) {
                    isCorrect = userList.get(i).getId();
                } else {
                    isCorrect = -1;
                }
            }
        }

        return isCorrect;
    }

    public static User getUser(int id) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getId() == id) {
                return userList.get(i);
            }
        }
        return null;
    }

    public static void addTestIDIntoUserTestIDList(int id, int testid) {
        try {
            int[] testidlist = getUser(id).getTestidlist();
            int[] pointlist = getUser(id).getPointlist();
            int[] attemptlist = getUser(id).getAttemptlist();
            int[] newtestidlist = new int[testidlist.length + 1];
            int[] newpointlist = new int[pointlist.length + 1];
            int[] newattemptlist = new int[attemptlist.length + 1];
            newtestidlist[0] = testid;
            newpointlist[0] = 0;
            newattemptlist[0] = 0;
            System.arraycopy(testidlist, 0, newtestidlist, 1, testidlist.length);
            System.arraycopy(pointlist, 0, newpointlist, 1, pointlist.length);
            System.arraycopy(attemptlist, 0, newattemptlist, 1, attemptlist.length);
            String buf1 = "", buf2 = "", buf3 = "";
            for (int i = 0; i < newtestidlist.length; i++) {
                buf1 += newtestidlist[i] + " ";
                buf2 += newpointlist[i] + " ";
                buf3 += newattemptlist[i] + " ";
            }
            stat.executeUpdate("UPDATE APP.USERDATA SET TESTIDLIST = '" + buf1 + "', POINTLIST = '" + buf2 + "', ATTEMPTLIST = '" + buf3 + "' WHERE ID = " + id);
            updateUserList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeTestIDFromUserTestIDList(int id, int testid) {
        try {
            int[] testidlist = getUser(id).getTestidlist();
            int[] pointlist = getUser(id).getPointlist();
            int[] attemptlist = getUser(id).getAttemptlist();
            if (testidlist.length != 0) {
                int[] newtestidlist = new int[testidlist.length - 1];
                int[] newpointlist = new int[pointlist.length - 1];
                int[] newattemptlist = new int[attemptlist.length - 1];
                for (int i = 0, j = 0; i < testidlist.length; i++) {
                    if (testidlist[i] != testid) {
                        newtestidlist[j] = testidlist[i];
                        newpointlist[j] = pointlist[i];
                        newattemptlist[j] = attemptlist[i];
                        j++;
                    }
                }
                String buf1 = "", buf2 = "", buf3 = "";
                for (int i = 0; i < newtestidlist.length; i++) {
                    buf1 += newtestidlist[i] + " ";
                    buf2 += newpointlist[i] + " ";
                    buf3 += newattemptlist[i] + " ";
                }
                stat.executeUpdate("UPDATE APP.USERDATA SET TESTIDLIST = '" + buf1 + "', POINTLIST = '" + buf2 + "', ATTEMPTLIST = '" + buf3 + "' WHERE ID = " + id);
                updateUserList();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User updateUserPointList(int id, int[] pointlist) {
        try {
            String buf = "";
            for (int i = 0; i < pointlist.length; i++) {
                buf += pointlist[i] + " ";
            }
            stat.executeUpdate("UPDATE APP.USERDATA SET POINTLIST = '" + buf + "' WHERE ID = " + id);
            updateUserList();
            return getUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User updateUserAttemptList(int id, int[] attemptlist) {
        try {
            String buf = "";
            for (int i = 0; i < attemptlist.length; i++) {
                buf += attemptlist[i] + " ";
            }
            stat.executeUpdate("UPDATE APP.USERDATA SET ATTEMPTLIST = '" + buf + "' WHERE ID = " + id);
            updateUserList();
            return getUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User addUser(String name, String surname, String nickname, String password) {
        try {
            stat.executeUpdate("INSERT INTO APP.USERDATA (RIGHTS, NAME, SURNAME, NICKNAME, PASSWORD) VALUES ('Guest','" + name + "','" + surname + "','" + nickname + "','" + password + "')");
            updateUserList();
            return userList.get(userList.size() - 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void deleteUser(int id) {
        try {
            stat.executeUpdate("DELETE FROM APP.USERDATA WHERE ID = " + id);
            updateUserList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeTestFromAllUserTestIDList(int testid) {
        for (int i = 0; i < userList.size(); i++) {
            removeTestIDFromUserTestIDList(userList.get(i).getId(), testid);
        }
    }

    public static void updateUseListrAttemptList(int testid, int attemptcount) {
        for (int i = 0; i < userList.size(); i++) {
            int[] testidlist = userList.get(i).getTestidlist();
            int[] attemptlist = userList.get(i).getAttemptlist();
            int[] newattemptlist = new int[attemptlist.length];
            for (int j = 0; j < testidlist.length; j++) {
                if (testidlist[j] != testid) {
                    newattemptlist[j] = attemptlist[j];
                } else {
                    newattemptlist[j] = attemptcount;
                }
            }
            updateUserAttemptList(userList.get(i).getId(), newattemptlist);
        }
    }

    public static void updateUser(int id, String rights, String name, String surname) {
        try {
            stat.executeUpdate("UPDATE APP.USERDATA SET RIGHTS = '" + rights + "', NAME = '" + name + "', SURNAME = '" + rights + "' WHERE ID = " + id);
            updateUserList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

/*
CREATE TABLE "USERDATA"
(
    "ID" INT NOT NULL PRIMARY KEY
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),
    "RIGHTS" VARCHAR(20),
    "NAME" VARCHAR(50),
    "SURNAME" VARCHAR(50),
    "NICKNAME" VARCHAR(50) UNIQUE,
    "PASSWORD" VARCHAR(50),
    "TESTIDLIST" VARCHAR(100) DEFAULT '',
    "POINTLIST" VARCHAR(100) DEFAULT '',
    "ATTEMPTLIST" VARCHAR(100) DEFAULT ''
)
 */
