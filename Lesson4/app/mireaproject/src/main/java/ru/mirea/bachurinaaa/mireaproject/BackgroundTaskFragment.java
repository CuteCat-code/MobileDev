package ru.mirea.bachurinaaa.mireaproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BackgroundTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BackgroundTaskFragment extends Fragment {

    private WorkManager workManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_background_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        workManager = WorkManager.getInstance(requireContext());

        Button startButton = view.findViewById(R.id.startButton);
        TextView statusTextView = view.findViewById(R.id.statusTextView);

        startButton.setOnClickListener(v -> {
            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                    .setInputData(new Data.Builder().putString("key", "value").build()) // Passing data to Worker
                    .build();



            workManager.enqueue(workRequest);
            statusTextView.setText("Задача запущена");

            workManager.getWorkInfoByIdLiveData(workRequest.getId()).observe(getViewLifecycleOwner(), workInfo -> {
                if (workInfo != null && workInfo.getState().isFinished()) {
                    statusTextView.setText("Задача закончена");
                }
            });
        });



    }
}