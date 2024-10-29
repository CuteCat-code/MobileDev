package ru.mirea.bachurinaaa.activitylifecycle;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    object mCamera;
    TextView LOGV = findViewById(R.id.MegaLog);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.MegaLog), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        LOGV.setText("onCreate");
        Log.println(Log.INFO, "info" ,"onCreate");
    }

    @Override
    protected void onRestart() {
        LOGV.setText("onRestart");
        Log.println(Log.INFO, "info" ,"onRestart");
        super.onRestart();
    }

    @Override
    public void onResume() {

        super.onResume();
        // Производится инициализации камеры после получения фокуса экрана

        if (mCamera == null) {
            //initializeCamera(); //Метод работы с камерой
        }
        LOGV.setText("onResume");
        Log.println(Log.INFO, "info" ,"onResume");
    }

    @Override
    protected void onPause() {

        super.onPause();

        if (mCamera != null){
            //mCamera.release();
            mCamera = null;
        }

        LOGV.setText("onPause");
        Log.println(Log.INFO, "info" ,"onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mCamera != null){
            //mCamera.release();
            mCamera = null;
        }
        LOGV.setText("onDestroy");
        Log.println(Log.INFO, "info" ,"onDestroy");
    }
}