package ru.mirea.bachurinaaa.notebook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private EditText fileNameEditText;
    private EditText quoteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileNameEditText = findViewById(R.id.fileNameEditText);
        quoteEditText = findViewById(R.id.quoteEditText);
        Button saveButton = findViewById(R.id.saveButton);
        Button loadButton = findViewById(R.id.loadButton);

        // Запрашиваем разрешение на запись и чтение
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }

        // Обработчик нажатия на кнопку "Сохранить"
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuoteToFile();
            }
        });

        // Обработчик нажатия на кнопку "Загрузить"
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadQuoteFromFile();
            }
        });
    }

    private void saveQuoteToFile() {
        String fileName = fileNameEditText.getText().toString();
        String quote = quoteEditText.getText().toString();

        if (!fileName.isEmpty() && !quote.isEmpty()) {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(path, fileName + ".txt");
            try {
                if (!path.exists()) {
                    path.mkdirs(); // создаем директорию, если она не существует
                }
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                writer.write(quote);
                writer.close();
                Log.d("File Operation", "Сохранено: " + file.getAbsolutePath());
            } catch (Exception e) {
                Log.e("File Operation", "Ошибка записи в файл: " + e.getMessage());
            }
        }
    }

    private void loadQuoteFromFile() {
        String fileName = fileNameEditText.getText().toString();
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, fileName + ".txt");

        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                quoteEditText.setText(stringBuilder.toString().trim());
                reader.close();
                Log.d("File Operation", "Загружено: " + file.getAbsolutePath());
            } catch (Exception e) {
                Log.e("File Operation", "Ошибка чтения из файла: " + e.getMessage());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Permissions", "Разрешения получены");
            } else {
                Log.e("Permissions", "Разрешения не получены");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}