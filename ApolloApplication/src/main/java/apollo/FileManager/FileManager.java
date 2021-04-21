package apollo.FileManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import apollo.Controller;
import apollo.Log;
import apollo.Objects.PNM;
import apollo.Enum.Tier;

public class FileManager {

    /** 
     * @return List<PNM>
     */
    public static List<PNM> importFile() {
    	JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int returnVal = fc.showOpenDialog(fc);
        List<PNM> members = new ArrayList<PNM>();

        //Check if file is opened
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));

                String line = null;
                //Parse through file
                while ((line = reader.readLine()) != null) {
                    String temp[] = line.split(",");

                    
                    //Add person to the table
                    Controller.getModel().addRow(new Object[] { temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6],
                            temp[7] });
                    PNM pnm = new PNM(temp[0], temp[1], temp[2], temp[3], Boolean.parseBoolean(temp[4]), Integer.parseInt(temp[5]), 
                    		temp[6], Tier.valueOf(temp[7]));
                    members.add(pnm);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            Log.logger.info("Opening: " + file.getName() + ".");

            //System.out.println("Opening: " + file.getName() + ".");
        } else {
            Log.logger.info("Open Commana Caancelled by user.");

            //System.out.println("Open command cancelled by user.");
        }
        return members;
    }








    
    public static void exportFile(){
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int returnVal = fc.showOpenDialog(fc);

        //Check if file is open
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                FileWriter excel = new FileWriter(file);

                //Export the column names to the file
                for (int i = 0; i < Controller.getModel().getColumnCount(); i++) {
                    excel.write(Controller.getModel().getColumnName(i) + ",");
                }
                excel.write("\n");

                //Export the rest of the table to the file
                for (int i = 0; i < Controller.getModel().getRowCount(); i++) {
                    for (int j = 0; j < Controller.getModel().getColumnCount(); j++) {
                        if (Controller.getModel().getValueAt(i, j) != null) {
                            excel.write(Controller.getModel().getValueAt(i, j).toString() + ",");
                        } else {
                            excel.write(",");
                        }
                    }
                    excel.write("\n");
                }
                excel.close();

            } catch (IOException er) {
                Log.logger.warning(er.getMessage());

                //System.out.println(er);
            }
        }

    }

    
}
