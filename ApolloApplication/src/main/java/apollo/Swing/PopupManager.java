package apollo.Swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import apollo.Controller;
import apollo.Enum.Semester;
import apollo.Enum.Tier;
import apollo.Objects.Event;
import apollo.Objects.PNM;

public class PopupManager {


    private static JTextField[] editFields;
	private static JLabel[] editLabels;
	static JButton submitButton;
	static String enterYear = null;

	public static JButton getSubmitButton() {
		return submitButton;
	}

	public static JTextField[] getEditFields() {
		return editFields;
	}

	/** 
     * eventPopup
     * 
     * This function creates a popup so that the user can 
     * 	create a new event
     */
	public static void eventPopup() {
    	final JFrame popup = new JFrame("New Event");
    	JPanel mainP = new JPanel();
    	mainP.setLayout(new GridLayout(0,2));
    	
    	//Create labels for popup
    	final JTextField fields[] = new JTextField[3];
    	JLabel labels[] = new JLabel[3];
    	labels[0] = new JLabel("Name: ");
    	labels[1] = new JLabel("Date (mm/dd/yyyy): ");
    	labels[2] = new JLabel("Location: ");
        
    	//Populate popup with labels and text fields
        for (int i = 0; i < 3; i++) {
        	fields[i] = new JTextField();
        	fields[i].setBounds(20, 220, 100, 25);
        	mainP.add(labels[i]);
        	mainP.add(fields[i]);
        }
        popup.add(mainP, BorderLayout.PAGE_START);
        
        JButton submitButton = new JButton("Submit");
        popup.add(submitButton, BorderLayout.PAGE_END);
        
        //Create JCheckBoxes for each PNM and add to panel
        JPanel namePanel = new JPanel(new GridLayout(0,3));
        final JCheckBox names[] = new JCheckBox[Controller.getMainRushClass().getMembers().size()];
        for (int i = 0; i < Controller.getMainRushClass().getMembers().size(); i++) {
        	names[i] = new JCheckBox(Controller.getMainRushClass().getMembers().get(i).getName());
        	namePanel.add(names[i]);
        }
        popup.add(namePanel, BorderLayout.CENTER);
        
        //Check for click of submit button
        submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//create event and add to each person
				try {
					Date d = new SimpleDateFormat("mm/dd/yyyy").parse(fields[1].getText());
					Event event = new Event(fields[0].getText(), d, fields[2].getText());
					//Add the attendees to the event
					for (int i = 0; i < Controller.getMainRushClass().getMembers().size(); i++) {
			        	if (names[i].isSelected()) {
			        		Controller.getMainRushClass().addEventToPerson(names[i].getText(), event);
			        		int index = Controller.getMainRushClass().findPerson(names[i].getText());
			        		event.getAttendees().add(Controller.getMainRushClass().getMembers().get(index));
			        	}
			        }
					Controller.getMainRushClass().getEvents().add(event);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				popup.setVisible(false);
				popup.dispose();
			}
    		
    	});
        
        popup.setVisible(true);
        popup.pack();
        popup.setLocationRelativeTo(null);
    }
	
	/** 
     * createRushClass
     * 
     * This function creates a popup so that the user can 
     * 	enter the semester and year for the rush class
     * 
     * @param   list   the list of PNMs to add to the rush class
     */
	public static void createRushClass(final List<PNM> list) {
    	//Create a grid layout
    	GridLayout layout = new GridLayout(0,2);
    	JPanel mainPanel = new JPanel();
    	mainPanel.setLayout(layout);
    	
    	//Create the popup to receive the data
    	final JFrame popup = new JFrame("Rush Class");
    	final JTextField year = new JTextField();
    	JLabel yearLabel = new JLabel("Year:");
    	year.setBounds(20, 220, 100, 25);
    	
    	mainPanel.add(yearLabel);
    	mainPanel.add(year);
    	
    	//Create a JComboBox to select the semester
    	Semester[] semesters = {Semester.FALL, Semester.SPRING};
    	Controller.getMainRushClass().setS(Semester.FALL);
    	final JComboBox<Semester> semesterList = new JComboBox<Semester>(semesters);
    	semesterList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Semester input = (Semester) semesterList.getSelectedItem();
                Controller.getMainRushClass().setS(input);
            }
        });
    	
    	//Add to panel
    	popup.add(mainPanel, BorderLayout.NORTH);
    	popup.add(semesterList);
    	popup.setVisible(true);
        popup.setLocationRelativeTo(null);
        popup.setSize(250,120);
        
        //Once submitted, assign the correct values to the rush class
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//Assign values to the rush class
            	Controller.getMainRushClass().setYear(Integer.parseInt(year.getText()));
            	Controller.getMainRushClass().setMembers(list);
                JLabel semesterLabel = new JLabel("Semester: " + Controller.getMainRushClass().getS().toString());
                JLabel yearLabel = new JLabel("Year: " + year.getText());
                JPanel top = new JPanel();
                top.add(semesterLabel);
                top.add(yearLabel);
                //Add rush class information to top of frame
                Controller.getMainFrame().add(top, BorderLayout.PAGE_START);
                Controller.getMainFrame().pack();
                Controller.getMainFrame().setSize(1250,350);
                
                popup.setVisible(false);
                popup.dispose();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        popup.add(buttonPanel, BorderLayout.PAGE_END);
    }
	/** 
     * setGraphicPanel
     * 
     * This function sets the frame to the gallery view
     * 
     * @param   list   the list of PNMs to add to the rush class
     */
	public static  void setGraphicPanel() throws IOException {
    	//Clear panel and add back top button panel
		Controller.getMainpanel().removeAll();
    	Controller.setTopButtonPanel(Controller.getMainpanel());
    	JPanel blank = new JPanel();
    	Controller.getMainpanel().add(blank, BorderLayout.CENTER);
        
    	//Remove everything, then add back button panel and gallery view
        List<PNM> members = Controller.getMainRushClass().getMembers();
        GridLayout lay = new GridLayout(0,3, 10, 10);
        JPanel graphicPanel = new JPanel();
        graphicPanel.setLayout(lay);
        JScrollPane scroll = new JScrollPane(graphicPanel);
        Controller.getMainpanel().add(scroll, BorderLayout.PAGE_END);
        final JButton buttons[] = new JButton[members.size()];
        //For each person, create a new panel with their information and add to grid
        for (int i = 0; i < members.size(); i++) {
        	final int j = i;
        	JPanel p = new JPanel(new GridLayout(0,1));
        	JLabel text = new JLabel(members.get(i).getName());
        	JLabel text2 = new JLabel("Legacy Status: " + members.get(i).getLegacy());
        	JLabel text3 = new JLabel("Tier: " + members.get(i).getT());
        	p.add(text);
        	p.add(text2);

        	p.add(text3);
        	buttons[i] = new JButton("More");
        	buttons[i].setName(members.get(i).getName());
        	//Check if button is clicked
        	buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	try {
						openMore(buttons[j].getName());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
            });
        	p.add(buttons[i]);
        	p.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        	graphicPanel.add(p);
        }
        
        Controller.getMainFrame().pack();
        Controller.getMainFrame().setLocationRelativeTo(null);
    }
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	} 
	
	/** 
     * openMore
     * 
     * This function opens the "more" page for the selected PNM
     * 
     * @param   name   the name of the PNM
	 * @throws IOException 
     */
	protected static void openMore(String name) throws IOException {
		final JFrame popup = new JFrame("More Information");
		final int index = Controller.getMainRushClass().findPerson(name);
		final PNM currPNM = Controller.getMainRushClass().getMembers().get(index);
		GridLayout layout = new GridLayout(5,2);
		layout.setHgap(10);
		layout.setVgap(10);
		
		String imgName = "Photos/" + currPNM.getName() + ".png";
		
		BufferedImage logo;
		try {
			logo = ImageIO.read(new File(imgName));
		} catch (IOException e) {
			//e.printStackTrace();
			logo = ImageIO.read(new File("DefaultSilhouette.jpg"));
		}
		BufferedImage newLogo = resize(logo, 200,200);
		JLabel img = new JLabel(new ImageIcon(newLogo));
		JPanel imgPanel = new JPanel();
		imgPanel.add(img);
        popup.add(imgPanel, BorderLayout.CENTER);
		
		//Populate the popup with the PNM's information
		JPanel mainPanel = new JPanel(layout);
		JLabel l = new JLabel("Name: " + currPNM.getName());
		mainPanel.add(l);
		JLabel l2 = new JLabel("Hometown: " + currPNM.getHometown());
		mainPanel.add(l2);
		JLabel l3 = new JLabel("Major: " + currPNM.getMajor());
		mainPanel.add(l3);
		JLabel l4 = new JLabel("Phone Number: " + currPNM.getPhoneNumber());
		mainPanel.add(l4);
		JLabel l5 = new JLabel("Email: " + currPNM.getEmail());
		mainPanel.add(l5);
		JLabel l6 = new JLabel("Age: " + currPNM.getAge());
		mainPanel.add(l6);
		JLabel l7 = new JLabel("Legacy Status: " + currPNM.getLegacy());
		mainPanel.add(l7);
		JLabel l8 = new JLabel("Tier: " + currPNM.getT());
		mainPanel.add(l8);
		JButton vouchButton = new JButton("Vouch List");
		vouchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	vouchListPopup(currPNM);
            }
        });
		JButton eventButton = new JButton("Events Attended");
		eventButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showEvents(currPNM.getEventList(), currPNM.getName());
            }
        });
		//Add buttons to panel
		mainPanel.add(vouchButton);
		mainPanel.add(eventButton);
		
		//Check if buttons are clicked
		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					iteratePerson("Next", popup, index);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
		JButton prev = new JButton("Prev");
		prev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					iteratePerson("Prev", popup, index);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
		
		popup.add(mainPanel, BorderLayout.PAGE_END);
		JPanel prevP = new JPanel();
		prevP.add(prev);
		popup.add(prevP, BorderLayout.LINE_START);
		JPanel nextP = new JPanel();
		nextP.add(next);
		popup.add(nextP, BorderLayout.LINE_END);
		popup.setVisible(true);
		popup.pack();
		popup.setLocationRelativeTo(null);
	}
	
	/** 
     * showEvents
     * 
     * This function opens a new popup window that displays all the event
     * 	in the rush class
     * 
     * @param	events the list of events to display
     * @param   name   the name to display
     */
	public static void showEvents(List<Event> events, String name) {
		final JFrame eventPopup = new JFrame("Event List");
		GridLayout layout = new GridLayout(0, 3);
		layout.setHgap(10);
		layout.setVgap(10);	
		JPanel eventPanel = new JPanel(layout);
		
		//Check if there are events to display
		if (events.size() == 0 && name != "") {
			JLabel error = new JLabel(name + " has not attended any events");
			eventPopup.add(error, BorderLayout.CENTER);
		} else if (name == "" && events.size() == 0){
			JLabel error = new JLabel("There are no events");
			eventPopup.add(error, BorderLayout.CENTER);
		} else {
			//Display the events in a grid layout
			eventPanel.add(new JLabel("Name"));
			eventPanel.add(new JLabel("Date"));
			eventPanel.add(new JLabel("Location"));
			
			for (int i = 0; i < events.size(); i++) {
				DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");  	
				JLabel eventName = new JLabel(events.get(i).getName());
				Font f = eventName.getFont();
				eventName.setFont(f.deriveFont(f.getStyle() | ~Font.BOLD));
				eventPanel.add(eventName);
				
				JLabel eventDate = new JLabel(dateFormat.format(events.get(i).getDate()));
				eventDate.setFont(f.deriveFont(f.getStyle() | ~Font.BOLD));
				eventPanel.add(eventDate);
				
				JLabel eventLoc = (new JLabel(events.get(i).getLocation()));
				eventLoc.setFont(f.deriveFont(f.getStyle() | ~Font.BOLD));
				eventPanel.add(eventLoc);
			}
			eventPopup.add(eventPanel, BorderLayout.CENTER);
		}
		//If done is clicked, close the popup
    	JButton done = new JButton("Done");
    	done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	eventPopup.setVisible(false);
            	eventPopup.dispose();
            }
        });
    	JPanel donePanel = new JPanel();
    	donePanel.add(done);
    	eventPopup.add(donePanel, BorderLayout.PAGE_END);
    	eventPopup.setVisible(true);
    	eventPopup.pack();
    	eventPopup.setLocationRelativeTo(null);
	}
	
	/** 
     * vouchListPopup
     * 
     * This function opens a new popup window that displays all the names
     * 	in the selected PNMs vouch list
     * 
     * @param	currPNM the selected PNM
     */
	public static void vouchListPopup(PNM currPNM) {
		final JFrame vouchPopup = new JFrame("Vouch List");
		GridLayout layout = new GridLayout(currPNM.getVouchList().size()/3, 3);
		layout.setHgap(10);
		layout.setVgap(10);
		JPanel title = new JPanel();
		//Create title for the popup
		JLabel titleL = new JLabel("Vouch List for " + currPNM.getName());
		title.add(titleL);
		vouchPopup.add(title, BorderLayout.PAGE_START);
		
		//Check if vouch list has names
		JPanel namesPanel = new JPanel(layout);
		if (currPNM.getVouchList().size() == 0) {
			JLabel error = new JLabel("No People in Vouch List");
			vouchPopup.add(error, BorderLayout.CENTER);
		} else {
			for (int i = 0; i < currPNM.getVouchList().size(); i++) {
				JLabel name = new JLabel(currPNM.getVouchList().get(i));
				Font f = name.getFont();
				name.setFont(f.deriveFont(f.getStyle() | ~Font.BOLD));
				namesPanel.add(name);
			}
			vouchPopup.add(namesPanel, BorderLayout.CENTER);
		}
		//If done is clicked, close the popup
    	JButton done = new JButton("Done");
    	done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	vouchPopup.setVisible(false);
            	vouchPopup.dispose();
            }
        });
    	JPanel donePanel = new JPanel();
    	donePanel.add(done);
    	vouchPopup.add(donePanel, BorderLayout.PAGE_END);
    	vouchPopup.setVisible(true);
    	vouchPopup.pack();
    	vouchPopup.setLocationRelativeTo(null);
	}
	
	/** 
     * addVouchPopup
     * 
     * This function opens a new popup window with a text field
     * 	so that the user can enter names to be added to the
     * 	selected PNMs vouch list
     * 
     * @param	currPNM the selected PNM
     */
	public static void addVouchPopup() {
		//Create popup
		final JFrame popup = new JFrame("Edit Tier");
		final int index = Controller.getMainRushClass().findPerson((String) 
				Controller.getModel().getValueAt(Controller.getTable().getSelectedRow(), 0));
    	JLabel name = new JLabel("Adding vouch names for " + (String) 
    			Controller.getModel().getValueAt(Controller.getTable().getSelectedRow(), 0));
    	name.setFont(new Font("Arial Hebrew", Font.PLAIN, 15));
    	final JTextField text = new JTextField();
    	text.setPreferredSize(new Dimension(100,20));
    	
    	//If submit is clicked, add name to vouch list and clear text field
    	JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Controller.getMainRushClass().getMembers().get(index).getVouchList().add(text.getText());
            	text.setText("");
            }
        });
        name.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        popup.add(name, BorderLayout.PAGE_START);
        JPanel p = new JPanel();
        p.add(text);
        p.add(submitButton);
        popup.add(p, BorderLayout.CENTER);
        
        //If done is clicked, close popup
        JButton done = new JButton("Done");
    	done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	popup.setVisible(false);
            	popup.dispose();
            }
        });
    	JPanel donePanel = new JPanel();
    	donePanel.add(done);
    	popup.add(donePanel, BorderLayout.PAGE_END);
        
    	popup.setVisible(true);
    	popup.pack();
    	popup.setLocationRelativeTo(null);
	}
	
	/** 
     * iteratePerson
     * 
     * This function iterates to the next or previous person if possible
     * 
     * @param	s 		whether to iterate forward or backward
     * @param	popup	the original popup to discard
     * @param	index	the index of the original PNM
	 * @throws IOException 
     */
	public static void iteratePerson(String s, JFrame popup, int index) throws IOException {
		//Check if the iteration is valid
		if (s == "Next" && index < Controller.getMainRushClass().getMembers().size()-1) {
			popup.setVisible(false);
			popup.dispose();
			openMore(Controller.getMainRushClass().getMembers().get(++index).getName());
		} else if (s == "Prev" && index > 0){
			popup.setVisible(false);
			popup.dispose();
			openMore(Controller.getMainRushClass().getMembers().get(--index).getName());
		} else {
			JOptionPane.showMessageDialog(popup,"Cannot Move to Next Person","Error",JOptionPane.WARNING_MESSAGE);
		}
	}

	/** 
     * tierPopup
     * 
     * This function opens a popup to edit the tier of the selected PNM
     *
     */
	public static void tierPopup() {
		//Create the popup
		final JFrame popup = new JFrame("Edit Tier");
    	JLabel name = new JLabel("Editing " + (String) 
    			Controller.getModel().getValueAt(Controller.getTable().getSelectedRow(), 0) + "'s tier");
    	name.setFont(new Font("Arial Hebrew", Font.PLAIN, 15));
    	Tier[] tiers = {Tier.GRAY, Tier.GREEN, Tier.RED};
    	final JComboBox<Tier> tierSelect = new JComboBox<Tier>(tiers);
    	
    	//If submit is clicked, change the tier of the PNM
    	JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Controller.getTable().setValueAt(tierSelect.getSelectedItem(), Controller.getTable().getSelectedRow(), 7);
            	Controller.getMainRushClass().editTier((String) 
            			Controller.getModel().getValueAt(Controller.getTable().getSelectedRow(), 0), 
            			(Tier) tierSelect.getSelectedItem());
                
                popup.setVisible(false);
                popup.dispose();
            }
        });
        //Add panels to the popup
        name.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        popup.add(name, BorderLayout.PAGE_START);
        JPanel p = new JPanel();
        p.add(tierSelect);
        popup.add(p, BorderLayout.CENTER);
        JPanel p2 = new JPanel();
        p2.add(submitButton);
    	popup.add(p2, BorderLayout.PAGE_END);
    	popup.setVisible(true);
    	popup.pack();
    	popup.setLocationRelativeTo(null);

	}

	/** 
     * tierPopup
     * 
     * This function opens a popup to add a person to the rush class
     * 
     * @param	title	the title of the popup
     */
	public static JDialog createPopup(String title) {
    	final JDialog popup = new JDialog(Controller.getMainFrame(), title);
    	GridLayout layout = new GridLayout(0,2);
    	JPanel mainPanel = new JPanel();
    	mainPanel.setLayout(layout);
    	
    	editFields = new JTextField[PNM.getColumnNames().length];
    	editLabels = new JLabel[PNM.getColumnNames().length];
        
    	//Create the correct amount of edit fields and add them to the panel
        for (int i = 0; i < PNM.getColumnNames().length; i++) {
        	editFields[i] = new JTextField();
        	editFields[i].setBounds(20, 220, 100, 25);
        	editLabels[i] = new JLabel(PNM.getColumnNames()[i] + ":");
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
        //Add buttons to popup
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        popup.add(buttonPanel, BorderLayout.PAGE_END);
        popup.setLocationRelativeTo(null);
        popup.pack();
        popup.setVisible(true);
        return popup;
    }

	
}
