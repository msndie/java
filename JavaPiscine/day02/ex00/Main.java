import java.io.*;
import java.util.*;

public class Main {
	private static boolean firstRead = true;

	public static void main(String[] args) {
		Map<String, String> map;
		Scanner sc = new Scanner(System.in);
		String line;

		map = readSignatures();
		if (map == null) {
			sc.close();
			return;
		}
		while (sc.hasNextLine()) {
			line = sc.nextLine().trim();
			if (line.equals("42")) {
				break;
			} else if (line.matches("[\\w]+ [\\w]+")) {
				System.out.println("Error");
				break;
			} else {
				fileProcessing(line, map);
			}
		}
		sc.close();
	}

	public static void printInFile(String name) {
		String path = "result.txt";
		try (FileOutputStream fileOutputStream = new FileOutputStream(path, !firstRead)) {
			fileOutputStream.write(name.getBytes());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		firstRead = false;
	}

	private static void fileProcessing(String path, Map<String, String> map) {
		StringBuilder signature = new StringBuilder();
		String signatureTmp;
		int maxLen = 0;
		int count = 0;
		int i;

		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getValue().length() > maxLen) {
				maxLen = entry.getValue().length();
			}
		}
		try (FileInputStream fileInputStream = new FileInputStream(path)) {
			while ((i = fileInputStream.read()) != -1) {
				String hex = Integer.toHexString(i);
				signature.append(" ");
				if (hex.length() == 1) {
					signature.append("0");
				}
				signature.append(hex);
				count += 2;
				if (count > maxLen)
					break;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		if (signature.length() != 0) {
			signatureTmp = signature.toString().trim();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (signatureTmp.startsWith(entry.getValue().toLowerCase())) {
					printInFile(entry.getKey() + "\n");
					System.out.println("PROCESSED");
					return;
				}
			}
		}
		System.out.println("UNDEFINED");
	}

	private static Map<String, String> readSignatures() {
		File file = new File("signatures.txt");
		ArrayList<String> strs = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		ListIterator<String> iterator;
		String tmp;

		try (Scanner sc = new Scanner(file)) {
			while (sc.hasNext()) {
				strs.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("No file found: signatures.txt");
		}
		iterator = strs.listIterator();
		while (iterator.hasNext()) {
			tmp = iterator.next();
			if (!tmp.matches("\\A\\p{ASCII}*\\z")) {
				System.out.println("Error");
				return null;
			}
			if (splitLine(tmp, map) == 1) {
				System.out.println("Error");
				return null;
			}
		}
		return map;
	}

	private static int splitLine(String str, Map<String, String> map) {
		String[] strs = str.split(",");
		if (strs.length != 2) {
			return 1;
		}
		map.putIfAbsent(strs[0], strs[1].trim());
		return 0;
	}
}