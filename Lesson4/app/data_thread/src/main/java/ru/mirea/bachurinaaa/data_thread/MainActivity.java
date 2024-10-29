package ru.mirea.bachurinaaa.data_thread;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.TimeUnit;

import ru.mirea.bachurinaaa.data_thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Runnable runn1 = new Runnable() {
            public void run() {
                binding.tvInfo.append("Запуск runn1 через runOnUiThread\n");
                Log.d("data_thread", "runn1 выполнен через runOnUiThread");
            }
        };

        final Runnable runn2 = new Runnable() {
            public void run() {
                binding.tvInfo.append("Запуск runn2 через post\n");
                Log.d("data_thread", "runn2 выполнен через post");
            }
        };

        final Runnable runn3 = new Runnable() {
            public void run() {
                binding.tvInfo.append("Запуск runn3 через postDelayed (задержка 2 сек)\n");
                Log.d("data_thread", "runn3 выполнен через postDelayed");
            }
        };

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    runOnUiThread(runn1);

                    TimeUnit.SECONDS.sleep(1);
                    binding.tvInfo.post(runn2);

                    binding.tvInfo.postDelayed(runn3, 2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
    /*
    Запуск runn1 через runOnUiThread
    Запуск runn2 через post
    Запуск runn3 через postDelayed (задержка 2 сек)
     */
}