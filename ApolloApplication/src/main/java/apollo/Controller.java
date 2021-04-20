package apollo;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import apollo.Objects.PNM;
import apollo.Objects.RushClass;
import apollo.Swing.Button;
import apollo.Swing.PopupManager;
import apollo.Table.TableView;
import apollo.FileManager.FileManager;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;


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
    static RushClass mainRushClass = new RushClass();;
    static JTable table;
	static JScrollPane pane;
    private static final long serialVersionUID = 1L;
    static JComboBox<String> colSelect;
    static TableRowSorter<TableModel> sorter;


    public static RushClass getMainRushClass() {
        return mainRushClass;
    }

    public static JFrame getMainFrame() {
        return mainFrame;
    }

    public static DefaultTableModel getModel() {
        return model;
    }

    public static JTable getTable() {
        return table;
    }

    public static JPanel getMainpanel() {
        return mainPanel;
    }

    public static JScrollPane getPane() {
        return pane;
    }




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
        
        // Setting Font & Background Color for Opening Sequence
        label.setFont(new Font("Edwardian Script ITC", Font.PLAIN, 45));
        label.setForeground(new Color(254, 209, 65));
        label.setBackground(Color.decode("#00A3E0"));

        

        // New Database Button w/ Action Listener
        JButton newDatabase = new JButton("New Database");
        newDatabase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.logger.info("New Database");
                mainPage();
                List<PNM> members = new ArrayList<PNM>();
                PopupManager.createRushClass(members, mainRushClass, mainFrame);
            }
        });

        // Import Database Button w/ Action Listener
        JButton importDatabase = new JButton("Import Database");
        importDatabase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.logger.info("Import Database");
                Button.importDatabase();
            }

        });
        

        // Adding to Panel
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
        frame.setVisible(true);

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
         * Adding Panels to Main Panel
         */
        setTopButtonPanel(mainPanel);
        setTablePanel(mainPanel);
        setFilter(mainPanel);


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

        /** Add New Person
         * 
         * This button is responsible for adding
         * a new person to the list
         */
        JButton addNewPerson = new JButton("Add Person");
        addNewPerson.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.logger.info("Add Person Button Pressed");

                Button.addNewPerson();

                
            } 
        });

        

        /** Remove Person Button
         * 
         * This button is responsible for removing
         * a person from the list
         */
        JButton removePerson = new JButton("Remove Person");
        removePerson.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Log.logger.info("Remove Person Button Pressed");

                Button.removePerson();
            }
        });



        /** List View Button
         * 
         * This button organizes the table into a list view
         */
        JButton listView = new JButton("List View");
        listView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//Remove everything, then add back button panel and table

                Log.logger.info("List View Button Pressed");

               // System.out.println("List View");
                if (!TableView.getListviewMode()) {
                    TableView.setListviewMode(true);
                    TableView.listView();
                	
                }
            } 
        });
        if (TableView.getListviewMode()) {
            listView.setEnabled(false);
        }



        /** Gallery View
         * 
         * This button organizes the table into a gallery view
         */
        JButton graphicView = new JButton("Gallery View");
        graphicView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO Modify graphic view button to show graphic view of PNM's
                Log.logger.info("Gallery View");
                TableView.galleryView();
            	
            }
        });
        if (!TableView.getListviewMode()) {
            graphicView.setEnabled(false);
        }
        
        /** Export to File Button
         * 
         * This button will export the table to a file
         */
        JButton exportToFile = new JButton("Export");
        exportToFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.logger.info("Exporting File");
                FileManager.exportFile();
				
				}
            }
        );
        
        JButton editTier = new JButton("Edit Tier");
        editTier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Log.logger.info("Editing Tier");
                //System.out.println("Edit Tier");
                //Check to make sure that a row is selected
                if (table.getSelectedRow() != -1) {
                	PopupManager.tierPopup(model, table, mainRushClass);
                }
            }
        });
        
        JButton eventButton = new JButton("Create Event");
        eventButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PopupManager.eventPopup(mainRushClass);
            }
        });
        
        JButton showEventsButton = new JButton("Show Events");
        showEventsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PopupManager.showEvents(mainRushClass.getEvents(), "");
            }
        });
        
        JButton addVouchNames = new JButton("Add Vouch Names");
        addVouchNames.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (table.getSelectedRow() != -1) {
                	PopupManager.addVouchPopup(model, table, mainRushClass);
                }
            }
        });
        
        JPanel tableButtons = new JPanel(new GridLayout(0,3, 5, 5));
        JPanel otherPanel = new JPanel(new GridLayout(0,2,5,5));

        // Adding Buttons to the button panel
        tableButtons.add(addNewPerson);
        tableButtons.add(listView);
        tableButtons.add(graphicView);
        tableButtons.add(exportToFile);
        tableButtons.add(eventButton);
        tableButtons.add(showEventsButton);
        
        otherPanel.add(addVouchNames);
        otherPanel.add(removePerson);
        otherPanel.add(editTier);

        tableButtons.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        otherPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        // Adding button panel to main panel 
        mainPanel.add(tableButtons, BorderLayout.LINE_START);
        if (TableView.getListviewMode()) {
        	mainPanel.add(otherPanel, BorderLayout.LINE_END);
        }
    }

    






    /** 
     * setTablePanel
     * 
     * Adds the Table to a Table Panel which then adds that
     * panel to the main frame
     * @param   mainPanel   the main panel of the open page
     */
    public static void setTablePanel(JPanel mainPanel) {
       
        model = new DefaultTableModel(PNM.getColumnNames(), 0);

        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(400, 200));
        table.setFillsViewportHeight(true);
        sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        
        pane = new JScrollPane(table);

        mainPanel.add(pane, BorderLayout.PAGE_END);
    }
    
    public static void setFilter(JPanel mainPanel) {
    	JLabel filterLabel = new JLabel("Filter:");
    	colSelect = new JComboBox<String>(PNM.getColumnNames());
    	final JTextField filterText = new JTextField();
        filterText.setPreferredSize(new Dimension(80,20));
        filterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter(filterText);
                    }
                    public void insertUpdate(DocumentEvent e) {
                        newFilter(filterText);
                    }
                    public void removeUpdate(DocumentEvent e) {
                        newFilter(filterText);
                    }
                });
    	
        JTextField clear = new JTextField();
    	newFilter(clear);
    	JPanel other = new JPanel();
        other.add(filterLabel);
        other.add(colSelect);
        other.add(filterText);
        other.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        mainPanel.add(other, BorderLayout.CENTER);
    }
    
    private static void newFilter(JTextField f) {
    	RowFilter<? super TableModel, ? super Integer> rf = null;
    	try {
            rf = RowFilter.regexFilter(f.getText(), colSelect.getSelectedIndex());
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
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