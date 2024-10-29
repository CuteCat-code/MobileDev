package ru.mirea.bachurinaaa.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.bachurinaaa.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Thread(new Runnable() {
                    public void run() {
                        int numberThread = counter++;
                        //МОЙ НОМЕР ГРУППЫ: БИСО-03-19, НОМЕР ПО СПИСКУ: 2, МОЙ ЛЮБИМЫЙ ФИЛЬМ: Дьявол носит прада
                        Log.d("ThreadProject", String.format("Запущен поток № %d студентом группы № %s номер по списку № %d",
                                numberThread, "БИСО-03-19", 2));


                        long endTime = System.currentTimeMillis() + 20 * 1000;


                        while (System.currentTimeMillis() < endTime) {
                            synchronized (this) {
                                try {
                                    wait(endTime - System.currentTimeMillis());
                                    Log.d(MainActivity.class.getSimpleName(), "Endtime: " + endTime);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                        Log.d("ThreadProject", "Выполнен поток № " + numberThread);
                    }
                }).start(); // Запуск потока
            }
        });
    }
}