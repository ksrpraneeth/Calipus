package benchmark.calipus.com.calipus.Tasks;

import com.ytkoff.droidbenchmark.BenchmarkTask;

import java.util.Random;

import benchmark.calipus.com.calipus.Algorithms.QuickSort;

/**
 * Created by KP on 10/4/15.
 */
public class QuicksortTask extends BenchmarkTask {
    private int[] numbers;
    private final static int SIZE = 100;
    private final static int MAX = 100;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        numbers = new int[SIZE];
        Random generator = new Random();
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = generator.nextInt(MAX);
        }
    }

    @Override
    protected Object task() {
        QuickSort sorter = new QuickSort();
        sorter.sort(numbers);
        return numbers;
    }
}
