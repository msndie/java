package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {
    @ParameterizedTest
    @ValueSource(ints = {2, 947, 199, 509, 641})
    void isPrimeForPrimes(int number) {
        NumberWorker nw = new NumberWorker();
        Assertions.assertTrue(nw.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 10, 15, 949, 10000})
    void isPrimeForNotPrimes(int number) {
        NumberWorker nw = new NumberWorker();
        Assertions.assertFalse(nw.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1})
    void isPrimeForIncorrectNumbers(int number) {
        NumberWorker nw = new NumberWorker();
        Assertions.assertThrows(NumberWorker.IllegalNumberException.class, () -> nw.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    void digitSumTest(int number, int sum) {
        NumberWorker nw = new NumberWorker();
        Assertions.assertEquals(sum, nw.digitSum(number));
    }
}
