package Users;

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
                if (rs.getString(7) != null) {
                    String[] buf = rs.getString(7).split(" ");
                    testidlist = new int[buf.length];
                    for (int i = 0; i < buf.length; i++) {
                        testidlist[i] = Integer.parseInt(buf[i]);
                    }
                }
                int[] pointlist = new int[0];
                if (rs.getString(8) != null) {
                    String[] buf = rs.getString(8).split(" ");
                    pointlist = new int[buf.length];
                    for (int i = 0; i < buf.length; i++) {
                        pointlist[i] = Integer.parseInt(buf[i]);
                    }
                }
                switch (rights) {
                    case "Guest": {
                        userList.add(new Guest(id, rights, name, surname, nickname, password, testidlist, pointlist));
                        break;
                    }
                    case "Student": {
                        userList.add(new Student(id, rights, name, surname, nickname, password, testidlist, pointlist));
                        break;
                    }
                    case "Teacher": {
                        userList.add(new Teacher(id, rights, name, surname, nickname, password, testidlist, pointlist));
                        break;
                    }
                    case "Admin": {
                        userList.add(new Admin(id, rights, name, surname, nickname, password, testidlist, pointlist));
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

    public static User addUser(String name, String surname, String nickname, String password) {
        try {
            int id = 1 + userList.get(userList.size() - 1).getId();
            String nan = null;
            stat.executeUpdate("INSERT INTO APP.USERDATA VALUES (" + id + ",'Guest','" + name + "','" + surname + "','" + nickname + "','" + password + "'," + nan + "," + nan + ")");
            updateUserList();
            return getUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
