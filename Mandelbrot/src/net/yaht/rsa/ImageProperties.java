package net.yaht.rsa;

import org.apache.commons.cli.CommandLine;

public class ImageProperties {
	/**
	 * Default values for the properties of the fractal image
	 */
	private int imageWidth = 640;
	private int imageHeight = 480;
	private double xMin = -2.0;
	private double xMax = 2.0;
	private double yMin = -2.0;
	private double yMax = 2.0;
	private double complexPlaneWidth = xMax - xMin;
	private double complexPlaneHeight = yMax - yMin;
	private int threadsCount = 1;
	private String outputFileName = "mandelbrot_fractal.png";
	private boolean quietMode = false;

	public void setQuietMode() {
		quietMode = true;
	}

	public void setOutputFile(CommandLine line) {
		outputFileName = line.getOptionValue('o');
	}

	public void setThreadsNumber(CommandLine line) {
		threadsCount = Integer.parseInt(line.getOptionValue('t'));
	}

	public void setComplexPlaneSize(CommandLine line) {
		xMin = Double.parseDouble(line.getOptionValues('r')[0]);
		xMax = Double.parseDouble(line.getOptionValues('r')[1]);
		yMin = Double.parseDouble(line.getOptionValues('r')[2]);
		yMax = Double.parseDouble(line.getOptionValues('r')[3]);
		complexPlaneWidth = xMax - xMin;
		complexPlaneHeight = yMax - yMin;
	}

	public void setImageSize(CommandLine line) {
		imageWidth = Integer.parseInt(line.getOptionValues('s')[0]);
		imageHeight = Integer.parseInt(line.getOptionValues('s')[1]);
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public double getxMin() {
		return xMin;
	}

	public double getxMax() {
		return xMax;
	}

	public double getyMin() {
		return yMin;
	}

	public double getComplexPlaneWidth() {
		return complexPlaneWidth;
	}

	public double getComplexPlaneHeight() {
		return complexPlaneHeight;
	}

	public int getThreadsCount() {
		return threadsCount;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public boolean isQuietMode() {
		return quietMode;
	}
}
