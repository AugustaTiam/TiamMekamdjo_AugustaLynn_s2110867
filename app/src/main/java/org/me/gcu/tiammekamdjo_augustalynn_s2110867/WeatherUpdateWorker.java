package org.me.gcu.tiammekamdjo_augustalynn_s2110867;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class WeatherUpdateWorker extends Worker {

    public WeatherUpdateWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {

        try {

            Thread.sleep(5000);

            return Result.success();
        } catch (InterruptedException e) {
            e.printStackTrace();

            return Result.failure();
        }
    }
}

