package com.example.practica2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.graphics.Color;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextCuenta;
    CheckBox checkBoxPropina;
    SeekBar seekBarPropina;
    TextView textViewSeekValue, textViewResultado;
    RadioGroup radioGroupPago;
    RatingBar ratingBarServicio;
    Button btnCalcular;
    AutoCompleteTextView autoCamarero;

    int porcentajePropina = 10; // valor inicial

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias a los elementos del layout
        editTextCuenta = findViewById(R.id.editTextCuenta);
        checkBoxPropina = findViewById(R.id.checkBoxPropina);
        seekBarPropina = findViewById(R.id.seekBarPropina);
        textViewSeekValue = findViewById(R.id.textViewSeekValue);
        radioGroupPago = findViewById(R.id.radioGroupPago);
        ratingBarServicio = findViewById(R.id.ratingBarServicio);
        btnCalcular = findViewById(R.id.btnCalcular);
        textViewResultado = findViewById(R.id.textViewResultado);
        autoCamarero = findViewById(R.id.autoCamarero);

        // Lista de camareros para el AutoCompleteTextView
        ArrayList<String> camareros = new ArrayList<>();
        camareros.add("Carlos");
        camareros.add("María");
        camareros.add("Lucía");
        camareros.add("Javier");
        camareros.add("Ana");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                camareros
        );
        autoCamarero.setAdapter(adapter);

        // Cambiar el texto del porcentaje de propina cuando se mueva el SeekBar
        seekBarPropina.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                porcentajePropina = progress;
                textViewSeekValue.setText("Propina: " + porcentajePropina + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Botón para calcular el total
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textoCuenta = editTextCuenta.getText().toString().trim();

                // Validar que se haya introducido algo
                if (textoCuenta.isEmpty()) {
                    textViewResultado.setTextColor(Color.RED);
                    textViewResultado.setText("⚠️ Introduce el total de la cuenta.");
                    return;
                }

                double cuenta;
                try {
                    cuenta = Double.parseDouble(textoCuenta);
                } catch (NumberFormatException e) {
                    textViewResultado.setTextColor(Color.RED);
                    textViewResultado.setText("❌ Valor no válido. Escribe un número.");
                    return;
                }

                if (cuenta <= 0) {
                    textViewResultado.setTextColor(Color.RED);
                    textViewResultado.setText("❌ La cuenta debe ser mayor que 0.");
                    return;
                }

                // Calcular total con propina
                double total = cuenta;
                if (checkBoxPropina.isChecked()) {
                    total += cuenta * (porcentajePropina / 100.0);
                }

                // Obtener metodo de pago
                int idSeleccionado = radioGroupPago.getCheckedRadioButtonId();
                RadioButton radioSeleccionado = findViewById(idSeleccionado);
                String metodoPago = radioSeleccionado.getText().toString();

                // Calificación del servicio
                float calificacion = ratingBarServicio.getRating();

                // Nombre del camarero
                String camarero = autoCamarero.getText().toString();
                if (camarero.isEmpty()) camarero = "No especificado";

                // Mostrar resultado
                String resultado = "💰 Total: " + String.format("%.2f", total) + " €\n"
                        + "Pago: " + metodoPago + "\n"
                        + "Camarero: " + camarero + "\n"
                        + "Calificación: " + calificacion + " ⭐";

                textViewResultado.setTextColor(Color.BLACK);
                textViewResultado.setText(resultado);
            }
        });
    }
}
