package ru.mirea.bachurinaaa.musicplayer;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.bachurinaaa.musicplayer.databinding.ActivityMusicPlayerBinding;

public class MusicPlayerActivity extends AppCompatActivity {

    private ActivityMusicPlayerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMusicPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.songTitle.setText("Название текущей песни");
        binding.artistName.setText("Имя исполнителя");

        binding.playPauseButton.setOnClickListener(v ->
                Toast.makeText(this, "Воспроизведение/Пауза", Toast.LENGTH_SHORT).show()
        );

        binding.prevButton.setOnClickListener(v ->
                Toast.makeText(this, "Предыдущая песня", Toast.LENGTH_SHORT).show()
        );

        binding.nextButton.setOnClickListener(v ->
                Toast.makeText(this, "Следующая песня", Toast.LENGTH_SHORT).show()
        );
    }
}