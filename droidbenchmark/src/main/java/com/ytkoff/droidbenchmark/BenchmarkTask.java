package com.ytkoff.droidbenchmark;

/**
 * Created by KP on 10/4/15.
 */

import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public abstract class BenchmarkTask {

    private static final String TAG = "DroidBenchmark";
    private static final DecimalFormat NUM_CYCLES_FORMATTER = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);




    protected void onPreExecute() {
        // Override me
    }

    protected abstract Object task();

    protected void onPostExecute() {
        // Override me
    }

    private void doExecution(long numTimes) {

        onPreExecute();

        for (int i = 0; i < numTimes; i++) {
            task();
        }

        onPostExecute();
    }

    BenchmarkResult execute(long numCycles) {

        BenchmarkResult result = new BenchmarkResult();
        result.className = getClass().getSimpleName();

        Log.v(TAG, "----------------------------------------------------------");
        Log.v(TAG, String.format("Starting benchmark. Class name: %s", result.className));

        long numWarmupCycles = 1000000L;

        long startTime = timestamp();
        doExecution(numWarmupCycles); // Warmup
        long endTime = timestamp();

        double elapsedTime = endTime - startTime;
        double avgTimePerTask = (double) elapsedTime / numWarmupCycles;

        result.warmupDurationSecs = elapsedTime / 1e9;
        result.warmupCycles = numWarmupCycles;
        result.warmupAvgTaskTimeNs = avgTimePerTask;
        Log.v(TAG, "-");
        Log.v(TAG, String.format("Warmup duration: %f seconds. Cycles: %s", result.warmupDurationSecs, NUM_CYCLES_FORMATTER.format(numWarmupCycles)));
        Log.v(TAG, String.format("Average time per task during warmup: %f nanoseconds", avgTimePerTask));

        startTime = System.nanoTime();
        doExecution(numCycles);
        endTime = System.nanoTime();

        elapsedTime = endTime - startTime;
        avgTimePerTask = (double) elapsedTime / numCycles;

        result.benchmarkDurationSecs = elapsedTime / 1e9;
        result.benchmarkCycles = numCycles;
        result.benchmarkAvgTaskTimeNs = avgTimePerTask;
        Log.v(TAG, "-");
        Log.v(TAG, String.format("Benchmarking duration: %f seconds. Cycles: %s", result.benchmarkDurationSecs, NUM_CYCLES_FORMATTER.format(numCycles)));
        Log.v(TAG, String.format("Average time per task during benchmarking: %f nanoseconds", avgTimePerTask));
        Log.v(TAG, "----------------------------------------------------------");

        return result;
    }

    private long timestamp() {
        final boolean apiLevel = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1);

        if (apiLevel) {
            return SystemClock.elapsedRealtimeNanos();
        }

        return System.nanoTime();
    }
}