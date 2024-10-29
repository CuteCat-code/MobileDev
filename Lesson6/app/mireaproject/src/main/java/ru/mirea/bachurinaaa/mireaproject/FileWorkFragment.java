package ru.mirea.bachurinaaa.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FileWorkFragment extends Fragment {

    private EditText fileEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_work, container, false);

        fileEditText = view.findViewById(R.id.fileEditText);
        FloatingActionButton fab = view.findViewById(R.id.fab);

        // Обработчик нажатия на FAB, который открывает диалог создания записи
        fab.setOnClickListener(v -> {
            showEncryptionDialog();
        });

        return view;
    }

    private void showEncryptionDialog() {
        // Логика шифрования текста (например, простая замена символов или базовое шифрование)
        String originalText = fileEditText.getText().toString();
        String encryptedText = encryptText(originalText);

        // Отобразить зашифрованный текст пользователю
        fileEditText.setText(encryptedText);
        Toast.makeText(getContext(), "Текст зашифрован!", Toast.LENGTH_SHORT).show();
    }

    private String encryptText(String text) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : text.toCharArray()) {
            encrypted.append((char) (c + 3)); // Простое шифрование сдвигом
        }
        return encrypted.toString();
    }
}