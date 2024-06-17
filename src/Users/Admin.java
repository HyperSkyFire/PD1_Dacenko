package Users;

import Tests.TestData;
import javax.swing.JRadioButton;
import static project.GUI.gui;
import questions.QuestionData;

/**
 * @since 9:50_11.06.2024
 * @author Daniils_Dacenko_PR-21
 */
public class Admin extends User {
    
    public Admin(int id, String rights, String name, String surname, String nickname, String password, int[] testlist, int[] pointlist, int[] attemptlist) {
        super(id, rights, name, surname, nickname, password, testlist, pointlist, attemptlist);
    }

    public void showUserData() {
        if (gui.jComboBox4.getSelectedIndex() != -1) {
            User user = UserData.getUserList()[gui.jComboBox4.getSelectedIndex()];
            gui.jTextField10.setText(user.getName());
            gui.jTextField11.setText(user.getSurname());
            switch (user.getRights()) {
                case "Guest": {
                    gui.jComboBox6.setSelectedIndex(0);
                    break;
                }
                case "Student": {
                    gui.jComboBox6.setSelectedIndex(1);
                    break;
                }
                case "Teacher": {
                    gui.jComboBox6.setSelectedIndex(2);
                    break;
                }
                case "Admin": {
                    gui.jComboBox6.setSelectedIndex(3);
                    break;
                }
            }
        }
    }

    public void updateUserData() {
        if (gui.jTextField10.getText() != null && gui.jTextField11.getText() != null) {
            User user = UserData.getUserList()[gui.jComboBox4.getSelectedIndex()];
            String name = gui.jTextField10.getText();
            String surname = gui.jTextField11.getText();
            String rights = "";
            switch (gui.jComboBox6.getSelectedIndex()) {
                case 0: {
                    rights = "Guset";
                    break;
                }
                case 1: {
                    rights = "Student";
                    break;
                }
                case 2: {
                    rights = "Teacher";
                    break;
                }
                case 3: {
                    rights = "Admin";
                    break;
                }
            }
            UserData.updateUser(user.getId(), rights, name, surname);
        } else {
            gui.jTextPane2.setText("Ne visi lauki ir aizpilditi");
            gui.jDialog2.setVisible(true);
        }
    }

    public void deleteUser() {
        User user = UserData.getUserList()[gui.jComboBox4.getSelectedIndex()];
        UserData.deleteUser(user.getId());
    }

    
    public void startTestRedactoring() {
        currentQuestionNum = 0;
        openQestionRedactorLog(currentQuestionNum);
        gui.jDialog8.setVisible(false);
    }

    public void nextQuestionRedactorLog() {
        currentQuestionNum++;
        openQestionRedactorLog(currentQuestionNum);
    }

    public void previousQuestionRedactorLog() {
        currentQuestionNum--;
        openQestionRedactorLog(currentQuestionNum);
    }

    public void saveChangedQuestion() throws NumberFormatException {
        int id = gui.test.getQuestionlist()[currentQuestionNum].getId();
        String type = gui.test.getQuestionlist()[currentQuestionNum].getType();
        String question = gui.jTextArea5.getText();
        String[] answerlist = {gui.jTextField3.getText(), gui.jTextField7.getText(), gui.jTextField8.getText(), gui.jTextField9.getText()};
        int[] correctanswerlist = new int[1];
        boolean[] buf = {gui.jRadioButton5.isSelected(), gui.jRadioButton6.isSelected(), gui.jRadioButton7.isSelected(), gui.jRadioButton8.isSelected()};
        for (int i = 0; i < 4; i++) {
            if (buf[i]) {
                correctanswerlist[0] = i + 1;
            }
        }
        int points = Integer.parseInt(gui.jTextArea8.getText());
        QuestionData.updateQuestion(id, type, question, answerlist, correctanswerlist, points);
        gui.test = TestData.getTest(gui.test.getId());
    }

    public void leftFromQuestionRedactor() {
        gui.jTabbedPane1.setSelectedComponent(gui.jPanel37);
    }
    
    public void deleteTest() {
        for (gui.currentTestNum = 0; gui.currentTestNum < TestData.getTestList().length; gui.currentTestNum++) {
            if (gui.jComboBox5.getSelectedItem().equals(TestData.getTestList()[gui.currentTestNum].getName())) {
                gui.test = TestData.getTestList()[gui.currentTestNum];
                break;
            }
        }
        TestData.deleteTest(gui.test.getId());
        UserData.removeTestFromAllUserTestIDList(gui.test.getId());
        for (int i = 0; i < gui.test.getQuestionidlist().length; i++) {
            QuestionData.deleteQuestion(gui.test.getQuestionidlist()[i]);
        }
        gui.update(gui.user.getId());
    }

    public void openQestionRedactorLog(int num) {
        switch (gui.test.getQuestionlist()[num].getType()) {
            case "radio": {
                gui.buttonGroup1.clearSelection();
                if (num == 0) {
                    gui.jButton56.setVisible(true);
                    gui.jButton57.setVisible(false);
                } else if (num + 1 >= gui.test.getQuestionlist().length) {
                    gui.jButton56.setVisible(false);
                    gui.jButton57.setVisible(true);
                } else {
                    gui.jButton56.setVisible(true);
                    gui.jButton57.setVisible(true);
                }
                gui.jTabbedPane1.setSelectedComponent(gui.jPanel32);
                gui.jLabel43.setText(gui.test.getName());
                gui.jTextArea5.setText(gui.test.getQuestionlist()[num].getQuestion());
                gui.jLabel13.setText((num + 1) + "");
                gui.jTextField3.setText(gui.test.getQuestionlist()[num].getAnswerlist()[0]);
                gui.jTextField7.setText(gui.test.getQuestionlist()[num].getAnswerlist()[1]);
                gui.jTextField8.setText(gui.test.getQuestionlist()[num].getAnswerlist()[2]);
                gui.jTextField9.setText(gui.test.getQuestionlist()[num].getAnswerlist()[3]);
                gui.jTextArea8.setText(gui.test.getQuestionlist()[num].getPoints() + "");
                JRadioButton[] radioButtons = {gui.jRadioButton5, gui.jRadioButton6, gui.jRadioButton7, gui.jRadioButton8};
                radioButtons[gui.test.getQuestionlist()[num].getCorrectanswerlist()[0] - 1].setSelected(true);
                break;
            }
            case "text": {

                break;
            }
            case "check": {

                break;
            }
            case "combo": {

                break;
            }
        }
    }

    public void closeAcces() {
        for (gui.currentTestNum = 0; gui.currentTestNum < TestData.getTestList().length; gui.currentTestNum++) {
            if (gui.jComboBox5.getSelectedItem().equals(TestData.getTestList()[gui.currentTestNum].getName())) {
                
                gui.test = TestData.getTestList()[gui.currentTestNum];
                break;
            }
        }
        if (gui.test.getAttemptcount() != 0) {
            TestData.updateTestAttemptList(gui.test.getId(), 0);
            UserData.updateUseListrAttemptList(gui.test.getId(), 0);
            gui.update(gui.user.getId());

        } else {
            gui.jTextPane2.setText("Tests jau slegts");
            gui.jDialog2.setVisible(true);
        }
    }

    public void openAccess() {
        for (gui.currentTestNum = 0; gui.currentTestNum < TestData.getTestList().length; gui.currentTestNum++) {
            if (gui.jComboBox5.getSelectedItem().equals(TestData.getTestList()[gui.currentTestNum].getName())) {
                
                gui.test = TestData.getTestList()[gui.currentTestNum];
                break;
            }
        }
        if (gui.test.getAttemptcount() == 0) {
            gui.jDialog10.setVisible(true);
        } else {
            gui.jTextPane2.setText("Tests jau atrverts");
            gui.jDialog2.setVisible(true);
        }
    }

    public void setAttemptCount() {
        if ((int) (gui.jSpinner1.getValue()) > 0) {
            TestData.updateTestAttemptList(gui.test.getId(), (int) (gui.jSpinner1.getValue()));
            UserData.updateUseListrAttemptList(gui.test.getId(), (int) (gui.jSpinner1.getValue()));
            gui.update(gui.user.getId());
        } else {
            gui.jTextPane2.setText("Jautajumu skaits var\nbut tikai vairak neka 0");
            gui.jDialog2.setVisible(true);
        }
    }
    
    public void removeAccess() {
        int[] testidlist = UserData.getStudentList()[gui.jComboBox3.getSelectedIndex()].getTestidlist();
        boolean exists = false;
        for (int i = 0; i < testidlist.length; i++) {
            if (testidlist[i] == gui.test.getId()) {
                exists = true;
                break;
            }
        }
        if (exists) {
            UserData.removeTestIDFromUserTestIDList(UserData.getStudentList()[gui.jComboBox3.getSelectedIndex()].getId(), gui.test.getId());
        } else {
            gui.jTextPane2.setText("Piekļuve ir jau noņemta!");
            gui.jDialog2.setVisible(true);
        }
    }

    public void addAccess() {
        int[] testidlist = UserData.getStudentList()[gui.jComboBox3.getSelectedIndex()].getTestidlist();
        boolean exists = false;
        for (int i = 0; i < testidlist.length; i++) {
            if (testidlist[i] == gui.test.getId()) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            UserData.addTestIDIntoUserTestIDList(UserData.getStudentList()[gui.jComboBox3.getSelectedIndex()].getId(), gui.test.getId());
            testidlist = UserData.getStudentList()[gui.jComboBox3.getSelectedIndex()].getTestidlist();
            int[] attemptlist = UserData.getStudentList()[gui.jComboBox3.getSelectedIndex()].getAttemptlist();
            for (int i = 0; i < testidlist.length; i++) {
                if (testidlist[i] == gui.test.getId()) {
                    attemptlist[i] = gui.test.getAttemptcount();
                    UserData.updateUserAttemptList(UserData.getStudentList()[gui.jComboBox3.getSelectedIndex()].getId(), attemptlist);
                }
            }
        } else {
            gui.jTextPane2.setText("Piekļuve ir jau piešķirta!");
            gui.jDialog2.setVisible(true);
        }
    }
    
    public void changeAcces() {
        for (gui.currentTestNum = 0; gui.currentTestNum < TestData.getTestList().length; gui.currentTestNum++) {
            if (gui.jComboBox5.getSelectedItem().equals(TestData.getTestList()[gui.currentTestNum].getName())) {
                gui.test = TestData.getTestList()[gui.currentTestNum];
                break;
            }
        }
        gui.jComboBox3.removeAllItems();
        User[] studentlist = UserData.getStudentList();
        for (int i = 0; i < studentlist.length; i++) {
            gui.jComboBox3.addItem(studentlist[i].getName());
        }
        gui.jDialog9.setVisible(true);
    }

}
