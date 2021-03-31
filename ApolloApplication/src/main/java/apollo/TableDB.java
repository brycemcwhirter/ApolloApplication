package apollo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableDB extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    static DefaultTableModel model;

    public TableDB() {
        String[] columnNames = { "Name", "Hometown", "Email", "Major", "Legacy", "Age", "Phone Number", "Tier" };
        model = new DefaultTableModel(columnNames, 0);
        JFrame tableFrame = new JFrame();
        tableFrame.setSize(750, 300);

        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(750, 200));
        table.setFillsViewportHeight(true);

        tableFrame.add(new JScrollPane(table), BorderLayout.PAGE_START);
        tableFrame.setVisible(true);
    }

    public TableDB(String n) {
        String[] columnNames = { "Name", "Hometown", "Email", "Major", "Legacy", "Age", "Phone Number", "Tier" };
        model = new DefaultTableModel(columnNames, 0);

        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(fc);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));

                String line = null;
                while ((line = reader.readLine()) != null) {
                    String temp[] = line.split(",");

                    model.addRow(
                            new Object[] { temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7] });
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
