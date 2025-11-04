package com.example.edittext;

import android.media.Rating;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ProgressBar pb = findViewById(R.id.progressBarIndeterminate3);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            int v = 0;
            @Override
            public void run() {
                if (v<=100){
                    pb.setProgress(v+=5);
                    new Handler(Looper.getMainLooper()).postDelayed(this, 200);
                }
            }
        }, 200);

        String [] opciones = {"Opcion1", "Opcion2", "Opcion3", "Opcion4", "Opcion5"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,opciones);

        setContentView(R.layout.activity_main);
        Spinner miSpinner = (Spinner) findViewById(R.id.miSpinner);
        String[] valores = {"Star Wars", "Reservoir Dogs", "El Club de la lucha", "Snach: Cerdos y Diamantes"};
        miSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));

        miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Has seleccionado el valor:" + parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("Has seleccionado nada");
            }
        });

        Switch switch1 = (Switch) findViewById(R.id.switch1);
        TextView textView = (TextView) findViewById(R.id.textview);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textView.setText("Estado: Encendido");
                } else {
                    textView.setText("Estado: Apagado");
                }
            }
        });

        SeekBar seekBar = findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText("Valor: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //
            }
        });

        RatingBar ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                textView.setText("Calificación: "+rating);
            }
        });

    }

}