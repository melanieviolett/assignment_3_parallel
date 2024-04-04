import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class TempSensor implements Runnable {
    private final List<Integer> temperatureReadings;
    private final Random random;

    public TempSensor(List<Integer> temperatureReadings, long seed) {
        this.temperatureReadings = temperatureReadings;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        // this simulates 1 hour of readings : (60 minutes)
        for (int i = 0; i < 60; i++) {
            // generates random temp from -100 to 70
            int temperature = random.nextInt(171) - 100;
            synchronized (temperatureReadings) {
                temperatureReadings.add(temperature);
            }
        }
    }
}

public class Problem2 {
    private static final int sensors = 8;
    private static final int hours = 3;
    private static final int reads_per_hour = 60;

    public static void main(String[] args) throws InterruptedException {
        List<Integer> temp_readings = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executor = Executors.newFixedThreadPool(sensors);

        for (int i = 0; i < sensors; i++) {
            executor.execute(new TempSensor(temp_readings, System.nanoTime() + i));
        }

        executor.shutdown();
        executor.awaitTermination(hours * reads_per_hour, TimeUnit.SECONDS);
        report(temp_readings);
    }

    private static void report(List<Integer> temp_readings) {

        List<List<Integer>> hourly_readings = new ArrayList<>();
        for (int i = 0; i < hours; i++) {
            int st = i * reads_per_hour;
            int end = (i + 1) * reads_per_hour;
            List<Integer> hour_reads = temp_readings.subList(st, end);
            hourly_readings.add(hour_reads);
        }

        for (int i = 0; i < hours; i++) {
            System.out.println("Hour " + (i + 1) + " Report:");
            List<Integer> hour_reads = hourly_readings.get(i);
            hourly_report(hour_reads);
            System.out.println();
        }
    }

    private static void hourly_report(List<Integer> hourReadings) {
        List<Integer> five_highest = hourReadings.subList(Math.max(0, hourReadings.size() - 5),
                hourReadings.size());

        List<Integer> five_lowest = hourReadings.subList(0, Math.min(5, hourReadings.size()));

        int maxdiff = Integer.MIN_VALUE;
        int maxdiff_startidx = 0;
        for (int i = 0; i < hourReadings.size() - 10; i++) {
            int difference = hourReadings.get(i + 10) - hourReadings.get(i);
            if (difference > maxdiff) {
                maxdiff = difference;
                maxdiff_startidx = i;
            }
        }
        List<Integer> maxdiff_int = hourReadings.subList(maxdiff_startidx, maxdiff_startidx + 10);

        System.out.println("Top 5 highest temps: " + five_highest);
        System.out.println("Top 5 lowest temps: " + five_lowest);
        System.out.println("10-minute interval with largest temp difference: " + maxdiff_int);
    }
}
