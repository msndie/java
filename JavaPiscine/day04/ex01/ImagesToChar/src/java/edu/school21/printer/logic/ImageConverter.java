package edu.school21.printer.logic;

import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageConverter {

	private final char black;
	private final char white;

	public ImageConverter(char black, char white) {
		this.black = black;
		this.white = white;
	}

	public char[][] readBmpImage() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("image.bmp");
		BufferedImage image = ImageIO.read(inputStream);
		int width = image.getWidth();
		int height = image.getHeight();
		char[][] array = new char[height][width];
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
