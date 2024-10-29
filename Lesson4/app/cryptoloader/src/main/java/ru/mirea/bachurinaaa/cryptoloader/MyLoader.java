package ru.mirea.bachurinaaa.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {
    public static final String ARG_WORD = "word";
    public static final String ARG_KEY = "key";

    private byte[] encryptedText;
    private byte[] secretKey;

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null) {
            encryptedText = args.getByteArray(ARG_WORD);
            secretKey = args.getByteArray(ARG_KEY);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        SystemClock.sleep(3000); // Эмуляция долгой операции
        if (encryptedText != null && secretKey != null) {
            try {
                SecretKey originalKey = new SecretKeySpec(secretKey, 0, secretKey.length, "AES");
                return decryptMsg(encryptedText, originalKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String decryptMsg(byte[] cipherText, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return new String(cipher.doFinal(cipherText));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}