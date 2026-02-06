package com.example.tema8servicios;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private MyBoundService myService;
    private boolean isBound = false;
    private TextView txtCounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtCounter = findViewById(R.id.txtCounter);
        Button btnBindService = findViewById(R.id.btnBindService);
        Button btnUnbindService = findViewById(R.id.btnUnbindService);
        Button btnGetCounter = findViewById(R.id.btnGetCounter);
// Botón para vincularse al servicio
        btnBindService.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyBoundService.class);
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        });
// Botón para desvincularse del servicio
        btnUnbindService.setOnClickListener(v -> {
            if (isBound) {
                unbindService(serviceConnection);
                isBound = false;
            }
        });
// Botón para obtener el valor del contador
        btnGetCounter.setOnClickListener(v -> {
            if (isBound) {
                txtCounter.setText("Contador: " + myService.getCounter());
            }
        });
    }
    // Conexión con el servicio
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBoundService.LocalBinder binder = (MyBoundService.LocalBinder) service;
            myService = binder.getService();
            isBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };
}