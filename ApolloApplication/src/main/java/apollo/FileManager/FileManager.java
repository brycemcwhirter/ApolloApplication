package apollo.FileManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;

import apollo.Controller;
import apollo.Log;
import apollo.Objects.Event;
import apollo.Objects.PNM;
import apollo.Enum.Tier;

public class FileManager {

	/**
	 * importFile
	 * 
	 * This function asks the user for a file and imports the data into the rush
	 * class
	 * 
	 * @return List<PNM> the list of PNMs for the rush class
	 */
	public static List<PNM> importFile() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnVal = fc.showOpenDialog(fc);
		List<PNM> members = new ArrayList<PNM>();

		// Check if file is opened
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();

			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));

				String line = null;
				// Parse through file
				while ((line = reader.readLine()) != null) {
					String temp[] = line.split(",");

					// Add person to the table
					Controller.getModel().addRow(
							new Object[] { temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7] });
					PNM pnm = new PNM(temp[0], temp[1], temp[2], temp[3], Boolean.parseBoolean(temp[4]),
							Integer.parseInt(temp[5]), temp[6], Tier.valueOf(temp[7]));
					members.add(pnm);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			Log.logger.info("Opening: " + file.getName() + ".");
		} else {
			Log.logger.info("Open Command Cancelled by user.");
		}
		return members;
	}

	/**
	 * exportFile
	 * 
	 * This function asks the user for a file and exports the data from the rush
	 * class into the file
	 * 
	 */
	public static void exportFile() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnVal = fc.showOpenDialog(fc);

		// Check if file is open
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				FileWriter excel = new FileWriter(file);

				// Export the column names to the file
				for (int i = 0; i < Controller.getModel().getColumnCount(); i++) {
					excel.write(Controller.getModel().getColumnName(i) + ",");
				}
				excel.write("\n");

				// Export the rest of the table to the file
				for (int i = 0; i < Controller.getModel().getRowCount(); i++) {
					for (int j = 0; j < Controller.getModel().getColumnCount(); j++) {
						if (Controller.getModel().getValueAt(i, j) != null) {
							excel.write(Controller.getModel().getValueAt(i, j).toString() + ",");
						} else {
							excel.write(",");
						}
					}
					excel.write("\n");
				}
				// Close the file
				excel.close();
				Log.logger.info("PNMs Exported");

			} catch (IOException er) {
				Log.logger.warning(er.getMessage());
			}
		}

	}

	/**
	 * importEvent
	 * 
	 * This function asks the user for a file and imports the events and attendees
	 * into the rush class
	 * 
	 */
	public static void importEvents() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnVal = fc.showOpenDialog(fc);

		// Check if file is opened
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			BufferedReader reader = null;
			List<Event> events = new ArrayList<Event>();
			try {
				reader = new BufferedReader(new FileReader(file));

				String line = null;
				// Parse through file
				while ((line = reader.readLine()) != null) {
					String temp[] = line.split(",");
					Date d = new SimpleDateFormat("mm/dd/yyyy").parse(temp[1]);
					Event event = new Event(temp[0], d, temp[2]);
					for (int i = 3; i < temp.length; i++) {
						// Check if name is valid
						int index = Controller.getMainRushClass().findPerson(temp[i]);
						if (index != -1) {
							Controller.getMainRushClass().getMembers().get(index).getEventList().add(event);
							event.getAttendees().add(Controller.getMainRushClass().getMembers().get(index));
						}
					}
					events.add(event);
				}
				Controller.getMainRushClass().setEvents(events);
				Log.logger.info("Opening: " + file.getName() + ".");
				Log.logger.info("Event List Imported");
			} catch (IOException e1) {
				Log.logger.warning(e1.getMessage());
			} catch (ParseException e) {
				Log.logger.warning(e.getMessage());
			}
		}
	}

	/**
	 * importVouchList
	 * 
	 * This function asks the user for a file and imports the vouch list names of
	 * the PNMs into the rush class
	 * 
	 */
	public static void importVouchList() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnVal = fc.showOpenDialog(fc);

		// Check if file is opened
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));

				String line = null;
				// Parse through file
				while ((line = reader.readLine()) != null) {
					String temp[] = line.split(",");
					int index = Controller.getMainRushClass().findPerson(temp[0]);
					// Add each name to the PNMs vouch list
					for (int i = 1; i < temp.length; i++) {
						Controller.getMainRushClass().getMembers().get(index).getVouchList().add(temp[i]);
					}
				}
				Log.logger.info("Opening: " + file.getName() + ".");
				Log.logger.info("Vouch List Imported");
			} catch (IOException e1) {
				Log.logger.warning(e1.getMessage());
			}
		}
	}

	/**
	 * exportEventList
	 * 
	 * This function asks the user for a file and exports the events and attendees
	 * of the rush class into the file
	 * 
	 */
	public static void exportEventList() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnVal = fc.showOpenDialog(fc);

		// Check if file is opened
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				FileWriter excel = new FileWriter(file);
				DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
				// Writing each event to the file
				for (int i = 0; i < Controller.getMainRushClass().getEvents().size(); i++) {
					Event event = Controller.getMainRushClass().getEvents().get(i);
					excel.write(event.getName() + "," + dateFormat.format(event.getDate()) + "," + event.getLocation()
							+ ",");
					// writing the attendees to the file
					for (int j = 0; j < event.getAttendees().size(); j++) {
						excel.write(event.getAttendees().get(j).getName() + ",");
					}
					excel.write("\n");
				}
				excel.close();
				Log.logger.info("Event List Exported");
			} catch (IOException e1) {
				Log.logger.warning(e1.getMessage());
			}
		}
	}

	/**
	 * exportVouchList
	 * 
	 * This function asks the user for a file and imports the vouch list names of
	 * each PNM into the file
	 * 
	 */
	public static void exportVouchList() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnVal = fc.showOpenDialog(fc);

		// Check if file is opened
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				FileWriter excel = new FileWriter(file);
				// writing the members to the file
				for (int i = 0; i < Controller.getMainRushClass().getMembers().size(); i++) {
					PNM pnm = Controller.getMainRushClass().getMembers().get(i);
					excel.write(pnm.getName() + ",");
					// writing the names of the PNMs vouch list to the file
					for (int j = 0; j < pnm.getVouchList().size(); j++) {
						excel.write(pnm.getVouchList().get(j) + ",");
					}
					excel.write("\n");
				}
				excel.close();
				Log.logger.info("Vouch List Exported");
			} catch (IOException e1) {
				Log.logger.warning(e1.getMessage());
			}
		}
	}
}
