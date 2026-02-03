package com.example.practic5;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FragmentUno extends Fragment {

    private TextInputLayout textInputLayoutNombre, textInputLayoutEmail, textInputLayoutTelefono;
    private TextInputEditText editTextNombre, editTextEmail, editTextTelefono;

    public FragmentUno() {
        // Constructor vacío obligatorio
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_uno, container, false);

        // Referencias a los TextInputLayout
        textInputLayoutNombre = view.findViewById(R.id.textInputLayoutNombre);
        textInputLayoutEmail = view.findViewById(R.id.textInputLayoutEmail);
        textInputLayoutTelefono = view.findViewById(R.id.textInputLayoutTelefono);

        // Referencias a los EditText
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextTelefono = view.findViewById(R.id.editTextTelefono);

        // Botón de validación
        view.findViewById(R.id.btnValidar).setOnClickListener(v -> validarFormulario(v));

        return view;
    }

    private void validarFormulario(View v) {
        boolean valido = true;

        // Validar Nombre
        String nombre = editTextNombre.getText().toString().trim();
        if (nombre.isEmpty()) {
            textInputLayoutNombre.setError("El nombre es obligatorio");
            valido = false;
        } else {
            textInputLayoutNombre.setError(null);
        }

        // Validar Email
        String email = editTextEmail.getText().toString().trim();
        if (email.isEmpty()) {
            textInputLayoutEmail.setError("El correo es obligatorio");
            valido = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputLayoutEmail.setError("Correo no válido");
            valido = false;
        } else {
            textInputLayoutEmail.setError(null);
        }

        // Validar Teléfono
        String telefono = editTextTelefono.getText().toString().trim();
        if (telefono.isEmpty()) {
            textInputLayoutTelefono.setError("El teléfono es obligatorio");
            valido = false;
        } else if (telefono.length() < 7) {
            textInputLayoutTelefono.setError("Teléfono demasiado corto");
            valido = false;
        } else {
            textInputLayoutTelefono.setError(null);
        }

        // Mostrar Snackbar si todo es correcto
        if (valido) {
            Snackbar.make(v, "Formulario enviado ✔", Snackbar.LENGTH_LONG).show();
        }
    }
}
