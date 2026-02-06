package com.example.tema8hilos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView txtTiempo;
    private Button btnIniciar, btnPausar, btnReiniciar;

    private boolean corriendo = false;
    private int segundos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTiempo = findViewById(R.id.txtTiempo);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnPausar = findViewById(R.id.btnPausar);
        btnReiniciar = findViewById(R.id.btnReiniciar);

        btnIniciar.setOnClickListener(v -> iniciar());
        btnPausar.setOnClickListener(v -> pausar());
        btnReiniciar.setOnClickListener(v -> reiniciar());
    }

    private void iniciar() {
        if (corriendo) return;

        corriendo = true;

        new Thread(() -> {
            while (corriendo) {
                runOnUiThread(() -> actualizarTiempo());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void pausar() {
        corriendo = false;
    }

    private void reiniciar() {
        corriendo = false;
        segundos = 0;
        txtTiempo.setText("00:00");
    }

    private void actualizarTiempo() {
        segundos++;

        int min = segundos / 60;
        int seg = segundos % 60;

        txtTiempo.setText(String.format(Locale.getDefault(), "%02d:%02d", min, seg));
    }
}
