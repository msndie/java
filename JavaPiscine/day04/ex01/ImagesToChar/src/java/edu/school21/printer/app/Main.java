package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Error");
			return;
		}
		if (args[0].length() != 1 || args[1].length() != 1) {
			System.out.println("Error");
			return;
		}
		ImageConverter fileToCharArr = new ImageConverter(args[0].charAt(0), args[1].charAt(0));
		try {
			char[][] arr = fileToCharArr.readBmpImage();
			for (int i = 0; i < arr.length; i++) {
				for (int j = 0; j < arr[i].length; j++) {
					System.out.print(arr[i][j]);
				}
				System.out.println("");
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
