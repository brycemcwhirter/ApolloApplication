package tester;

import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import apollo.Enum.*;
import apollo.Objects.*;

public class Test {
	private static void createAndShowGUI() throws FileNotFoundException {
		// Create and set up the window.
		JFrame frame = new JFrame("Apollo Application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		JPanel panel = new JPanel();
		panel.setOpaque(true);
		// content panes must be opaque
		frame.setContentPane(panel);
		// Create and set menu bar

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main() {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
