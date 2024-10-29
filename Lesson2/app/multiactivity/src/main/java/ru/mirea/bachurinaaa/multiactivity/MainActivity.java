package ru.mirea.bachurinaaa.multiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editTextText = findViewById(R.id.MyEditText);
    }
    public void onClickNewActivity(View view) {

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("key", "MIREA - БАЧУРИНА АЛЁНА АЛЕКСЕЕВНА");
        startActivity(intent);
    }

    public void onClickNewActivitySend(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("key", editTextText.getText().toString());
        startActivity(intent);
    }
}