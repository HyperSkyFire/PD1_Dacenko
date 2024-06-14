package Users;

import Tests.Test;
import Tests.TestData;

/**
 * @since 9:50_11.06.2024
 * @author User
 */
public class User {

    private int id;
    private String rights;
    private String name;
    private String surname;
    private String nickname;
    private String password;
    private int[] testidlist;
    private int[] pointlist;
    private Test[] testlist;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getRights() {
        return rights;
    }

    public User(int id, String rights, String name, String surname, String nickname, String password, int[] testidlist, int[] pointlist) {
        this.id = id;
        this.rights = rights;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.password = password;
        this.testidlist = testidlist;
        this.pointlist = pointlist;
        this.testlist = new Test[testidlist.length];
        for (int i = 0; i < testidlist.length; i++) {
            testlist[i] = TestData.getTest(testidlist[i]);
        }
        
    }

    public Test[] getTestlist() {
        return testlist;
    }
    
    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public int[] getPointlist() {
        return pointlist;
    }

    public int[] getTestidlist() {
        return testidlist;
    }
    
}
