package benchmark.calipus.com.calipus.Activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.ytkoff.droidbenchmark.Models.BenchmarkResult;
import com.ytkoff.droidbenchmark.DroidBenchmark;

import benchmark.calipus.com.calipus.R;
import benchmark.calipus.com.calipus.Tasks.QuicksortTask;

import static android.app.ProgressDialog.show;

public class BenchmarkChooser extends AppCompatActivity {
    Spinner taskSpinner;
    EditText warmupcuyclesEditText;
    EditText benchmarkCyclesEditText;
    EditText sizeOfDataSetEditText;
    EditText maxValueOfRandomInput;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benchmark_chooser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_benchmark_chooser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startBenchmark(View v) {
        new DroidBenchmark() {

            @Override
            public void onPreExecute() {
                mDialog = show(BenchmarkChooser.this, null, "Benchmarking…");
            }

            @Override
            public void onPostExecute(BenchmarkResult result) {
               // showResult(result);
                mDialog.dismiss();
            }

        }.benchmark(BenchmarkChooser.this, QuicksortTask.class, 500000L);
    }
}
