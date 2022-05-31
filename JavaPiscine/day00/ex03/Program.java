import java.util.Scanner;

public class Program {

    private static final int MAX_NBR_OF_WEEKS = 18;
    private static final int NBR_OF_TESTS = 5;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int weekCounter = 1;
        long min;
        long minScores = 0;
        String week;

        while (true) {
            week = sc.nextLine();
            if (week.equals("42")) {
                break;
            }
            if (!week.equals("Week " + weekCounter)) {
                System.err.println("IllegalArgument");
                sc.close();
                System.exit(-1);
            }
            min = readMinGrade(sc);
            for (int j = 1; j < weekCounter; j++) {
                min *= 10;
            }
            minScores += min;
            if (weekCounter == MAX_NBR_OF_WEEKS) {
                break;
            }
            weekCounter++;
        }
        sc.close();
        printStat(minScores, weekCounter);
    }

    private static void printStat(long minScores, int weekCounter) {
        int blocks;

        for (int i = 1; i < weekCounter; i++) {
            System.out.print("Week " + (i + 1) + " ");
            blocks = (int)(minScores % 10);
            minScores /= 10;
            for (int j = 0; j < blocks; j++) {
                System.out.print("=");
            }
            System.out.println(">");
        }
    }

    private static long readMinGrade(Scanner sc) {
        int i = 0;
        int grade = 0;
        int prevGrade;
        long min = 0;

        while (i < NBR_OF_TESTS && sc.hasNextInt()) {
            if (i > 0) {
                prevGrade = grade;
            }
            grade = sc.nextInt();
            if (grade > 9 || grade < 1) {
                System.err.println("IllegalArgument");
                sc.close();
                System.exit(-1);
            }
            if (i == 0) {
                min = grade;
            } else if (grade < min) {
                min = grade;
            }
            i++;
        }
        sc.nextLine();
        return min;
    }
}