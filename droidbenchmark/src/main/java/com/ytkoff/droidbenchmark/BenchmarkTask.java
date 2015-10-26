package com.ytkoff.droidbenchmark;

/**
 * Created by KP on 10/4/15.
 */

import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.ytkoff.droidbenchmark.Models.BenchmarkResult;
import com.ytkoff.droidbenchmark.Models.TimeInfo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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

    private void doExecution() {


        task();


    }

    BenchmarkResult execute(long numCycles) {

        BenchmarkResult result = new BenchmarkResult();

        result.className = getClass().getSimpleName();

        Log.v(TAG, "----------------------------------------------------------");
        Log.v(TAG, String.format("Starting benchmark. Class name: %s", result.className));

        long numWarmupCycles = 1000L;

        long totalElapsedTime = 0;
        for (int i = 0; i < numWarmupCycles; i++) {
            onPreExecute();
            TimeInfo timeInfo = new TimeInfo();
            timeInfo.startTime = timestamp();
            doExecution(); // Warmup
            timeInfo.endTime = timestamp();
            Long elapsedTime = timeInfo.endTime - timeInfo.startTime;
            totalElapsedTime = (long) (totalElapsedTime + elapsedTime);

        }
        double avgTimePerTask = (double) totalElapsedTime / numWarmupCycles;
        result.warmupDurationSecs = totalElapsedTime / 1e9;
        result.warmupCycles = numWarmupCycles;
        result.warmupAvgTaskTimeNs = avgTimePerTask;
        Log.v(TAG, "-");
        Log.v(TAG, String.format("Warmup duration: %f seconds. Cycles: %s", result.warmupDurationSecs, NUM_CYCLES_FORMATTER.format(numWarmupCycles)));
        Log.v(TAG, String.format("Average time per task during warmup: %f nanoseconds", avgTimePerTask));
  /////////////////Starting to calculate real benchmark after warmup;
        totalElapsedTime = 0;
        for (int i = 0; i < numCycles; i++) {
            onPreExecute();
            TimeInfo timeInfo = new TimeInfo();
            timeInfo.startTime = timestamp();
            doExecution();
            timeInfo.endTime = timestamp();
            Long elapsedTime = timeInfo.endTime - timeInfo.startTime;
            totalElapsedTime = (long) (totalElapsedTime + elapsedTime);

        }
        avgTimePerTask = (double) totalElapsedTime / numWarmupCycles;
        result.benchmarkDurationSecs = totalElapsedTime / 1e9;
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