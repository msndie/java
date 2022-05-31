package edu.school21.printer.logic;

import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageConverter {

	private final int black = 1;
	private final int white = 0;

	public int[][] readBmpImage() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("image.bmp");
        if (inputStream == null) {
        	return null;
		}
		BufferedImage image = ImageIO.read(inputStream);
		int width = image.getWidth();
		int height = image.getHeight();
		int[][] array = new int[height][width];
		int	colour;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				colour = image.getRGB(x, y);
				if (colour == Color.BLACK.getRGB()) {
					array[y][x] = black;
				} else {
					array[y][x] = white;
				}
			}
		}
		return array;
	}

}
