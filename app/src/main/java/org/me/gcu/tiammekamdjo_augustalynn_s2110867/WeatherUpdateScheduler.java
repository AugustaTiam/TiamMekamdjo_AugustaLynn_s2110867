package org.me.gcu.tiammekamdjo_augustalynn_s2110867;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeatherUpdateScheduler {
    private static final int DEFAULT_UPDATE_HOUR = 8; // Default update hour

    private ScheduledExecutorService executorService;
    private int updateFrequencyHours;

    public WeatherUpdateScheduler(int updateFrequencyHours) {
        this.updateFrequencyHours = updateFrequencyHours;
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public WeatherUpdateScheduler(MainActivity mainActivity) {
    }

    public void start() {
        // Calculate initial delay to schedule the first update at the next occurrence of the default update hour
        long initialDelay = calculateInitialDelay();

        // Schedule the task to run periodically
        executorService.scheduleAtFixedRate(this::fetchWeatherData, initialDelay, updateFrequencyHours, TimeUnit.HOURS);
    }

    private void fetchWeatherData() {

    }

    private long calculateInitialDelay() {
        // Get the current hour of the day
        int currentHour = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentHour = java.time.LocalDateTime.now().getHour();
        }

        // Calculate the delay until the next update hour
        int nextUpdateHour = currentHour < DEFAULT_UPDATE_HOUR ? DEFAULT_UPDATE_HOUR : DEFAULT_UPDATE_HOUR + updateFrequencyHours;
        int hoursUntilNextUpdate = nextUpdateHour - currentHour;

        // Calculate the initial delay in milliseconds
        return hoursUntilNextUpdate * 60 * 60 * 1000;
    }

    public void stop() {
        // Shutdown the executor service when not needed
        executorService.shutdown();
    }
}
