package benchmark.calipus.com.calipus.Activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ytkoff.droidbenchmark.Models.BenchmarkResult;
import com.ytkoff.droidbenchmark.DroidBenchmark;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import benchmark.calipus.com.calipus.R;
import benchmark.calipus.com.calipus.Tasks.QuicksortTask;

import static android.app.ProgressDialog.show;

public class MainActivity extends AppCompatActivity {
    private static final DecimalFormat NUM_CYCLES_FORMATTER = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);

    private Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    private void showResult(BenchmarkResult result) {

        ((TextView) findViewById(R.id.tv_class_name)).setText(result.className);

        ((TextView) findViewById(R.id.tv_warmup_duration)).setText(String.valueOf(result.warmupDurationSecs)+ " Sec");
        ((TextView) findViewById(R.id.tv_warmup_cycles)).setText(NUM_CYCLES_FORMATTER.format(result.warmupCycles));
        ((TextView) findViewById(R.id.tv_warmup_avg_time)).setText(String.valueOf(result.warmupAvgTaskTimeNs)+ " Nano Sec");

        ((TextView) findViewById(R.id.tv_benchmarking_duration)).setText(String.valueOf(result.benchmarkDurationSecs)+" Sec");
        ((TextView) findViewById(R.id.tv_benchmarking_cycles)).setText(NUM_CYCLES_FORMATTER.format(result.benchmarkCycles));
        ((TextView) findViewById(R.id.tv_benchmarking_avg_time)).setText(String.valueOf(result.benchmarkAvgTaskTimeNs) + " Nano Sec");
    }
    public void startBenchmark(View v) {
        new DroidBenchmark() {

            @Override
            public void onPreExecute() {
                mDialog = show(MainActivity.this, null, "Benchmarking…");
            }

            @Override
            public void onPostExecute(BenchmarkResult result) {
                // showResult(result);
                mDialog.dismiss();
                showResult(result);
            }

        }.benchmark(MainActivity.this, QuicksortTask.class, 5000000L);
    }

}
