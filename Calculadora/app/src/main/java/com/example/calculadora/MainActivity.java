package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView pantalla;
    private String entrada = "";

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

        pantalla = findViewById(R.id.tvDisplay);

        configurarBotonesNumericos();
        configurarBotonesOperaciones();
        configurarBotonesEspeciales();
    }

    /** Configura los botones del 0 al 9. */
    private void configurarBotonesNumericos() {
        int[] botonesNumericos = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btn8, R.id.btn9
        };

        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            String valor = b.getText().toString();

            if (entrada.equals("0")) {
                entrada = valor.equals("0") ? "0" : valor;
            } else {
                entrada += valor;
            }

            pantalla.setText(entrada);
        };

        for (int id : botonesNumericos) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    /** Configura los botones de operaciones y la coma. */
    private void configurarBotonesOperaciones() {
        int[] botonesOperaciones = {
                R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv
        };

        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            String operador = b.getText().toString();

            if (!entrada.isEmpty() && !terminaConOperador()) {
                entrada += operador;
                pantalla.setText(entrada);
            }
        };

        for (int id : botonesOperaciones) {
            findViewById(id).setOnClickListener(listener);
        }

        // Botón coma
        findViewById(R.id.btnComma).setOnClickListener(v -> {
            if (!ultimoNumeroTieneComa()) {
                if (entrada.isEmpty() || terminaConOperador()) {
                    entrada += "0,";
                } else {
                    entrada += ",";
                }
                pantalla.setText(entrada);
            }
        });

        // Botón igual = calcular resultado
        findViewById(R.id.btnEq).setOnClickListener(v -> {
            if (!terminaConOperador() && !entrada.isEmpty()) {
                String resultado = calcularResultado(entrada);
                entrada = resultado;
                pantalla.setText(resultado);
            }
        });
    }

    /** Configura los botones C (borrar último) y AC (borrar todo). */
    private void configurarBotonesEspeciales() {
        findViewById(R.id.btnAC).setOnClickListener(v -> {
            entrada = "0";
            pantalla.setText(entrada);
        });

        findViewById(R.id.btnC).setOnClickListener(v -> {
            if (entrada.length() > 0) {
                entrada = entrada.substring(0, entrada.length() - 1);
                if (entrada.isEmpty()) entrada = "0";
                pantalla.setText(entrada);
            }
        });
    }

    /** Verifica si la entrada termina con un operador. */
    private boolean terminaConOperador() {
        return entrada.endsWith("+") || entrada.endsWith("−")
                || entrada.endsWith("×") || entrada.endsWith("÷");
    }

    /** Verifica si el último número tiene coma. */
    private boolean ultimoNumeroTieneComa() {
        int ultimaOperacion = Math.max(
                Math.max(entrada.lastIndexOf("+"), entrada.lastIndexOf("−")),
                Math.max(entrada.lastIndexOf("×"), entrada.lastIndexOf("÷"))
        );
        String ultimoNumero = entrada.substring(ultimaOperacion + 1);
        return ultimoNumero.contains(",");
    }

    /** Calcula operaciones básicas sin librerías externas. */
    private String calcularResultado(String expr) {
        try {
            // Reemplazar símbolos
            expr = expr.replace(",", ".")
                    .replace("×", "*")
                    .replace("÷", "/")
                    .replace("−", "-");

            double resultado = evaluar(expr);
            // Quitar .0 si es entero
            String textoResultado = String.valueOf(resultado);
            if (textoResultado.endsWith(".0")) {
                textoResultado = textoResultado.substring(0, textoResultado.length() - 2);
            }
            return textoResultado.replace(".", ",");
        } catch (Exception e) {
            return "Error";
        }
    }

    /** Metodo que evalúa operaciones básicas +, -, *, / sin usar librerías. */
    private double evaluar(String expr) {
        // Quita espacios
        expr = expr.replace(" ", "");

        // Maneja multiplicación y división primero
        double resultado = 0;
        char operadorActual = '+';
        double numeroActual = 0;
        int i = 0;

        while (i < expr.length()) {
            char c = expr.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                StringBuilder numero = new StringBuilder();
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    numero.append(expr.charAt(i));
                    i++;
                }
                numeroActual = Double.parseDouble(numero.toString());
                i--; // retrocede una posición
            }

            if (!Character.isDigit(c) || i == expr.length() - 1) {
                switch (operadorActual) {
                    case '+': resultado += numeroActual; break;
                    case '-': resultado -= numeroActual; break;
                    case '*': resultado *= numeroActual; break;
                    case '/': resultado /= numeroActual; break;
                }
                operadorActual = c;
                numeroActual = 0;
            }

            i++;
        }

        return resultado;
    }
}
