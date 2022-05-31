package edu.school21.printer.logic;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageConverter {

	private final String path;
	private final char black;
	private final char white;

	public ImageConverter(String path, char black, char white) {
		this.path = path;
		this.black = black;
		this.white = white;
	}

	public char[][] readBmpImage() throws IOException {
		BufferedImage image = ImageIO.read(new File(path));
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
