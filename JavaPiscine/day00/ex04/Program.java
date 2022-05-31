import java.util.Scanner;

public class Program {

    private static final int MAX_NBR_OF_CHAR_OCCUR = 999;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        if (str.length() == 0) {
            return;
        }
        char[] text = str.toCharArray();
        int uniq = nbrOfUniqSymb(text);
        char[] symb = new char[uniq];
        int[] symbCount = new int[uniq];


        for (int i = 0, j = 0; i < text.length; i++) {
            if (!isOccurred(symb, text[i], j)) {
                symb[j] = text[i];
                j++;
            }
        }
        for (int i = 0; i < symb.length; i++) {
            symbCount[i] = nbrOfAppearance(text, symb[i]);
        }
        sortArrays(symb, symbCount);
        printHistogram(symb, symbCount, uniq);
    }

    private static void printHistogram(char[] symb, int[] symbCount, int uniq) {
        double tenPercents = (double)symbCount[0] / 10;
        final int nbrOfRows = 12;
        int[] sharps;
        int sharp = 10;
        char temp;

        if (uniq < 10) {
            sharps = new int[uniq];
        } else {
            sharps = new int[10];
        }
        for (int i = 0; i < sharps.length; i++) {
            sharps[i] = (int)(symbCount[i] / tenPercents);
        }
        for (int i = 0; i < nbrOfRows; i++) {
            if (i + 1 < sharps.length) {
                if (symbCount[i] == symbCount[i + 1] && symb[i] > symb[i + 1]) {
                    temp = symb[i];
                    symb[i] = symb[i + 1];
                    symb[i + 1] = temp;
                }
            }
            if (i == 0) {
                for (int j = 0; j < sharps.length; j++) {
                    System.out.print(symbCount[j]);
                    if (j + 1 < symbCount.length && symbCount[j] != symbCount[j + 1]) {
                        break;
                    }
                }
            } else
                if (i < 12 - 1) {
                for (int j = 0; j < sharps.length; j++) {
                    if (sharps[j] >= sharp) {
                        System.out.print("  #");
                    } else if (sharps[j] == sharp - 1) {
                        if (symbCount[j] > 9) {
                            System.out.print(" ");
                        } else if (symbCount[j] < 10) {
                            System.out.print("  ");
                        }
                        System.out.print(symbCount[j]);
                    }
                }
                sharp--;
            } else {
                for (int j = 0; j < sharps.length; j++) {
                    System.out.print("  " + symb[j]);
                }
            }
            System.out.print("\n");
        }
    }

    private static void sortArrays(char[] symb, int[] symbCount) {
        char tempC;
        int tempI;

        for (int i = 1; i < symbCount.length; i++) {
            for (int j = i; j > 0; j--) {
                if (symbCount[j] > symbCount[j - 1]) {
                    tempI = symbCount[j];
                    tempC = symb[j];
                    symbCount[j] = symbCount[j - 1];
                    symbCount[j - 1] = tempI;
                    symb[j] = symb[j - 1];
                    symb[j - 1] = tempC;
                }
            }
        }
    }

    private static int nbrOfAppearance(char[] arr, char c) {
        int nbr = 0;

        for (char value : arr) {
            if (c == value) {
                nbr++;
            }
        }
        return nbr > MAX_NBR_OF_CHAR_OCCUR ? MAX_NBR_OF_CHAR_OCCUR : nbr;
    }

    private static boolean isOccurred(char[] arr, char c, int start) {
        boolean occurred = false;

        for (int i = start - 1; i >= 0; i--) {
            if (arr[i] == c) {
                occurred = true;
                break;
            }
        }
        return occurred;
    }

    private static int nbrOfUniqSymb(char[] arr) {
        int uniq = 0;

        for (int i = 0; i < arr.length; i++) {
            if (!isOccurred(arr, arr[i], i)) {
                uniq++;
            }
        }
        return uniq;
    }
}