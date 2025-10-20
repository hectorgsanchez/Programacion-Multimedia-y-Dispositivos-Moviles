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

import java.util.ArrayList;
import java.util.List;

/**
 * Calculadora básica en Android
 */
public class MainActivity extends AppCompatActivity {

    private TextView pantalla;
    private String entrada = "0";

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

        findViewById(R.id.btnEq).setOnClickListener(v -> {
            if (!terminaConOperador() && !entrada.isEmpty()) {
                String resultado = calcularResultado(entrada);
                entrada = resultado;
                pantalla.setText(resultado);
            }
        });
    }

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

    /**
     * Evaluación robusta:
     * - Tokeniza números y operadores
     * - Aplica * y / primero (izquierda -> derecha)
     * - Luego suma y resta (izquierda -> derecha)
     * - Maneja unary minus (ej. -5+3)
     */
    private double evaluar(String expr) throws Exception {
        expr = expr.replace(" ", "");
        if (expr.isEmpty()) return 0;

        List<Double> nums = new ArrayList<>();
        List<Character> ops = new ArrayList<>();

        int i = 0;
        while (i < expr.length()) {
            char c = expr.charAt(i);

            // Si es un operador (y no es signo de número), lo añadimos
            if ((c == '+' || c == '-' || c == '*' || c == '/') && !(c == '-' && (i == 0 || "+-*/".indexOf(expr.charAt(i - 1)) != -1))) {
                ops.add(c);
                i++;
            } else {
                // Es parte de un número (puede tener signo inicial)
                StringBuilder sb = new StringBuilder();
                if (c == '+' || c == '-') { // signo del número
                    sb.append(c);
                    i++;
                    if (i >= expr.length()) throw new Exception("Expresión inválida");
                }
                // tomar dígitos y punto decimal
                while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    sb.append(expr.charAt(i));
                    i++;
                }
                String numStr = sb.toString();
                if (numStr.equals("+") || numStr.equals("-") || numStr.isEmpty()) throw new Exception("Número inválido");
                double valor = Double.parseDouble(numStr);
                nums.add(valor);
            }
        }

        if (nums.size() == 0) throw new Exception("Sin números");

        // Primera pasada: resolver * y / (izquierda a derecha)
        List<Double> nums2 = new ArrayList<>();
        List<Character> ops2 = new ArrayList<>();

        nums2.add(nums.get(0));
        for (int j = 0; j < ops.size(); j++) {
            char op = ops.get(j);
            double next = nums.get(j + 1);

            if (op == '*' || op == '/') {
                double last = nums2.remove(nums2.size() - 1);
                if (op == '*') {
                    nums2.add(last * next);
                } else {
                    if (next == 0) throw new ArithmeticException("División por cero");
                    nums2.add(last / next);
                }
            } else {
                ops2.add(op);
                nums2.add(next);
            }
        }

        // Segunda pasada: sumar/restar (izquierda a derecha)
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
