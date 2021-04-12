package apollo;



import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.System.Logger;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;


/**
 * Controller
 */
public class Controller extends JPanel {

    static DefaultTableModel model;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static void openDialogue() {

        // Created New JFrame
        final JFrame frame = new JFrame("Apollo");
        frame.setSize(230, 250);

        // Created New JPanel
        JPanel panel = new JPanel();
        frame.add(panel);

        // Added a New JLabel. Don't know if this is necesary.
        JLabel label = new JLabel("Apollo");
        label.setFont(new Font("Edwardian Script ITC", Font.PLAIN, 45));
        label.setForeground(new Color(254, 209, 65));
        label.setBackground(Color.decode("#00A3E0"));

        // Image Icon Code
        // ImageIcon logo = new ImageIcon("Apollo.png");

        // New Database Button w/ Action Listener
        JButton newDatabase = new JButton("New Database");
        newDatabase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("New Database");
                mainPage();
            }
        });

        // Import Database Button w/ Action Listener
        JButton importDatabase = new JButton("Import Database");
        importDatabase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Import Database");
                mainPage();
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

                            model.addRow(new Object[] { temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6],
                                    temp[7] });
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    System.out.println("Opening: " + file.getName() + ".");
                } else {
                    System.out.println("Open command cancelled by user.");
                }
            }

        });
        // label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        // newDatabase.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        // importDatabase.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(newDatabase);
        panel.add(importDatabase);
        panel.setBackground(new Color(0, 163, 224));

        // Showing Frame
        // Just found this set location relative to
        // function that will put the frame to the center
        // every time
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(0, 163, 224));
        // frame.pack();
        frame.setVisible(true);

    }

    public static void mainPage() {
        JFrame mainFrame = new JFrame();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,1));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize(screenSize.width, screenSize.height);

        topButtonPanel(mainPanel);
        tablePanel(mainPanel);


        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);

        
    }

    
    /** 
     * @param frame
     */
    public static void topButtonPanel(JPanel panel) {

        JPanel topButtonPanel = new JPanel();

        JButton addNewPerson = new JButton("Add Person");
        addNewPerson.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add Person");
                
            }
        });

        JButton removePerson = new JButton("Remove Person");
        removePerson.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Remove Person");
                
            }
        });


        topButtonPanel.add(addNewPerson);
        topButtonPanel.add(removePerson);
        
        topButtonPanel.setVisible(true);

        panel.add(topButtonPanel, BorderLayout.CENTER);
    }




    
    /** 
     * @param frame
     */
    public static void tablePanel(JPanel panel) {
        
        String[] columnNames = { "Name", "Hometown", "Email", "Major", "Legacy", "Age", "Phone Number", "Tier" };

        model = new DefaultTableModel(columnNames, 0);

        JTable table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(750, 200));
        table.setFillsViewportHeight(true);

        panel.add(new JScrollPane(table), BorderLayout.PAGE_END);  
    }

    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        // Just Calling Open Page
        openDialogue();
    }

}