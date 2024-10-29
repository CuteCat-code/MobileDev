package ru.mirea.bachurinaaa.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.bachurinaaa.looper.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyLooper myLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String result = msg.getData().getString("result");
                Log.d(MainActivity.class.getSimpleName(), "Результат из MyLooper: " + result);
            }
        };

        // Запуск MyLooper
        myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();

        binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String job = binding.editTextJob.getText().toString();
                int age;
                try {
                    age = Integer.parseInt(binding.editTextAge.getText().toString());
                } catch (NumberFormatException e) {
                    age = 0;
                }

                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("JOB", job);
                bundle.putInt("AGE", age);
                msg.setData(bundle);

                if (myLooper.mHandler != null) {
                    myLooper.mHandler.sendMessage(msg);
                }
            }
        });
    }
}