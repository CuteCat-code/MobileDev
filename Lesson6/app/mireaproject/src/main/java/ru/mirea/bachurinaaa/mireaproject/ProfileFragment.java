package ru.mirea.bachurinaaa.mireaproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private EditText nameEditText, ageEditText, emailEditText;
    private Button saveButton;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Инициализируем элементы
        nameEditText = view.findViewById(R.id.nameEditText);
        ageEditText = view.findViewById(R.id.ageEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        saveButton = view.findViewById(R.id.saveButton);

        // Подключение к SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("UserProfile", getContext().MODE_PRIVATE);

        // Загрузка данных, если они были сохранены
        loadProfileData();

        // Сохранение данных в SharedPreferences при нажатии на кнопку
        saveButton.setOnClickListener(v -> saveProfileData());

        return view;
    }

    private void loadProfileData() {
        nameEditText.setText(sharedPreferences.getString("name", ""));
        ageEditText.setText(sharedPreferences.getString("age", ""));
        emailEditText.setText(sharedPreferences.getString("email", ""));
    }

    private void saveProfileData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", nameEditText.getText().toString());
        editor.putString("age", ageEditText.getText().toString());
        editor.putString("email", emailEditText.getText().toString());
        editor.apply();
    }
}