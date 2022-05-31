package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;
import javafx.scene.paint.*;
import java.io.IOException;
import com.beust.jcommander.*;
import com.diogonunes.jcolor.Attribute;

import static com.diogonunes.jcolor.Attribute.*;
import static com.diogonunes.jcolor.Ansi.colorize;

public class Main {

	@Parameters(separators = "=")
	private class Argv {
		@Parameter(names={"--black", "-b"})
		String blackColour;
		@Parameter(names={"--white", "-w"})
		String whiteColour;
	}

	public static void main(String[] args) {
		Main main = new Main();
		Argv argv = main.new Argv();
		try {
			JCommander.newBuilder()
				.addObject(argv)
				.build()
				.parse(args);
		} catch (ParameterException e) {
			System.out.println("Wrong parameters");
		}
		main.run(argv);
	}

	private void run(Argv argv) {
		ImageConverter fileToCharArr = new ImageConverter();
		try {
			int[][] arr = fileToCharArr.readBmpImage();
			if (arr == null) {
				System.out.println("Error");
				return;
			}
			Color wColor = Color.web(argv.whiteColour);
			Color bColor = Color.web(argv.blackColour);
			Attribute white = BACK_COLOR((int)(wColor.getRed() * 255),
										(int)(wColor.getGreen() * 255),
										(int)(wColor.getBlue() * 255));
			Attribute black = BACK_COLOR((int)(bColor.getRed() * 255),
										(int)(bColor.getGreen() * 255),
										(int)(bColor.getBlue() * 255));
			for (int[] ints : arr) {
				for (int aInt : ints) {
					if (aInt == 0) {
						System.out.print(colorize(" ", white));
					} else {
						System.out.print(colorize(" ", black));
					}
				}
				System.out.println("");
			}
		} catch (IOException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
}
