package com.example.ciclodevidaactivdades;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Ejemplo", "Estoy en on create");
    }

    protected void onStart() {
        super.onStart();
        Log.i("Ejemplo", "Estoy en on start");
    }

    protected void onRestart() {
        super.onRestart();
        Log.i("Ejemplo", "Estoy en on restart");
    }

    protected void onResume() {
        super.onResume();
        Log.i("Ejemplo", "Estoy en on resume");
    }

    protected void onPause() {
        super.onPause();
        Log.i("Ejemplo", "Eestoy en on pause");
    }

    protected void onStop() {
        super.onStop();
        Log.i("Ejemplo", "Estoy en on stop");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i("Ejemplo", "Estoy en on destroy");
    }
}
