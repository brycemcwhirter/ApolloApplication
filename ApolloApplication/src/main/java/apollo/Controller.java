package apollo;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller
 */
public class Controller implements ActionListener {

    public static void openPage() {

        JFrame frame = new JFrame("Apollo");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        frame.add(panel);

        JLabel label = new JLabel("Apollo");
        label.setBounds(10, 20, 80, 25);
        panel.add(label);

    }

    public static void main(String[] args) {
        openPage();
        System.out.println("hi");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}