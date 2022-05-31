import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int nbr = sc.nextInt();
        boolean isPrime = true;
        int iter = 1;

        if (nbr <= 1) {
            System.err.println("IllegalArgument");
            sc.close();
            System.exit(-1);
        } else if ((nbr % 2 == 0 || nbr % 3 == 0) && nbr > 3) {
            isPrime = false;
        } else {
            for (int i = 5; i * i <= nbr; i += 6, iter++) {
                if (nbr % i == 0 || nbr % (i + 2) == 0) {
                    isPrime = false;
                    break;
                }
            }
        }
        System.out.println(isPrime + " " + iter);
        sc.close();
    }
}