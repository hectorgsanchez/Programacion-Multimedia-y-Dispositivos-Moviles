package com.example.tema8fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Confirmación")
                .setMessage("Estás seguro de que deseas continuar?")
                .setPositiveButton("Sí", (dialog, id) -> {
                    // Acción al confirmar
                    Toast.makeText(getContext(), "Continuando...", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (dialog, id) -> dialog.dismiss());
        return builder.create();
    }
}
