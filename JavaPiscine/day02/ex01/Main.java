import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Error");
            return;
        }
        List<String> linesFirst;
        List<String> linesSecond;
        File file1 = new File(args[0]);
        File file2 = new File(args[1]);
        ArrayList<String> dictionary = new ArrayList<>();
        if ((linesFirst = readFile(file1)) == null) {
            return;
        }
        addWordsToDictionary(dictionary, linesFirst);
        if ((linesSecond = readFile(file2)) == null) {
            return;
        }
        addWordsToDictionary(dictionary, linesSecond);
        printDictionaryToFile(dictionary);
        Vector<Integer> freqFirst = countFrequencies(dictionary, linesFirst);
        Vector<Integer> freqSecond = countFrequencies(dictionary, linesSecond);
        calcSimilarity(freqFirst, freqSecond);
    }

    private static void printDictionaryToFile(ArrayList<String> dictionary) {
        String path = "dictionary.txt";
		try (FileOutputStream fileOutputStream = new FileOutputStream(path, false)) {
            for (String string : dictionary) {
                fileOutputStream.write((string + "\n").getBytes());
            }
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
    }

    private static void calcSimilarity(Vector<Integer> first, Vector<Integer> second) {
        double sum = 0;
        double sumA = 0;
        double sumB = 0;
        double similatiry;
        int a;
        int b;
        for (int i = 0; i < first.size(); i++) {
            a = first.get(i);
            sumA += a * a;
            b = second.get(i);
            sumB += b * b;
            sum += a * b;
        }
        similatiry = sum / (Math.sqrt(sumA) * Math.sqrt(sumB));
        System.out.printf("Similarity = %f\n", similatiry);
    }

    private static Vector<Integer> countFrequencies(ArrayList<String> dictionary, List<String> list) {
        Vector<Integer> vector = new Vector<>(dictionary.size());
        for (String str : dictionary) {
            vector.add(Collections.frequency(list, str));
        }
        return vector;
    }

    private static void addWordsToDictionary(ArrayList<String> dictionary, List<String> lines) {
        Iterator<String> iterator = lines.iterator();
        String tmp;
        while (iterator.hasNext()) {
            tmp = iterator.next();
            if (!dictionary.contains(tmp)) {
                dictionary.add(tmp);
            }
        }
    }

    private static List<String> readFile(File file) {
        String[] arr;
        try (Reader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader)) {
            List<String> listLines = bufferedReader.lines().collect(Collectors.toList());
            List<String> listWords = new LinkedList<>();
            for (String string : listLines) {
                arr = string.split(" ");
                for (String string2 : arr) {
                    listWords.add(string2);
                }
            }
            return listWords;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}