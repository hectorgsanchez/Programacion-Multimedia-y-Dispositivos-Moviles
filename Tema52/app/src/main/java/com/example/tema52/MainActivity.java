package com.example.tema52;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.linear);

        Datos[] datos=new Datos[]{
                new Datos("Línea principal 1", "Línea inferior 1"),
                new Datos("Línea principal 2", "Línea inferior 2"),
                new Datos("Línea principal 3", "Línea inferior 3"),
                new Datos("Línea principal 4", "Línea inferior 4"),
        };

        ListView listado = findViewById(R.id.listView);
        Adaptador miAdaptador = new Adaptador(this,datos);
        listado.setAdapter(miAdaptador);

        View miCabecera = getLayoutInflater().inflate(R.layout.cabecera,null);
        listado.addHeaderView(miCabecera);

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((Datos)parent.getItemAtPosition(position)).getTexto1();
                Toast.makeText(MainActivity.this, "Elemento pulsado: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });


    }


}
