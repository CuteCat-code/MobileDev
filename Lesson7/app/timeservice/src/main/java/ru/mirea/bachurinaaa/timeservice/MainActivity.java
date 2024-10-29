package ru.mirea.bachurinaaa.timeservice;

import static android.content.ContentValues.TAG;

import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import ru.mirea.bachurinaaa.timeservice.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final String host = "time.nist.gov"; // или time-a.nist.gov
    private final int port = 13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetTimeTask timeTask = new GetTimeTask();
                timeTask.execute();
            }
        });
    }
    private class GetTimeTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String timeResult = "";
            try {
                Socket socket = new Socket(host, port);
                BufferedReader reader = SocketUtils.getReader(socket);
                reader.readLine(); // игнорируем первую строку
                timeResult = reader.readLine(); // считываем вторую строку
                Log.d(TAG,timeResult);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return timeResult;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            String[] parts = result.split(" ");
            String datePart = parts[1]; // "24-10-28"
            String timePart = parts[2]; // "16:59:42"

            String combinedDateTime = datePart + " " + timePart;

            SimpleDateFormat originalFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat desiredFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm:ss a", new Locale("ru"));

            try {
                Date date = originalFormat.parse(combinedDateTime);
                String formattedDateTime = desiredFormat.format(date);

                binding.textView.setText(formattedDateTime);
            } catch (ParseException e) {
                e.printStackTrace();
                binding.textView.setText("Неверный формат");
            }
        }
    }
}