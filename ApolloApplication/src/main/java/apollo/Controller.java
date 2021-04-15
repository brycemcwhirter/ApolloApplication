package apollo;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import apollo.Objects.PNM;
import apollo.Objects.RushClass;
import apollo.Enum.Semester;
import apollo.Enum.Tier;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	public enum viewState {
		LIST,
		GALLERY;
	}
	
	static DefaultTableModel model;
    static JFrame mainFrame = new JFrame();
    final static JPanel mainPanel = new JPanel();
    static RushClass mainRushClass;
    static JTable table;
    private static JTextField[] editFields;
	private static JLabel[] editLabels;
	static String columnNames[] = { "Name", "Hometown", "Email", "Major", "Legacy", "Age", "Phone Number", "Tier" };
	static JButton submitButton;
	static JScrollPane pane;
	static JPanel graphicPanel;
	public static Boolean listviewMode = true;
    private static final long serialVersionUID = 1L;

    /**
     * openDialogue
     * 
     * Sets up the Open Sequence for the Apollo Application
     * @throws IOException 
     * 
     */
    public static void openDialogue() throws IOException {

        // Created New JFrame
        final JFrame frame = new JFrame("Apollo");
        frame.setSize(300, 300);

        // Created New JPanel
        JPanel panel = new JPanel();
        frame.add(panel);

        // Added a New JLabel. Don't know if this is necesary.
        JLabel label = new JLabel("Apollo");
        BufferedImage logo = ImageIO.read(new File("ApolloLogo.png"));
        JLabel img = new JLabel(new ImageIcon(logo));
        img.setPreferredSize(new Dimension(200,150));
        
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
                List<PNM> members = new ArrayList<PNM>();
                createRushClass(members);
            }
        });

        // Import Database Button w/ Action Listener
        JButton importDatabase = new JButton("Import Database");
        importDatabase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Import Database");
                mainPage();
                List<PNM> members = importFile();
                createRushClass(members);
            }

        });
        

        panel.add(label);
        panel.add(img);
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
                    model.addRow(new Object[] { temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6],
                            temp[7] });
                    PNM pnm = new PNM(temp[0], temp[1], temp[2], temp[3], Boolean.parseBoolean(temp[4]), Integer.parseInt(temp[5]), 
                    		temp[6], Tier.valueOf(temp[7]));
                    members.add(pnm);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            System.out.println("Opening: " + file.getName() + ".");
        } else {
            System.out.println("Open command cancelled by user.");
        }
        return members;
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
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //mainFrame.setSize(screenSize.width, screenSize.height);

        /**
         * Adding Panels to Main Panel
         */
        setTopButtonPanel(mainPanel);
        setTablePanel(mainPanel);

        /**
         * Adding Panels to Main Frame
         */
        mainFrame.add(mainPanel);
        mainFrame.setSize(1000,350);
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
    					String[] temp = new String[columnNames.length];
    					for (int i = 0; i < columnNames.length; i++) {
    						temp[i] = editFields[i].getText();
    					}
    					PNM pnm = new PNM(temp[0], temp[1], temp[2], temp[3], Boolean.parseBoolean(temp[4]), Integer.parseInt(temp[5]), 
                        		temp[6], Tier.valueOf(temp[7]));
    					mainRushClass.addMember(pnm);
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
         * a person from the list
         */
        JButton removePerson = new JButton("Remove Person");
        removePerson.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Remove Person");
                //Check to make sure that a row is selected
                if (table.getSelectedRow() != -1) {
                	Object[] options = {"Yes", "No"};
            		int n = JOptionPane.showOptionDialog(mainFrame,
            			    "Are you sure you want to remove " + (model.getValueAt(table.getSelectedRow(), 0)) + "?",
            			    "Delete Row?",
            			    JOptionPane.YES_NO_OPTION,
            			    JOptionPane.QUESTION_MESSAGE,
            			    null,
            			    options,
            			    options[0]);
    	        	if (n == 0) {
    	        		//Remove the person from the table and the rush class
	                	mainRushClass.removePerson((String) model.getValueAt(table.getSelectedRow(), 0));
	                	model.removeRow(table.getSelectedRow());
    	        	}
                }
            }
        });



        /** List View
         * 
         * This button organizes the table into a list view
         */
        JButton listView = new JButton("List View");
        listView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//Remove everything, then add back button panel and table
                System.out.println("List View");
                if (!listviewMode) {
	                Controller.mainPanel.removeAll();
	            	setTopButtonPanel(Controller.mainPanel);
	            	Controller.mainPanel.add(pane, BorderLayout.PAGE_END);
	            	mainFrame.setSize(1000,350);
	                mainFrame.setLocationRelativeTo(null);
	                listviewMode = true;
                }
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
              //Remove everything, then add back button panel and gallery view
                if (listviewMode) {
                	Controller.setGraphicPanel(Controller.mainPanel);
                	listviewMode = false;
                }
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
     * Adds the Table to a Table Panel which then adds that
     * panel to the main frame
     * @param   mainPanel   the main panel of the open page
     */
    public static void setTablePanel(JPanel mainPanel) {
       
        //TODO fix this function so that the table does not take up the whole screen and shows data
        model = new DefaultTableModel(columnNames, 0);

        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(400, 200));
        table.setFillsViewportHeight(true);
        
        pane = new JScrollPane(table);

        mainPanel.add(pane, BorderLayout.PAGE_END);
    }


    //TODO Modify graphic panel to show graphics
    public static  void setGraphicPanel(JPanel mainPanel) {
    	mainPanel.removeAll();
    	setTopButtonPanel(mainPanel);
        
    	//Remove everything, then add back button panel and gallery view
        List<PNM> test = mainRushClass.getMembers();
        GridLayout lay = new GridLayout(3,(test.size()/2)+1);
        graphicPanel = new JPanel();
        graphicPanel.setLayout(lay);
        mainPanel.add(graphicPanel, BorderLayout.PAGE_END);
      //For each person, create a new panel with their information and add to grid
        for (int i = 0; i < test.size(); i++) {
        	JPanel p = new JPanel();
        	GridLayout lay2 = new GridLayout(3,1);
        	p.setLayout(lay2);
        	JLabel text = new JLabel();
        	text.setText(test.get(i).getName());
        	JLabel text2 = new JLabel();
        	text2.setText(test.get(i).getMajor());
        	p.add(text);
        	p.add(text2);
        	JButton bt = new JButton("More");
        	p.add(bt);
        	p.setSize(new Dimension(30, 100));
        	p.setBorder(BorderFactory.createLineBorder(Color.black));
        	graphicPanel.add(p);
        }
        if (test.size() < 2) {
        	mainFrame.setSize(1000,200);
        } else {
        	mainFrame.setSize(1000,test.size()/2 * 100);
        }
        mainFrame.setLocationRelativeTo(null);
    }
    
    /** 
     * createRushClass
     * 
     * Prompts the user to enter information for the rush class
     */
    public static void createRushClass(final List<PNM> list) {
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
    	
    	//Create a JComboBox to select the semester
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
        
        //Once submitted, assign the correct values to the rush class
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainRushClass.setYear(Integer.parseInt(year.getText()));
                mainRushClass.setMembers(list);
                JLabel semesterLabel = new JLabel("Semester: " + mainRushClass.getS().toString());
                JLabel yearLabel = new JLabel("Year: " + year.getText());
                JPanel top = new JPanel();
                top.add(semesterLabel);
                top.add(yearLabel);
                mainFrame.add(top, BorderLayout.PAGE_START);
                mainFrame.pack();
                mainFrame.setSize(1000,350);
                
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
        
    	//Create the correct amount of edit fields and add them to the panel
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
     * @throws IOException 
    */
    public static void main(String[] args) throws IOException {
        // Just Calling Open Page
        openDialogue();
    }

}