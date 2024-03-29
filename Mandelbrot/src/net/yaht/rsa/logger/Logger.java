package net.yaht.rsa.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/**
 * Contains setting up of a logger object
 */
public class Logger {
	private java.util.logging.Logger logger;
	private static Handler fileHandler;

	static {
		setUpFileHandler();
	}

	/**
	 * Sets logging output to file
	 */
	private static void setUpFileHandler() {
		try {
			fileHandler = new FileHandler("log/mandelbrot.log");
		} catch (SecurityException e) {
			throw new RuntimeException("SecurityException while setting up file handler", e);
		} catch (IOException e) {
			throw new RuntimeException("IOException while setting up file handler", e);
		}
		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);
		fileHandler.setLevel(Level.ALL);
	}

	/**
	 * Constructs a logger with given name and minimum level
	 * 
	 * @param className Name of the logger
	 * @param loggingLevel Minimum level to log on
	 */
	public Logger(String className, Level loggingLevel) {
		logger = java.util.logging.Logger.getLogger(className);
		logger.addHandler(fileHandler);
		logger.setUseParentHandlers(false);
		logger.setLevel(loggingLevel);
	}

	public void logException(Level loggingLevel, String message, Throwable thrown) {
		logger.log(loggingLevel, message, thrown);
	}

	public void logMessage(Level loggingLevel, String message) {
		logger.log(loggingLevel, message);
	}
}
