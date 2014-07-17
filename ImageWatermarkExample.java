import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageWatermarkExample {
	/**
	 * Embeds a textual watermark over a source image to produce a watermarked
	 * one.
	 * 
	 * @param text
	 *            The text to be embedded as watermark.
	 * @param sourceImageFile
	 *            The source image file.
	 * @param destImageFile
	 *            The output image file.
	 */
	static void addTextWatermark(String text, File sourceImageFile,
			File destImageFile,int centerX,int centerY) {
		try {
			BufferedImage sourceImage = ImageIO.read(sourceImageFile);
			Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

			// initializes necessary graphic properties
			//AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
			//g2d.setComposite(alphaChannel);
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Times New Roman", Font.BOLD, 40));
			FontMetrics fontMetrics = g2d.getFontMetrics();
			Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

			// calculates the coordinate where the String is painted
			//centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
			//centerY = sourceImage.getHeight() / 2;

			// paints the textual watermark
			g2d.drawString(text, centerX, centerY);

			ImageIO.write(sourceImage, "png", destImageFile);
			g2d.dispose();

			System.out.println("The text watermark is added to the image.");

		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	/**
	 * Embeds an image watermark over a source image to produce a watermarked
	 * one.
	 * 
	 * @param watermarkImageFile
	 *            The image file used as the watermark.
	 * @param sourceImageFile
	 *            The source image file.
	 * @param destImageFile
	 *            The output image file.
	 */
	static void addImageWatermark(File watermarkImageFile,
			File sourceImageFile, File destImageFile) {
		try {
			BufferedImage sourceImage = ImageIO.read(sourceImageFile);
			BufferedImage watermarkImage = ImageIO.read(watermarkImageFile);

			// initializes necessary graphic properties
			Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
			AlphaComposite alphaChannel = AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, 0.3f);
			g2d.setComposite(alphaChannel);

			// calculates the coordinate where the image is painted
			int topLeftX = (sourceImage.getWidth() - watermarkImage.getWidth()) / 2;
			int topLeftY = (sourceImage.getHeight() - watermarkImage
					.getHeight()) / 2;

			// paints the image watermark
			g2d.drawImage(watermarkImage, topLeftX, topLeftY, null);

			ImageIO.write(sourceImage, "png", destImageFile);
			g2d.dispose();

			System.out.println("The image watermark is added to the image.");

		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	public static void main(String[] args) {
		File sourceImageFile = new File("icard.png");
		File destImageFile = new File("ab_wm.png");

		addTextWatermark("Jyoti Akhade", sourceImageFile, destImageFile,420,320);

		//destImageFile = new File("/home/manisha/image_watermarked.png");

		//File watermarkImageFile = new File("/home/manisha/logo.jpg");
		//addImageWatermark(watermarkImageFile, sourceImageFile, destImageFile);
	}
}
