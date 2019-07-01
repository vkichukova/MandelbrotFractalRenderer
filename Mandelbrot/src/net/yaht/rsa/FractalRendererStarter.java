package net.yaht.rsa;

import java.io.IOException;
import java.util.logging.Level;

import org.apache.commons.cli.ParseException;

import net.yaht.rsa.logger.Logger;

/**
 * Main class that starts the fractal rendering process
 */
public class FractalRendererStarter {
	/**
	 * Starts the fractal rendering process
	 * 
	 * @param args Console input arguments
	 */
	public static void main(String[] args) {
		Logger logger = new Logger(FractalRendererStarter.class.getName(), Level.SEVERE);

		try {
			CommandParser commandParser = new CommandParser();
			ImageProperties imageProperties = new ImageProperties();
			commandParser.parseCommand(args, imageProperties);
			ImageRenderer imageRenderer = new ImageRenderer();
			imageRenderer.renderImage(imageProperties);
		} catch (IOException | ParseException | InterruptedException e) {
			logger.logException(Level.SEVERE, e.getMessage(), e);
		}
	}

}
