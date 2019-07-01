package net.yaht.rsa;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Contains logic for parsing the image properties from the user input
 */
public class CommandParser {

	/**
	 * Sets the image properties entered from user, if any
	 * 
	 * @param args            Console arguments
	 * @param imageProperties Predefined properties, which already contain default
	 *                        values
	 * @throws ParseException if a parsing exception occurs
	 */
	public void parseCommand(String[] args, ImageProperties imageProperties) throws ParseException {
		setNewPropertiesValues(args, imageProperties);
	}

	/**
	 * Checks whether there are parsed options are present, and sets new values for
	 * the image properties
	 * 
	 * @param args            Console arguments
	 * @param imageProperties Predefined properties, which already contain default
	 *                        values
	 * @throws ParseException if a parsing exception occurs
	 */
	private void setNewPropertiesValues(String[] args, ImageProperties imageProperties) throws ParseException {
		Options commandOptions = setAvailableOptions();
		CommandLineParser commandLineParser = new DefaultParser();
		CommandLine commandLine = commandLineParser.parse(commandOptions, args);

		if (commandLine.hasOption('s')) {
			imageProperties.setImageSize(commandLine);
		}
		if (commandLine.hasOption('r')) {
			imageProperties.setComplexPlaneSize(commandLine);
		}
		if (commandLine.hasOption('t')) {
			imageProperties.setThreadsNumber(commandLine);
		}
		if (commandLine.hasOption('o')) {
			imageProperties.setOutputFile(commandLine);
		}
		if (commandLine.hasOption('q')) {
			imageProperties.setQuietMode();
		}
	}

	/**
	 * Sets the available options for the program with: short name, long name, value
	 * separator, description, arguments number and whether the option is required
	 * 
	 * @return object containing the defined options
	 */
	private Options setAvailableOptions() {
		Options options = new Options();

		Option imageSize = Option.builder("s").longOpt("size").valueSeparator('x').required(false)
				.desc("Image size in pixels").numberOfArgs(2).build();
		options.addOption(imageSize);

		Option rectSize = Option.builder("r").longOpt("rect").valueSeparator(':').required(false)
				.desc("Size of the complex plane").numberOfArgs(4).build();
		options.addOption(rectSize);

		Option threadsNum = Option.builder("t").longOpt("tasks").required(false).desc("Number of running threads")
				.numberOfArgs(1).build();
		options.addOption(threadsNum);

		Option outputFile = Option.builder("o").longOpt("output").required(false).desc("Name of the image output file")
				.numberOfArgs(1).build();
		options.addOption(outputFile);

		Option quietMode = Option.builder("q").longOpt("quiet").desc("Quiet mode (Do not print on console)")
				.required(false).build();
		options.addOption(quietMode);

		return options;
	}
}
