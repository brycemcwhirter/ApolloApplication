package apollo.Swing;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import apollo.Controller;
import apollo.Objects.PNM;
import apollo.FileManager.*;
import apollo.Enum.Tier;

public class Button {

    public static void importDatabase(){
        Controller.mainPage();
        List<PNM> members = FileManager.importFile();
        PopupManager.createRushClass(members);

    }

    /** 
     * addNewPerson
     * 
     * This function creates and adds a new PNM to the rush class
     * 
     */
    public static void addNewPerson(){
        final JDialog popup = PopupManager.createPopup("Add Person");
        //Action listener for adding a person button
        PopupManager.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] temp = new String[PNM.getColumnNames().length];
                //Get information from the text fields
                for (int i = 0; i < PNM.getColumnNames().length; i++) {
                    temp[i] = PopupManager.getEditFields()[i].getText();
                }
                if (temp[4].equals("true"))
                	temp[4] = "false";
                if (!temp[7].equals("RED") && !temp[7].equals("GRAY") && !temp[7].equals("GREEN"))
                	temp[7] = "GRAY";
                PNM pnm = new PNM(temp[0], temp[1], temp[2], temp[3], Boolean.parseBoolean(temp[4]), Integer.parseInt(temp[5]), 
                        temp[6], Tier.valueOf(temp[7]));
                Controller.getMainRushClass().addMember(pnm);
                //Add data from text fields to table
                Controller.getModel().addRow(temp); 
                popup.setVisible(false);
                popup.dispose();
            }
            
        });

    }

    /** 
     * removePerson
     * 
     * This function removes the selected person from the rush class
     * 
     */
    public static void removePerson(){
        //Check to make sure that a row is selected
        if (Controller.getTable().getSelectedRow() != -1) {
            Object[] options = {"Yes", "No"};
            //Create a option pane to record response
            int n = JOptionPane.showOptionDialog(Controller.getMainFrame(),
                    "Are you sure you want to remove " + (Controller.getModel().getValueAt(Controller.getTable().getSelectedRow(), 0)) + "?",
                    "Delete Row?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (n == 0) {
                //Remove the person from the table and the rush class
                Controller.getMainRushClass().removePerson((String) Controller.getModel().getValueAt(Controller.getTable().getSelectedRow(), 0));
                Controller.getModel().removeRow(Controller.getTable().getSelectedRow());
            }
        }
    }
    
}
