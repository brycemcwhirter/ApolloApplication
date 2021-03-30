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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Controller
 */
public class Controller extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void openPage() {

        // Created New JFrame
        JFrame frame = new JFrame("Apollo");
        frame.setSize(230, 250);

        // Created New JPanel
        JPanel panel = new JPanel();
        frame.add(panel);

        // Added a New JLabel. Don't know if this is necesary.
        JLabel label = new JLabel("Apollo");
        label.setFont(new Font("Edwardian Script ITC", Font.PLAIN, 45));
        // label.setBounds(10, 20, 80, 25);
        label.setBackground(Color.decode("#00A3E0"));

        // New Database Button w/ Action Listener
        JButton newDatabase = new JButton("New Database");
        newDatabase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("New Database");
                createTable();

            }
        });

        // Import Database Button w/ Action Listener
        JButton importDatabase = new JButton("Import Database");
        importDatabase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Import Database");
                createTable();
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(Controller.this);
           	 
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                	File file = fc.getSelectedFile();
                	                
                	BufferedReader reader = null;
                	try {
                		reader = new BufferedReader(new FileReader(file));
                					
                	    String line = null;
                		while ((line = reader.readLine()) != null) {
                			String temp[] = line.split(",");
                						
                			model.addRow(new Object[]{temp[0], temp[1], temp[2], temp[3], temp[4]});
                		}
                	} catch (IOException e1) {
                		e1.printStackTrace();
                	}
                }
                });
            }
        });
        // label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        // newDatabase.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        // importDatabase.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(newDatabase);
        panel.add(importDatabase);

        // Showing Frame
        // Just found this set location relative to
        // function that will put the frame to the center
        // every time
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.decode("#00A3E0"));
        // frame.pack();
        frame.setVisible(true);

    }
    
    public static void createTable() {
    	JFrame tableFrame = new JFrame();
    	tableFrame.setSize(750,300);
    	String[] columnNames = {"Name","Hometown", "Email", "Major", "Legacy", "Age", "Phone Number", "Tier"};

    	DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    	         
    	JTable table = new JTable(model);
    	table.setPreferredScrollableViewportSize(new Dimension(750, 200));
    	table.setFillsViewportHeight(true);
    	        
    	tableFrame.add(new JScrollPane(table), BorderLayout.PAGE_START); 
    	tableFrame.setVisible(true);
    }

    public static void main(String[] args) {
        // Just Calling Open Page
        openPage();
        System.out.println("hi");
    }

}