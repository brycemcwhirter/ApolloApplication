package apollo;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller
 */
public class Controller implements ActionListener {

    public static void openPage() {

        JFrame frame = new JFrame("Apollo");
        frame.setSize(500, 500);

        JPanel panel = new JPanel();
        frame.add(panel);

        JLabel label = new JLabel("Apollo");
        label.setBounds(10, 20, 80, 25);
        panel.add(label);

        JButton newDatabase = new JButton("New Database");
        panel.add(newDatabase);

        JButton importDatabase = new JButton("Import Database");
        panel.add(importDatabase);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        openPage();
        System.out.println("hi");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e);

    }

}