package com.ytkoff.droidbenchmark;

/**
 * Created by KP on 10/4/15.
 */

public class BenchmarkResult {

    public String className;

    public double warmupDurationSecs;
    public long warmupCycles;
    public double warmupAvgTaskTimeNs;

    public double benchmarkDurationSecs;
    public long benchmarkCycles;
    public double benchmarkAvgTaskTimeNs;

}