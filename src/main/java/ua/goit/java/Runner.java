package ua.goit.java;

import java.util.Random;
import java.util.stream.IntStream;

public class Runner {

    public static void main(String[] args) {
        long result = 0L;
        Random random = new Random();
        int countOfElements = 263;
        int countOfThreads = 4;
        int[] array = new int[countOfElements];

        IntStream.range(0, countOfElements).forEach (i -> array[i] = random.nextInt(100) );

        result = new SquareSumImpl().getSquareSum(array, countOfThreads);
        System.out.println (" Total result = " + result);
    }
}