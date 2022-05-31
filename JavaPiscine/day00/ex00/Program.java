public class Program {

    public static void main(String[] args) {
        int length = 0;
        int sum = 0;
        int number;
        int tmp;

        number = 479598;
        tmp = number % 10;
        number /= 10;
        sum += tmp;
        tmp = number % 10;
        number /= 10;
        sum += tmp;
        tmp = number % 10;
        number /= 10;
        sum += tmp;
        tmp = number % 10;
        number /= 10;
        sum += tmp;
        tmp = number % 10;
        number /= 10;
        sum += tmp;
        tmp = number % 10;
        number /= 10;
        sum += tmp;
        System.out.println(sum);
    }
}