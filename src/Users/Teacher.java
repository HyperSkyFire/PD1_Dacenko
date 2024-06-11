package Users;

/**
 * @since 8:10_24.04.2024
 * @author Daniils_Dacenko_PR-21
 */
public class Teacher extends User {

    public Teacher(int id, String rights, String name, String surname, String nickname, String password, int[] testlist, int[] pointlist) {
        super(id, rights, name, surname, nickname, password, testlist, pointlist);
    }

}
