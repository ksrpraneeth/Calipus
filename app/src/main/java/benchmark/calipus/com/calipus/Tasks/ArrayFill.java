package benchmark.calipus.com.calipus.Tasks;

import com.ytkoff.droidbenchmark.BenchmarkTask;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by KP on 10/4/15.
 */
public class ArrayFill extends BenchmarkTask {
    int randomNo;

    public int getRandomNo() {
        randomNo = randInt(10, 1000);
        return randomNo;
    }

    public void setRandomNo(int randomNo) {
        this.randomNo = randomNo;
    }

    public static int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        setRandomNo(getRandomNo());
    }

    @Override
    protected Object task() {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(randomNo);
        return integers;
    }
}
