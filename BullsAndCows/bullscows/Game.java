package bullscows;

import java.util.Scanner;
import java.util.Random;

public class Game {

	static String code;
	static int nb;
	static int availableNumbers;
	static int availableChars;
	static int ch;
	static Scanner sc = new Scanner(System.in);
	static Random random = new Random(System.nanoTime());

	public static void startGame() {
		boolean win = false;
		String resived;
		int turn = 1;
		int bulls = 0;
		int cows = 0;

		if (generateCode() == -1) {
			return;
		}
		System.out.println("Okay, let's start a game!");
		while (!win) {
			System.out.printf("Turn %d:\n", turn);
			resived = sc.next();
			for (int i = 0; i < resived.length(); i++) {
				for (int j = 0; j < code.length(); j++) {
					if (resived.charAt(i) == code.charAt(j)) {
						if (i == j) {
							bulls++;
						} else {
							cows++;
						}
						break;
					}
				}
			}
			System.out.printf("Grade: %d bull(s) and %d cow(s).\n", bulls, cows);
			if (bulls == nb) {
				win = true;
				System.out.println("Congratulations! You guessed the secret code.");
			} else {
				bulls = 0;
				cows = 0;
				turn++;
			}
		}
	}

	private static int checkNb(String resived) {
		for (int i = 0; i < resived.length(); i++) {
			if (!Character.isDigit(resived.charAt(i))) {
				System.out.printf("Error: \"%s\" isn't a valid number.\n", resived);
				return -1;
			}
		}
		return 0;
	}

	private static int parseNb() {
		String resived;

		System.out.println("Please, enter the secret code's length:");
		resived = sc.nextLine();
		if (checkNb(resived) == -1) {
			return -1;
		}
		nb = Integer.parseInt(resived);
		if (nb > 36) {
			System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.\n", nb);
			return -1;
		} else if (nb < 1) {
			System.out.printf("Error: can't generate a secret number with a length of %d.\n", nb);
			return -1;
		}
		return 0;
	}

	private static int parsePossibleChars() {
		String resived;

		System.out.println("Input the number of possible symbols in the code:");
		resived = sc.nextLine();
		if (checkNb(resived) == -1) {
			return -1;
		}
		ch = Integer.parseInt(resived);
		if (ch > 36) {
			System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
			return -1;
		} else if (ch < nb) {
			System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n", nb, ch);
			return -1;
		}
		return 0;
	}

	private static int generateCode() {

		if (parseNb() == -1 || parsePossibleChars() == -1) {
			return -1;
		}
		if (ch >= 10) {
			availableNumbers = 10;
			ch -= 10;
			availableChars = ch;
		} else {
			availableNumbers = ch;
			ch -= availableNumbers;
			availableChars = 0;
		}
		code = getCode(generateAvailableChars());
		System.out.print("The secret is prepared: ");
		for (int i = 0; i < nb; i++) {
			System.out.print("*");
		}
		System.out.printf(" (%c-%c", '0', availableNumbers + '0' - 1);
		if (availableChars > 0) {
			System.out.printf(", %c-%c).\n", 'a', availableChars + 'a' - 1);
		} else {
			System.out.println(").");
		}
		return 0;
	}

	private static StringBuilder generateAvailableChars() {
		StringBuilder str = new StringBuilder();
		char c = '0';

		for (int i = 0; i < availableNumbers; i++) {
			str.append(c++);
		}
		c = 'a';
		for (int i = 0; i < availableChars; i++) {
			str.append(c++);
		}
		return str;
	}

	private static String getCode(StringBuilder chars) {
		StringBuilder codeS = new StringBuilder();
		while (codeS.length() < nb) {
			codeS.append(randomChar(chars));
		}
		// System.out.println(codeS);
		return codeS.toString();
	}

	private static char randomChar(StringBuilder chars) {
		int randomNumber = random.nextInt(chars.length());
		char c = chars.charAt(randomNumber);
		chars.deleteCharAt(randomNumber);
		return c;
	}
}