package ru.mirea.bachurinaaa.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread {
    public Handler mHandler;
    private Handler mainHandler;

    public MyLooper(Handler mainThreadHandler) {
        mainHandler = mainThreadHandler;
    }

    @Override
    public void run() {
        Log.d("MyLooper", "Запуск потока MyLooper");
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String job = msg.getData().getString("JOB");
                int age = msg.getData().getInt("AGE");

                Log.d("MyLooper", "Получено сообщение: " + job + ", возраст: " + age);

                Message response = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", "Профессия: " + job + ", возраст: " + age);
                response.setData(bundle);

                try {
                    Thread.sleep(age * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mainHandler.sendMessage(response);
            }
        };
        Looper.loop();
    }
}
