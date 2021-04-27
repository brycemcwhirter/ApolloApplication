package apollo;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Log {

	public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	static {
		try {
			InputStream configFile = Log.class.getClassLoader().getResourceAsStream("logger.properties");
			LogManager.getLogManager().readConfiguration(configFile);
			configFile.close();
		} catch (IOException ex) {
			System.out.println("WARNING: Could not open configuration file");
			System.out.println("WARNING: Logging not configured (console output only)");
		}
		logger.info("starting the app");
	}

}
