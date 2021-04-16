package apollo.Swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import apollo.Controller;
import apollo.Enum.Semester;
import apollo.Enum.Tier;
import apollo.Objects.Event;
import apollo.Objects.PNM;
import apollo.Objects.RushClass;

public class PopupManager {
	public static void eventPopup(final RushClass mainRushClass) {
    	final JFrame popup = new JFrame("New Event");
    	JPanel mainP = new JPanel();
    	mainP.setLayout(new GridLayout(0,2));
    	
    	final JTextField fields[] = new JTextField[3];
    	JLabel labels[] = new JLabel[3];
    	labels[0] = new JLabel("Name: ");
    	labels[1] = new JLabel("Date (dd/mm/yyyy): ");
    	labels[2] = new JLabel("Location: ");
        
        for (int i = 0; i < 3; i++) {
        	fields[i] = new JTextField();
        	fields[i].setBounds(20, 220, 100, 25);
        	mainP.add(labels[i]);
        	mainP.add(fields[i]);
        }
        popup.add(mainP, BorderLayout.PAGE_START);
        
        JButton submitButton = new JButton("Submit");
        popup.add(submitButton, BorderLayout.PAGE_END);
        
        
        JPanel namePanel = new JPanel(new GridLayout(0,3));
        final JCheckBox names[] = new JCheckBox[mainRushClass.getMembers().size()];
        for (int i = 0; i < mainRushClass.getMembers().size(); i++) {
        	names[i] = new JCheckBox(mainRushClass.getMembers().get(i).getName());
        	namePanel.add(names[i]);
        }
        popup.add(namePanel, BorderLayout.CENTER);
        
        submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//create event and add to each person
				try {
					Date d = new SimpleDateFormat("dd/MM/yyyy").parse(fields[1].getText());
					Event event = new Event(fields[0].getText(), d, fields[2].getText());
					for (int i = 0; i < mainRushClass.getMembers().size(); i++) {
			        	if (names[i].isSelected()) {
			        		mainRushClass.addEventToPerson(names[i].getText(), event);
			        	}
			        }
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
	
	public static void createRushClass(final List<PNM> list, final RushClass mainRushClass, final JFrame mainFrame) {
    	
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
        popup.setSize(250,120);
        
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
	
	public static  void setGraphicPanel(JPanel mainPanel, JFrame mainFrame, RushClass mainRushClass) throws IOException {
    	mainPanel.removeAll();
    	Controller.setTopButtonPanel(mainPanel);
        
    	//Remove everything, then add back button panel and gallery view
        List<PNM> test = mainRushClass.getMembers();
        GridLayout lay = new GridLayout(3,(test.size()/2)+1);
        lay.setHgap(15);
        lay.setVgap(15);
        JPanel graphicPanel = new JPanel();
        graphicPanel.setLayout(lay);
        JScrollPane scroll = new JScrollPane(graphicPanel);
        mainPanel.add(scroll, BorderLayout.PAGE_END);
        //For each person, create a new panel with their information and add to grid
        for (int i = 0; i < test.size(); i++) {
        	JPanel p = new JPanel();
        	GridLayout lay2 = new GridLayout(4,1);
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
        	p.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        	graphicPanel.add(p);
        }
        
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
    }
	
	public static void tierPopup(final DefaultTableModel model, final JTable table, final RushClass mainRushClass) {
		final JFrame popup = new JFrame("Edit Tier");
    	JLabel name = new JLabel("Editing " + (String) model.getValueAt(table.getSelectedRow(), 0) + "'s tier");
    	name.setFont(new Font("Arial Hebrew", Font.PLAIN, 15));
    	Tier[] tiers = {Tier.GRAY, Tier.GREEN, Tier.RED};
    	final JComboBox<Tier> tierSelect = new JComboBox<Tier>(tiers);
    	
    	JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	table.setValueAt(tierSelect.getSelectedItem(), table.getSelectedRow(), 7);
            	mainRushClass.editTier((String) model.getValueAt(table.getSelectedRow(), 0), 
            			(Tier) tierSelect.getSelectedItem());
                
                popup.setVisible(false);
                popup.dispose();
            }
        });
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
	
}
