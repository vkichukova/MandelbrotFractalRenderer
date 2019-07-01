package net.yaht.rsa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

/**
 * Contains tests for the CommandParser class
 *
 */
public class CommandParserTest {
	private static final double DELTA = 0.00001;
	private CommandParser commandParser = new CommandParser();

	/**
	 * Tests if the user input will be parsed correctly
	 * 
	 * @throws ParseException If a parsing exception occurs
	 */
	@Test
	public void testParseCommand() throws ParseException {
		String[] programArguments = { "-t", "3", "-s", "2048x1024", "-rect", "-2.0:2.0:-1.0:1.0", "-q", "-o",
				"file.png" };
		ImageProperties imageProperties = new ImageProperties();
		commandParser.parseCommand(programArguments, imageProperties);

		int expectedThreadsCount = 3;
		assertEquals(expectedThreadsCount, imageProperties.getThreadsCount());

		int expectedImageHeight = 1024;
		assertEquals(expectedImageHeight, imageProperties.getImageHeight());

		int expectedImageWidth = 2048;
		assertEquals(expectedImageWidth, imageProperties.getImageWidth());

		double expectedComplextPlaneHeight = 2.0;
		assertEquals(expectedComplextPlaneHeight, imageProperties.getComplexPlaneHeight(), DELTA);

		double expectedComplextPlaneWidth = 4.0;
		assertEquals(expectedComplextPlaneWidth, imageProperties.getComplexPlaneWidth(), DELTA);

		assertTrue(imageProperties.isQuietMode());

		String expectedOutputFileName = "file.png";
		assertEquals(expectedOutputFileName, imageProperties.getOutputFileName());
	}

	/**
	 * Tests whether the correct values will be used if there is no user input for
	 * the properties
	 * 
	 * @throws ParseException If a parsing exception occurs
	 */
	@Test
	public void testParseCommandWithDefaultValues() throws ParseException {
		String[] programArguments = {};
		ImageProperties imageProperties = new ImageProperties();
		commandParser.parseCommand(programArguments, imageProperties);

		int expectedThreadsCount = 1;
		assertEquals(expectedThreadsCount, imageProperties.getThreadsCount());

		int expectedImageHeight = 480;
		assertEquals(expectedImageHeight, imageProperties.getImageHeight());

		int expectedImageWidth = 640;
		assertEquals(expectedImageWidth, imageProperties.getImageWidth());

		double expectedComplextPlaneHeight = 4.0;
		assertEquals(expectedComplextPlaneHeight, imageProperties.getComplexPlaneHeight(), DELTA);

		double expectedComplextPlaneWidth = 4.0;
		assertEquals(expectedComplextPlaneWidth, imageProperties.getComplexPlaneWidth(), DELTA);

		assertFalse(imageProperties.isQuietMode());

		String expectedOutputFileName = "mandelbrot_fractal.png";
		assertEquals(expectedOutputFileName, imageProperties.getOutputFileName());
	}
}
