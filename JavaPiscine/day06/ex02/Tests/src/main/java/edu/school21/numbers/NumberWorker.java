package edu.school21.numbers;

public class NumberWorker {

    static class IllegalNumberException extends RuntimeException {
        IllegalNumberException(String message) {
            super(message);
        }
    }

    public boolean isPrime(int number) throws IllegalNumberException {
        boolean isPrime = true;

        if (number <= 1) {
            throw new IllegalNumberException("Wrong number");
        } else if ((number % 2 == 0 || number % 3 == 0) && number > 3) {
            isPrime = false;
        } else {
            for (int i = 5; i * i <= number; i += 6) {
                if (number % i == 0 || number % (i + 2) == 0) {
                    isPrime = false;
                    break;
                }
            }
        }
        return isPrime;
    }

    public int digitSum(int number) {
        int sum = 0;
        int tmp;

        if (number < 0) {
            number *= -1;
        }
        while (number != 0) {
            tmp = number % 10;
            number /= 10;
            sum += tmp;
        }
        return sum;
    }
}
