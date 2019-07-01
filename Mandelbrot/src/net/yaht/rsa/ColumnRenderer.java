package net.yaht.rsa;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.math3.complex.Complex;

import net.yaht.rsa.logger.Logger;

/**
 * Runnable that renders a given column of the image
 */
public class ColumnRenderer implements Runnable {
	private static final int MAX_ITERATIONS = 500;
	private static final Logger LOGGER = new Logger(ImageRenderer.class.getName(), Level.INFO);

	private static List<Integer> colors = new ArrayList<>();
	private ImageProperties imageProperties;
	private int columnIndex;
	private BufferedImage image;
	private String currentThreadName = Thread.currentThread().getName();

	static {
		fillColorsList();
	}

	/**
	 * Constructs a column renderer
	 * 
	 * @param imageProperties Already defined default properties for the image
	 * @param image           The image that will be rendered
	 * @param columnIndex     Index of the current column
	 */
	public ColumnRenderer(ImageProperties imageProperties, BufferedImage image, int columnIndex) {
		this.imageProperties = imageProperties;
		this.columnIndex = columnIndex;
		this.image = image;
	}

	/**
	 * Iterates the pixels in the column and calculates what color to use for each
	 * pixel
	 */
	@Override
	public void run() {

		double imageHeight = imageProperties.getImageHeight();
		double imageWidth = imageProperties.getImageWidth();
		double xMin = imageProperties.getxMin();
		double yMin = imageProperties.getyMin();
		double complexPlaneWidth = imageProperties.getComplexPlaneWidth();
		double complexPlaneHeight = imageProperties.getComplexPlaneHeight();

		if (!imageProperties.isQuietMode()) {
			logStartingInfo();
		}
		long startTime = Calendar.getInstance().getTimeInMillis();

		for (int row = 0; row < imageHeight; row++) {
			double realPart = (columnIndex / imageWidth) * complexPlaneWidth + xMin;
			double imaginaryPart = (row / imageHeight) * complexPlaneHeight + yMin;
			Complex startingComplexNumber = new Complex(realPart, imaginaryPart);

			int iterations = calculateIterations(startingComplexNumber);
			int color = calculateColor(iterations);
			image.setRGB(columnIndex, row, color);
		}

		if (!imageProperties.isQuietMode()) {
			logEndingInfo();
			logWorkingTimeInfo(startTime);
		}
	}

	/**
	 * Calculates iterations based on the position of the pixel against the
	 * Mandelbrot set
	 * 
	 * @param startingComplexNumber Complex number from which the formula will be
	 *                              calculated
	 * @return Number of iterations for calculating
	 */
	private int calculateIterations(Complex startingComplexNumber) {
		Complex newComplexNumber = startingComplexNumber;
		for (int i = 0; i < MAX_ITERATIONS; i++) {
			if (newComplexNumber.abs() > 2.0) {
				return i;
			}
			newComplexNumber = calculateFractalFormula(newComplexNumber, startingComplexNumber);
		}
		return MAX_ITERATIONS;
	}

	/**
	 * Applies the following formula for the 2 numbers: F(Z) = (Z + e^Z)^2 + C
	 * 
	 * @param z Complex number
	 * @param c Complex number
	 * @return Complex number - result of the applied formula
	 */
	private Complex calculateFractalFormula(Complex z, Complex c) {
		return (z.add(z.exp())).pow(2).add(c);
	}

	/**
	 * Gets the color depending on the distance between the point and the Mandelbrot
	 * set. If the point is in the set, then it is black, otherwise it is one of the
	 * predefined 10 colors of the color scheme.
	 * 
	 * @param iterations
	 * @return color for this pixel
	 */
	private int calculateColor(int iterations) {
		int color = 0x000000; // black color

		if (iterations > 0 && iterations < MAX_ITERATIONS) {
			int colorIndex = iterations % colors.size();
			return colors.get(colorIndex);
		}
		return color;
	}

	/**
	 * Logs start of column rendering
	 */
	private void logStartingInfo() {
		LOGGER.logMessage(Level.INFO, "Thread [" + currentThreadName + "] starts working on column:" + columnIndex);
	}

	/**
	 * Logs end of column rendering
	 */
	private void logEndingInfo() {
		LOGGER.logMessage(Level.INFO, "Thread [" + currentThreadName + "] ends working on column:" + columnIndex);
	}

	/**
	 * Logs information about the thread execution time
	 * @param startTime Starting time in milliseconds
	 */
	private void logWorkingTimeInfo(long startTime) {
		long endTime = Calendar.getInstance().getTimeInMillis();
		long runningTime = endTime - startTime;
		LOGGER.logMessage(Level.INFO, "Execution time for " + currentThreadName + ": " + runningTime);
	}

	/**
	 * The colors list is filled by a given color scheme
	 */
	private static void fillColorsList() {
		colors.add(0x3e1d0e);
		colors.add(0x0d0118);
		colors.add(0x070132);
		colors.add(0x050561);
		colors.add(0x00054d);
		colors.add(0x0a2575);
		colors.add(0x15499d);
		colors.add(0x2c6cba);
		colors.add(0x6ca6e0);
		colors.add(0xd3ecf8);
	}

}
