package com.example.practic5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentDos extends Fragment {

    private TextView tvPantalla;
    private String entrada = "0";

    public FragmentDos() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dos, container, false);
        tvPantalla = view.findViewById(R.id.tvDisplay);

        GridLayout grid = view.findViewById(R.id.gridButtons);

        // Asignar listener a todos los botones
        for (int i = 0; i < grid.getChildCount(); i++) {
            View v = grid.getChildAt(i);
            if (v instanceof Button) {
                ((Button) v).setOnClickListener(this::onBotonClick);
            }
        }

        return view;
    }

    private void onBotonClick(View v) {
        Button b = (Button) v;
        String texto = b.getText().toString();

        switch (texto) {
            case "AC":
                entrada = "0";
                tvPantalla.setText(entrada);
                break;
            case "C":
                if (entrada.length() > 0) {
                    entrada = entrada.substring(0, entrada.length() - 1);
                    if (entrada.isEmpty()) entrada = "0";
                    tvPantalla.setText(entrada);
                }
                break;
            case "=":
                if (!terminaConOperador() && !entrada.isEmpty()) {
                    String resultado = calcularResultado(entrada);
                    entrada = resultado;
                    tvPantalla.setText(resultado);
                }
                break;
            case ",":
                if (!ultimoNumeroTieneComa()) {
                    if (entrada.isEmpty() || terminaConOperador()) {
                        entrada += "0,";
                    } else {
                        entrada += ",";
                    }
                    tvPantalla.setText(entrada);
                }
                break;
            case "+":
            case "−":
            case "×":
            case "÷":
                if (!entrada.isEmpty() && !terminaConOperador()) {
                    entrada += texto;
                    tvPantalla.setText(entrada);
                }
                break;
            default: // Números del 0 al 9
                if (entrada.equals("0")) {
                    entrada = texto.equals("0") ? "0" : texto;
                } else {
                    entrada += texto;
                }
                tvPantalla.setText(entrada);
                break;
        }
    }

    private boolean terminaConOperador() {
        return entrada.endsWith("+") || entrada.endsWith("−")
                || entrada.endsWith("×") || entrada.endsWith("÷");
    }

    private boolean ultimoNumeroTieneComa() {
        int ultimaOperacion = Math.max(
                Math.max(entrada.lastIndexOf("+"), entrada.lastIndexOf("−")),
                Math.max(entrada.lastIndexOf("×"), entrada.lastIndexOf("÷"))
        );
        String ultimoNumero = entrada.substring(ultimaOperacion + 1);
        return ultimoNumero.contains(",");
    }

    private String calcularResultado(String expr) {
        try {
            expr = expr.replace(",", ".")
                    .replace("×", "*")
                    .replace("÷", "/")
                    .replace("−", "-");

            double resultado = evaluar(expr);
            String textoResultado = String.valueOf(resultado);

            if (textoResultado.endsWith(".0")) {
                textoResultado = textoResultado.substring(0, textoResultado.length() - 2);
            }
            return textoResultado.replace(".", ",");
        } catch (Exception e) {
            return "Error";
        }
    }

    private double evaluar(String expr) throws Exception {
        expr = expr.replace(" ", "");
        if (expr.isEmpty()) return 0;

        List<Double> nums = new ArrayList<>();
        List<Character> ops = new ArrayList<>();

        int i = 0;
        while (i < expr.length()) {
            char c = expr.charAt(i);

            if ((c == '+' || c == '-' || c == '*' || c == '/') &&
                    !(c == '-' && (i == 0 || "+-*/".indexOf(expr.charAt(i - 1)) != -1))) {
                ops.add(c);
                i++;
            } else {
                StringBuilder sb = new StringBuilder();
                if (c == '+' || c == '-') {
                    sb.append(c);
                    i++;
                    if (i >= expr.length()) throw new Exception("Expresión inválida");
                }
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    sb.append(expr.charAt(i));
                    i++;
                }
                double valor = Double.parseDouble(sb.toString());
                nums.add(valor);
            }
        }

        // Multiplicaciones y divisiones
        List<Double> nums2 = new ArrayList<>();
        List<Character> ops2 = new ArrayList<>();
        nums2.add(nums.get(0));

        for (int j = 0; j < ops.size(); j++) {
            char op = ops.get(j);
            double next = nums.get(j + 1);

            if (op == '*' || op == '/') {
                double last = nums2.remove(nums2.size() - 1);
                if (op == '*') nums2.add(last * next);
                else {
                    if (next == 0) throw new ArithmeticException("División por cero");
                    nums2.add(last / next);
                }
            } else {
                ops2.add(op);
                nums2.add(next);
            }
        }

        double resultado = nums2.get(0);
        for (int j = 0; j < ops2.size(); j++) {
            char op = ops2.get(j);
            double val = nums2.get(j + 1);
            if (op == '+') resultado += val;
            else resultado -= val;
        }

        return resultado;
    }
}
