package Users;

import project.GUI;
import static project.GUI.currentTime;
import static project.GUI.gui;

/**
 * @since 9:50_11.06.2024
 * @author Daniils_Dacenko_PR-21
 */
public class Student extends User {
    

    public Student(int id, String rights, String name, String surname, String nickname, String password, int[] testlist, int[] pointlist, int[] attemptlist) {
        super(id, rights, name, surname, nickname, password, testlist, pointlist, attemptlist);
    }
    
    public void openStudentTestLog() {
        if (!gui.jList1.isSelectionEmpty()) {
            for (gui.currentTestNum = 0; gui.currentTestNum < gui.user.getTestlist().length; gui.currentTestNum++) {
                if (gui.jList1.getSelectedValue().equals(gui.user.getTestlist()[gui.currentTestNum].getName())) {
                    gui.test = gui.user.getTestlist()[gui.currentTestNum];
                    break;
                }
            }
            gui.jDialog3.setVisible(true);
            gui.jLabel23.setText(gui.test.getName());
            gui.jLabel24.setText((gui.test.getTime() % 60 == 0) ? (gui.test.getTime() / 60) + ":00" : (gui.test.getTime() / 60) + ":" + (gui.test.getTime() % 60));
            gui.jLabel25.setText("Jautajumu skaits: " + gui.test.getQuestionlist().length);
            gui.jLabel26.setText("Punki: " + gui.user.getPointlist()[gui.currentTestNum] + " / " + gui.test.getPointcount());
            gui.jLabel31.setText("Meginajumu skaits: " + gui.user.getAttemptlist()[gui.currentTestNum] + " / " + gui.test.getAttemptcount());
        } else {
            gui.jTextPane2.setText("Izveleties testu");
            gui.jDialog2.setVisible(true);
        }
    }
    
    public void nextQuestionLog() {
        boolean[] radio = {gui.jRadioButton1.isSelected(), gui.jRadioButton2.isSelected(), gui.jRadioButton3.isSelected(), gui.jRadioButton4.isSelected()};
        gui.radioList[currentQuestionNum] = radio;
        if (radio[gui.test.getQuestionlist()[currentQuestionNum].getCorrectanswerlist()[0] - 1]) {
            gui.currentTestPoints[currentQuestionNum] = gui.test.getQuestionlist()[currentQuestionNum].getPoints();
        } else {
            gui.currentTestPoints[currentQuestionNum] = 0;
        }
        if (GUI.startTimer) {
            if (currentQuestionNum + 1 < gui.test.getQuestionlist().length) {
                currentQuestionNum++;
                gui.radioButtonGroup.clearSelection();
                openQestionLog(currentQuestionNum);
            } else {
                gui.jDialog5.setVisible(true);
            }
        } else {
            gui.jDialog7.setVisible(true);
        }
        gui.jRadioButton1.setSelected(gui.radioList[currentQuestionNum][0]);
        gui.jRadioButton2.setSelected(gui.radioList[currentQuestionNum][1]);
        gui.jRadioButton3.setSelected(gui.radioList[currentQuestionNum][2]);
        gui.jRadioButton4.setSelected(gui.radioList[currentQuestionNum][3]);
    }
    
    public void previousQuestionLog() {
        boolean[] radio = {gui.jRadioButton1.isSelected(), gui.jRadioButton2.isSelected(), gui.jRadioButton3.isSelected(), gui.jRadioButton4.isSelected()};
        gui.radioList[currentQuestionNum] = radio;
        if (radio[gui.test.getQuestionlist()[currentQuestionNum].getCorrectanswerlist()[0] - 1]) {
            gui.currentTestPoints[currentQuestionNum] = gui.test.getQuestionlist()[currentQuestionNum].getPoints();
        } else {
            gui.currentTestPoints[currentQuestionNum] = 0;
        }
        if (GUI.startTimer) {
            if (currentQuestionNum > 0) {
                currentQuestionNum--;
                gui.radioButtonGroup.clearSelection();
                openQestionLog(currentQuestionNum);
            } else {
                gui.jDialog4.setVisible(true);
            }
        } else {
            gui.jDialog7.setVisible(true);
        }
        gui.jRadioButton1.setSelected(gui.radioList[currentQuestionNum][0]);
        gui.jRadioButton2.setSelected(gui.radioList[currentQuestionNum][1]);
        gui.jRadioButton3.setSelected(gui.radioList[currentQuestionNum][2]);
        gui.jRadioButton4.setSelected(gui.radioList[currentQuestionNum][3]);
    }
    
    public void openQestionLog(int num) {
        switch (gui.test.getQuestionlist()[num].getType()) {
            case "radio": {
                gui.jTabbedPane1.setSelectedComponent(gui.jPanel17);
                gui.jLabel11.setText(gui.test.getName());
                gui.jTextArea1.setText(gui.test.getQuestionlist()[num].getQuestion());
                gui.jLabel13.setText((num + 1) + "");
                gui.jRadioButton1.setText(gui.test.getQuestionlist()[num].getAnswerlist()[0]);
                gui.jRadioButton2.setText(gui.test.getQuestionlist()[num].getAnswerlist()[1]);
                gui.jRadioButton3.setText(gui.test.getQuestionlist()[num].getAnswerlist()[2]);
                gui.jRadioButton4.setText(gui.test.getQuestionlist()[num].getAnswerlist()[3]);
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
    
    public boolean startTest() {
        if (gui.user.getAttemptlist()[gui.currentTestNum] > 0) {
            currentQuestionNum = 0;
            gui.currentTestPointsSum = 0;
            GUI.startTimer = true;
            int[] attemptlist = gui.user.getAttemptlist();
            attemptlist[gui.currentTestNum]--;
            UserData.updateUserAttemptList(gui.user.getId(), attemptlist);
            gui.update(gui.user.getId());
            currentTime = gui.test.getTime();
            gui.radioList = new boolean[gui.test.getQuestionlist().length][4];
            gui.currentTestPoints = new int[gui.test.getQuestionlist().length];
            gui.correctAnswers = new boolean[gui.test.getQuestionlist().length];
            openQestionLog(currentQuestionNum);
            return true;
        } else {
            gui.jTextPane2.setText("Mēģinājumu skaits ir beidzies!");
            gui.jDialog2.setVisible(true);
            return false;
        }
    }


    public void doneTest() {
        for (int i = 0; i < gui.currentTestPoints.length; i++) {
            gui.currentTestPointsSum += gui.currentTestPoints[i];
        }
        gui.jDialog6.setVisible(true);
        gui.jLabel27.setText(gui.test.getName());
        gui.jLabel28.setText((gui.test.getTime() % 60 == 0) ? (gui.test.getTime() / 60) + ":00" : (gui.test.getTime() / 60) + ":" + (gui.test.getTime() % 60));
        gui.jLabel29.setText("Punkti " + gui.currentTestPointsSum + " no " + gui.test.getPointcount());
        gui.jLabel30.setText("Procenti " + Math.round((double) (gui.currentTestPointsSum) / (double) (gui.test.getPointcount()) * 100) + "%");
        gui.radioButtonGroup.clearSelection();
        gui.jTabbedPane1.setSelectedComponent(gui.jPanel10);
    }

    public void saveTestResults() {
        int[] pointlist = gui.user.getPointlist();
        pointlist[gui.currentTestNum] = gui.currentTestPointsSum;
        UserData.updateUserPointList(gui.user.getId(), pointlist);
        gui.update(gui.user.getId());
        gui.jDialog6.setVisible(false);
    }
    
    
}
