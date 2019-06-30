package net.yaht.rsa;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.math3.complex.Complex;

import net.yaht.rsa.logger.Logger;

public class ColumnRenderer implements Runnable {
	private static final int MAX_ITERATIONS = 500;
	private static final Logger LOGGER = new Logger(ImageRenderer.class.getName(), Level.INFO);

	private static List<Integer> colors = new ArrayList<>();
	private ImageProperties imageProperties;
	private int columnNumber;
	private BufferedImage image;
	private String currentThreadName = Thread.currentThread().getName();

	static {
		fillColorsList();
	}

	public ColumnRenderer(ImageProperties imageProperties, BufferedImage image, int columnNumber) {
		this.imageProperties = imageProperties;
		this.columnNumber = columnNumber;
		this.image = image;
	}

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
			double realPart = (columnNumber / imageWidth) * complexPlaneWidth + xMin;
			double imaginaryPart = (row / imageHeight) * complexPlaneHeight + yMin;
			Complex startingComplexNumber = new Complex(realPart, imaginaryPart);

			int iterations = calculateIterations(startingComplexNumber);
			int color = calculateColor(iterations);
			image.setRGB(columnNumber, row, color);
		}

		if (!imageProperties.isQuietMode()) {
			logEndingInfo();
			logWorkingTimeInfo(startTime);
		}
	}

	private void logStartingInfo() {
		LOGGER.logMessage(Level.INFO, "Thread [" + currentThreadName + "] starts working on column:" + columnNumber);
	}

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

	private static Complex calculateFractalFormula(Complex z, Complex c) {
		// formula is: F(Z) = (Z + e^Z)^2 + C
		return (z.add(z.exp())).pow(2).add(c);
	}

	/**
	 * The color depends on the distance between the point and the mandelbrot set
	 * 
	 * @param iterations
	 * @return
	 */
	private int calculateColor(int iterations) {
		int color = 0x000000; // black color

		if (iterations > 0 && iterations < MAX_ITERATIONS) {
			int colorIndex = iterations % colors.size();
			return colors.get(colorIndex);
		}
		return color;
	}

	private void logEndingInfo() {
		LOGGER.logMessage(Level.INFO, "Thread [" + currentThreadName + "] ends working on column:" + columnNumber);
	}

	private void logWorkingTimeInfo(long startTime) {
		long endTime = Calendar.getInstance().getTimeInMillis();
		long diff = endTime - startTime;
		LOGGER.logMessage(Level.INFO, "Execution time for " + currentThreadName + ": " + diff);
	}

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
