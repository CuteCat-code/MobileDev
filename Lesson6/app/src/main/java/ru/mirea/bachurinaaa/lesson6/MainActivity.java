package ru.mirea.bachurinaaa.lesson6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText groupNumber;
    private EditText studentNumber;
    private EditText favoriteMovie;
    private Button saveButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        groupNumber = findViewById(R.id.groupNumber);
        studentNumber = findViewById(R.id.studentNumber);
        favoriteMovie = findViewById(R.id.favoriteMovie);
        saveButton = findViewById(R.id.saveButton);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Загрузка сохраненных данных
        loadPreferences();

        // Сохранение данных при нажатии на кнопку
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
            }
        });
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("group_number", groupNumber.getText().toString());
        editor.putString("student_number", studentNumber.getText().toString());
        editor.putString("favorite_movie", favoriteMovie.getText().toString());
        editor.apply();
    }

    private void loadPreferences() {
        String savedGroupNumber = sharedPreferences.getString("group_number", "");
        String savedStudentNumber = sharedPreferences.getString("student_number", "");
        String savedFavoriteMovie = sharedPreferences.getString("favorite_movie", "");

        groupNumber.setText(savedGroupNumber);
        studentNumber.setText(savedStudentNumber);
        favoriteMovie.setText(savedFavoriteMovie);
    }
}