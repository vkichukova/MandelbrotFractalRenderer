package net.yaht.rsa;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import net.yaht.rsa.logger.Logger;

public class ImageRenderer {

	private static final String IMAGE_FORMAT = "png";
	private static final Logger LOGGER = new Logger(ImageRenderer.class.getName(), Level.INFO);

	public void renderImage(ImageProperties imageProperties) throws IOException, InterruptedException {
		int imageWidth = imageProperties.getImageWidth();
		BufferedImage image = new BufferedImage(imageWidth, imageProperties.getImageHeight(),
				BufferedImage.TYPE_INT_RGB);
		ExecutorService executor = Executors.newFixedThreadPool(imageProperties.getThreadsCount());

		long startTime = Calendar.getInstance().getTimeInMillis();

		for (int column = 0; column < imageWidth; column++) {
			Runnable renderer = new ColumnRenderer(imageProperties, image, column);
			executor.execute(renderer);
		}

		executor.shutdown();
		executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);

		File out = new File(imageProperties.getOutputFileName());
		ImageIO.write(image, IMAGE_FORMAT, out);

		if (!imageProperties.isQuietMode()) {
			logProgramExecutionInfo(startTime);
		}
	}

	private void logProgramExecutionInfo(long startTime) {
		long endTime = Calendar.getInstance().getTimeInMillis();
		long diff = endTime - startTime;
		LOGGER.logMessage(Level.INFO, "Program execution time: " + diff);
	}
}
