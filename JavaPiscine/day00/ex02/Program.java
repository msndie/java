import java.util.Scanner;

public class Program {

    private static final int LAST_ELEMENT = 42;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int count = 0;
        int query;

        while (true) {
            query = sc.nextInt();
            if (query == LAST_ELEMENT) {
                break;
            } else if (isPrime(sumOfDigits(query))) {
                count++;
            }
        }
        sc.close();
        System.out.println("Count of coffee-request - " + count);
    }

    private static int sumOfDigits(int nbr) {
        int sum = 0;
        int tmp;

        while (nbr != 0) {
            tmp = nbr % 10;
            nbr /= 10;
            sum += tmp < 0 ? tmp * -1 : tmp;
        }
        return sum;
    }

    private static boolean isPrime(int nbr) {
        boolean isPrime = true;

        if (nbr <= 1) {
            isPrime = false;
        } else if ((nbr % 2 == 0 || nbr % 3 == 0) && nbr > 3) {
            isPrime = false;
        } else {
            for (int i = 5; i * i <= nbr; i += 6) {
                if (nbr % i == 0 || nbr % (i + 2) == 0) {
                    isPrime = false;
                    break;
                }
            }
        }
        return isPrime;
    }
}