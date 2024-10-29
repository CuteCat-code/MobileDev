package ru.mirea.bachurinaaa.securesharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {
    private TextView poetName;
    private ImageView poetImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        poetName = findViewById(R.id.poetName);
        poetImage = findViewById(R.id.poetImage);

        // Инициализация EncryptedSharedPreferences
        try {

            KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
            String mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);

            SharedPreferences secureSharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    mainKeyAlias,
                    getBaseContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            // Сохранение данных (например, имя поэта)
            secureSharedPreferences.edit().putString("poet_name", "Иосиф Бродский").apply();

            // Получение данных
            String result = secureSharedPreferences.getString("poet_name", "Неизвестно");
            poetName.setText(result);

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}
