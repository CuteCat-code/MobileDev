package ru.mirea.bachurinaaa.mireaproject;

import android.Manifest;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;

public class MicrophoneFragment extends Fragment {

    private MediaRecorder recorder;
    private String audioFilePath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_microphone, container, false);
        Button startButton = view.findViewById(R.id.startRecordingButton);
        Button stopButton = view.findViewById(R.id.stopRecordingButton);

        audioFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recorded_audio.3gp";

        startButton.setOnClickListener(v -> startRecording());
        stopButton.setOnClickListener(v -> stopRecording());
        return view;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(audioFilePath);

        try {
            recorder.prepare();
            recorder.start();
            Toast.makeText(getActivity(), "Recording started", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        Toast.makeText(getActivity(), "Recording stopped", Toast.LENGTH_SHORT).show();
    }
}
