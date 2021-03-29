package apollo;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

/**
 * Controller
 */
public class Controller {

    public static void openPage() {

        // Created New JFrame
        JFrame frame = new JFrame("Apollo");
        frame.setSize(500, 500);

        // Created New JPanel
        JPanel panel = new JPanel();
        frame.add(panel);

        // Added a New JLabel. Don't know if this is necesary.
        JLabel label = new JLabel("Apollo");
        label.setBounds(10, 20, 80, 25);
        label.setBackground(Color.decode("#00A3E0"));
        panel.add(label);

        // New Database Button w/ Action Listener
        JButton newDatabase = new JButton("New Database");
        newDatabase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("New Database");

            }
        });
        panel.add(newDatabase);

        // Import Database Button w/ Action Listener
        JButton importDatabase = new JButton("Import Database");
        importDatabase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Import Database");
            }
        });
        panel.add(importDatabase);

        // Showing Frame
        // Just found this set location relative to
        // function that will put the frame to the center
        // every time
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.decode("#00A3E0"));
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        // Just Calling Open Page
        openPage();
        System.out.println("hi");
    }

}