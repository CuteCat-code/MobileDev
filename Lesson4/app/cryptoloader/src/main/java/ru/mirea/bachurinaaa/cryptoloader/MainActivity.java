package ru.mirea.bachurinaaa.cryptoloader;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final int LOADER_ID = 1;
    private EditText editTextPhrase;
    private SecretKey secretKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPhrase = findViewById(R.id.editTextPhrase);
        Button buttonEncrypt = findViewById(R.id.buttonEncrypt);

        secretKey = generateKey();

        buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phrase = editTextPhrase.getText().toString();
                if (!phrase.isEmpty()) {
                    byte[] encryptedMessage = encryptMsg(phrase, secretKey); // Шифрование фразы

                    Bundle bundle = new Bundle();
                    bundle.putByteArray(MyLoader.ARG_WORD, encryptedMessage);
                    bundle.putByteArray(MyLoader.ARG_KEY, secretKey.getEncoded());

                    LoaderManager.getInstance(MainActivity.this).initLoader(LOADER_ID, bundle, MainActivity.this);
                }
            }
        });
    }

    private SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] encryptMsg(String message, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher.doFinal(message.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyLoader(this, args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String decryptedText) {
        if (decryptedText != null) {
            Toast.makeText(this, "Дешифрованный текст: " + decryptedText, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Ошибка дешифровки", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }
}