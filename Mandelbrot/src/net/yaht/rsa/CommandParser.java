package net.yaht.rsa;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandParser {

	public void parseCommand(String[] args, ImageProperties imageProperties) throws ParseException {
		setNewPropertiesValues(args, imageProperties);
	}
	
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

		Option quietMode = Option.builder("q").longOpt("quiet").desc("Quiet mode (Do not print on console)").required(false)
				.build();
		options.addOption(quietMode);

		return options;
	}
}
