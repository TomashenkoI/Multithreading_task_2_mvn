package ua.goit.java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * Created by 7 on 09.07.2016.
 */
public class SquareSumImpl implements SquareSum {

    @Override
    public long getSquareSum(int[] values, int numberOfThreads) {

        Long result = 0L;
        int countOfElements = values.length / numberOfThreads;
        Phaser phaser = new Phaser(numberOfThreads);

        List<Callable<Long>> callable = new ArrayList<>();
        IntStream.range(0, numberOfThreads).forEach(i -> callable.add(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " started");

            long threadResult = 0L;
            int firstIndex;
            int lastIndex;

            if (i == 0) {
                firstIndex = 0;
            } else {
                firstIndex = (i * countOfElements) + 1;
            }

            if (i == (values.length - 1)) {
                lastIndex = values.length;
            } else {
                lastIndex = (i + 1) * countOfElements;
            }

            System.out.println("First index of " + name + " is " + firstIndex);
            System.out.println("Last index of " + name + " is " + lastIndex);

            for (int j = firstIndex; j < lastIndex; j++) {
                threadResult += Math.pow(values[j], 2);
            }

            System.out.println("Result of " + name + " is " + threadResult);

            phaser.arriveAndAwaitAdvance();

            return threadResult;
        }));

        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            List<Future<Long>> sum = executor.invokeAll(callable);
            for (Future<Long> f : sum) {
                result = result + f.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return result;
    }
}
