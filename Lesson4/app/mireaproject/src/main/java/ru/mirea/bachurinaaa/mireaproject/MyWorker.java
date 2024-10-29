package ru.mirea.bachurinaaa.mireaproject;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Execute your background task here
        try {
            // Simulate a long-running task
            Thread.sleep(5000); // Replace with your actual task logic
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success(); // Return success result
    }
}
