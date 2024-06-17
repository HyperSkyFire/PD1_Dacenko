package Users;

/**
 * @since 9:50_11.06.2024
 * @author Daniils_Dacenko_PR-21
 */
public class Guest extends User {

    public Guest(int id, String rights, String name, String surname, String nickname, String password, int[] testlist, int[] pointlist, int[] attemptlist) {
        super(id, rights, name, surname, nickname, password, testlist, pointlist, attemptlist);
    }

}
