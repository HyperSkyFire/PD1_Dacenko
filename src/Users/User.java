package Users;

/**
 *
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

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getRights() {
        return rights;
    }

    public User(int id, String rights, String name, String surname, String nickname, String password, int[] testlist, int[] pointlist) {
        this.id = id;
        this.rights = rights;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.password = password;
        this.testidlist = testlist;
        this.pointlist = pointlist;
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
}
