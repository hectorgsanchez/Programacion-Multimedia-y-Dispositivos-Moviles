package com.example.aplicaciongestioneventos;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btnAgregar;
    ListView listEventos;

    ArrayList<Evento> eventos = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAgregar = findViewById(R.id.btnAgregar);
        listEventos = findViewById(R.id.listEventos);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>());

        listEventos.setAdapter(adapter);

        crearCanalNotificacion();

        btnAgregar.setOnClickListener(v -> mostrarDialogoNombre());

        listEventos.setOnItemClickListener((parent, view, position, id) -> {
            mostrarToast(eventos.get(position));
        });
    }

    // ---------- ALERTDIALOG ----------
    private void mostrarDialogoNombre() {
        EditText input = new EditText(this);
        input.setHint("Nombre del evento");

        new AlertDialog.Builder(this)
                .setTitle("Nuevo evento")
                .setView(input)
                .setPositiveButton("Siguiente", (dialog, which) -> {
                    String nombre = input.getText().toString();
                    seleccionarFecha(nombre);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // ---------- DATE PICKER ----------
    private void seleccionarFecha(String nombre) {
        Calendar c = Calendar.getInstance();

        new DatePickerDialog(this,
                (view, year, month, day) -> {
                    String fecha = day + "/" + (month + 1) + "/" + year;
                    seleccionarHora(nombre, fecha);
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show();
    }

    // ---------- TIME PICKER ----------
    private void seleccionarHora(String nombre, String fecha) {
        Calendar c = Calendar.getInstance();

        new TimePickerDialog(this,
                (view, hour, minute) -> {
                    String hora = String.format("%02d:%02d", hour, minute);
                    agregarEvento(nombre, fecha, hora);
                },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true).show();
    }

    // ---------- AÑADIR EVENTO ----------
    private void agregarEvento(String nombre, String fecha, String hora) {
        Evento evento = new Evento(nombre, fecha, hora);
        eventos.add(evento);

        adapter.add(nombre + " - " + fecha + " " + hora);
        adapter.notifyDataSetChanged();

        mostrarNotificacion(evento);
    }

    // ---------- NOTIFICACIÓN ----------
    private void mostrarNotificacion(Evento evento) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "eventos")
                        .setSmallIcon(R.drawable.ic_event)
                        .setContentTitle(evento.getNombre())
                        .setContentText(evento.getFecha() + " " + evento.getHora())
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        NotificationManagerCompat.from(this)
                .notify(eventos.size(), builder.build());
    }

    private void crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(
                    "eventos",
                    "Eventos",
                    NotificationManager.IMPORTANCE_DEFAULT);

            getSystemService(NotificationManager.class)
                    .createNotificationChannel(canal);
        }
    }

    // ---------- TOAST PERSONALIZADO ----------
    private void mostrarToast(Evento evento) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_custom, null);

        TextView texto = layout.findViewById(R.id.txtToast);
        texto.setText(evento.getNombre()
                + "\n" + evento.getFecha()
                + " " + evento.getHora());

        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
