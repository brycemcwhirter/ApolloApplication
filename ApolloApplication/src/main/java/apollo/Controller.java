package apollo;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import apollo.Objects.RushClass;
import apollo.Enum.Semester;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    static JFrame mainFrame = new JFrame();
    final static JPanel mainPanel = new JPanel();
    static RushClass mainRushClass;
    static JTable table;
    private static JTextField[] editFields;
	private static JLabel[] editLabels;
	static String columnNames[] = { "Name", "Hometown", "Email", "Major", "Legacy", "Age", "Phone Number", "Tier" };
	static JButton submitButton;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * openDialogue
     * 
     * Sets up the Open Sequence for the Apollo Application
     * 
     */
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
                createRushClass();
            }
        });

        // Import Database Button w/ Action Listener
        JButton importDatabase = new JButton("Import Database");
        importDatabase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Import Database");
                mainPage();
                importFile();
                createRushClass();
            }

        });
        

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
    
    public static void importFile() {
    	JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int returnVal = fc.showOpenDialog(fc);

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

    /**
     * mainPage
     * 
     * Opens up the main page and sets up panels
     */
    public static void mainPage() {

        /**
         * Main Panel Settings
         */
        
        mainPanel.setLayout(new BorderLayout());
       
        /**
         * Opening Main Panel to Full Screen 
         */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize(screenSize.width, screenSize.height);

        /**
         * Adding Panels to Main Panel
         */
        setTopButtonPanel(mainPanel);
        setTablePanel(mainPanel);

        /**
         * Adding Panels to Main Frame
         */
        mainFrame.add(mainPanel);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    
    /** 
     * setTopButtonPanel
     * 
     * Creates the Button Panel on top of the open page that holds
     * the buttons for adding/removing people. 
     * 
     * @param   mainPanel   the main panel of the open page
     */
    public static void setTopButtonPanel(JPanel mainPanel) {

        JPanel topButtonPanel = new JPanel();
        //topButtonPanel.setLayout(new FlowLayout());



        /** Add New Person
         * 
         * This button is responsible for adding
         * a new person to the list
         */
        JButton addNewPerson = new JButton("Add Person");
        addNewPerson.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Add Person");
                final JDialog popup = createPopup("Add Person");
                //Action listener for adding a person button
                submitButton.addActionListener(new ActionListener() {
    				@Override
    				public void actionPerformed(ActionEvent e) {
    					Object[] temp = new Object[columnNames.length];
    					for (int i = 0; i < columnNames.length; i++) {
    						temp[i] = editFields[i].getText();
    					}
    					//Add data from text fields to table
    					model.addRow(temp); 
    					popup.setVisible(false);
    					popup.dispose();
    				}
            		
            	});
            } 
        });

        

        /** Remove Person
         * 
         * This button is responsible for removing
         * an old person to the list
         */
        JButton removePerson = new JButton("Remove Person");
        removePerson.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Remove Person");
                
            }
        });



        /** List View
         * 
         * This button organizes the table into a list view
         */
        JButton listView = new JButton("List View");
        listView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("List View");
                
            } 
        });



        /** Graphic View
         * 
         * This button organizes the table into a graphic view
         */
        JButton graphicView = new JButton("Graphic View");
        graphicView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO Modify graphic view button to show graphic view of PNM's
                System.out.println("Graphic View");
                Controller.setGraphicPanel(Controller.mainPanel);
                
                
            }
        });
        
        /** Export to File
         * 
         * This button will export the table to a file
         */
        JButton exportToFile = new JButton("Export");
        exportToFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int returnVal = fc.showOpenDialog(fc);

				//Check if file is open
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						FileWriter excel = new FileWriter(file);

						//Export the column names to the file
						for (int i = 0; i < model.getColumnCount(); i++) {
							excel.write(model.getColumnName(i) + ",");
						}
						excel.write("\n");

						//Export the rest of the table to the file
						for (int i = 0; i < model.getRowCount(); i++) {
							for (int j = 0; j < model.getColumnCount(); j++) {
								if (model.getValueAt(i, j) != null) {
									excel.write(model.getValueAt(i, j).toString() + ",");
								} else {
									excel.write(",");
								}
							}
							excel.write("\n");
						}

						excel.close();

					} catch (IOException er) {
						System.out.println(er);
					}
				}
            }
        });

        

        // Adding Buttons to the button panel
        topButtonPanel.add(addNewPerson);
        topButtonPanel.add(removePerson);
        topButtonPanel.add(listView);
        topButtonPanel.add(graphicView);
        topButtonPanel.add(exportToFile);


        // Button Panel Settings
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        topButtonPanel.setSize(screenSize.width, 1000);
        topButtonPanel.setVisible(true);

        // Adding button panel to main panel 
        mainPanel.add(topButtonPanel, BorderLayout.CENTER);
    }

    

    /** 
     * setTablePanel
     * 
     * Adds the Table to a Tabel Panel which then adds that
     * panel to the main frame
     * @param   mainPanel   the main panel of the open page
     */
    public static void setTablePanel(JPanel mainPanel) {
       
        //TODO fix this function so that the table does not take up the whole screen and shows data
        model = new DefaultTableModel(columnNames, 0);

        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(750, 400));
        table.setFillsViewportHeight(true);

        mainPanel.add(new JScrollPane(table), BorderLayout.PAGE_END);

    }


    //TODO Modify graphic panel to show graphics
    public static void setGraphicPanel(JPanel mainPanel){

        JPanel graphicPanel = new JPanel();

        JLabel graphicLabel = new JLabel("graphic view here");

        graphicPanel.add(graphicLabel, BorderLayout.LINE_START);
        mainPanel.add(graphicPanel);
    }
    
    /** 
     * createRushClass
     * 
     * Prompts the user to enter information for the rush class
     */
    public static void createRushClass() {
    	mainRushClass = new RushClass();
    	
    	GridLayout layout = new GridLayout(0,2);
    	JPanel mainPanel = new JPanel();
    	mainPanel.setLayout(layout);
    	
    	//Create the popup to receive the data
    	final JFrame popup = new JFrame("Create Rush Class");
    	final JTextField year = new JTextField();
    	JLabel yearLabel = new JLabel("Year:");
    	year.setBounds(20, 220, 100, 25);
    	
    	mainPanel.add(yearLabel);
    	mainPanel.add(year);
    	
    	Semester[] semesters = {Semester.FALL, Semester.SPRING};
    	mainRushClass.setS(Semester.FALL);
    	final JComboBox<Semester> semesterList = new JComboBox<Semester>(semesters);
    	semesterList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Semester input = (Semester) semesterList.getSelectedItem();
                mainRushClass.setS(input);
            }
        });
    	
    	popup.add(mainPanel, BorderLayout.NORTH);
    	popup.add(semesterList);
    	popup.setVisible(true);
        popup.setLocationRelativeTo(null);
        popup.setSize(300,120);
        
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainRushClass.setYear(Integer.parseInt(year.getText()));
                JLabel semesterLabel = new JLabel("Semester: " + mainRushClass.getS().toString());
                JLabel yearLabel = new JLabel("Year: " + year.getText());
                JPanel top = new JPanel();
                top.add(semesterLabel);
                top.add(yearLabel);
                mainFrame.add(top, BorderLayout.PAGE_START);
                SwingUtilities.updateComponentTreeUI(mainFrame);
                
                popup.setVisible(false);
                popup.dispose();
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        popup.add(buttonPanel, BorderLayout.PAGE_END);
    }
    
    public static JDialog createPopup(String title) {
    	final JDialog popup = new JDialog(mainFrame, title);
    	GridLayout layout = new GridLayout(0,2);
    	JPanel mainPanel = new JPanel();
    	mainPanel.setLayout(layout);
    	
    	editFields = new JTextField[columnNames.length];
    	editLabels = new JLabel[columnNames.length];
        
        for (int i = 0; i < columnNames.length; i++) {
        	editFields[i] = new JTextField();
        	editFields[i].setBounds(20, 220, 100, 25);
        	editLabels[i] = new JLabel(columnNames[i] + ":");
        	mainPanel.add(editLabels[i]);
        	mainPanel.add(editFields[i]);
        }
      
        popup.add(mainPanel, BorderLayout.NORTH);
        submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");
        
        cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popup.setVisible(false);
				popup.dispose();
			}
    		
    	});
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        popup.add(buttonPanel, BorderLayout.PAGE_END);
        popup.setLocationRelativeTo(null);
        popup.pack();
        popup.setVisible(true);
        return popup;
    }


    /**
    * Main
    *
    * Calls open Dialogue and Begins the Application
    */
    public static void main(String[] args) {
        // Just Calling Open Page
        openDialogue();
    }

}