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

public class MainActivity extends AppCompatActivity {

    private TextView pantalla;
    private String entrada = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ajusta los márgenes para que la app ocupe toda la pantalla
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pantalla = findViewById(R.id.tvDisplay);

        // Configura los botones de la calculadora
        configurarBotonesNumericos();
        configurarBotonesOperaciones();
        configurarBotonesEspeciales();
    }

    // Botones del 0 al 9
    private void configurarBotonesNumericos() {
        int[] botonesNumericos = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btn8, R.id.btn9
        };

        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            String valor = b.getText().toString();

            // Evita que el número empiece con varios ceros
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

    // Botones de operaciones, coma y resultado
    private void configurarBotonesOperaciones() {
        int[] botonesOperaciones = {
                R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv
        };

        // Añade los operadores a la cadena de entrada
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

        // Botón de la coma decimal
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

        // Botón igual (=) para calcular el resultado
        findViewById(R.id.btnEq).setOnClickListener(v -> {
            if (!terminaConOperador() && !entrada.isEmpty()) {
                String resultado = calcularResultado(entrada);
                entrada = resultado;
                pantalla.setText(resultado);
            }
        });
    }

    // Botones C (borrar último) y AC (borrar todo)
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

    // Comprueba si la entrada termina con un operador
    private boolean terminaConOperador() {
        return entrada.endsWith("+") || entrada.endsWith("−")
                || entrada.endsWith("×") || entrada.endsWith("÷");
    }

    // Verifica si el último número ya tiene coma decimal
    private boolean ultimoNumeroTieneComa() {
        int ultimaOperacion = Math.max(
                Math.max(entrada.lastIndexOf("+"), entrada.lastIndexOf("−")),
                Math.max(entrada.lastIndexOf("×"), entrada.lastIndexOf("÷"))
        );
        String ultimoNumero = entrada.substring(ultimaOperacion + 1);
        return ultimoNumero.contains(",");
    }

    // Convierte los símbolos, evalúa la expresión y muestra el resultado
    private String calcularResultado(String expr) {
        try {
            expr = expr.replace(",", ".")
                    .replace("×", "*")
                    .replace("÷", "/")
                    .replace("−", "-");

            double resultado = evaluar(expr);
            String textoResultado = String.valueOf(resultado);

            // Quita los decimales innecesarios si el resultado es entero
            if (textoResultado.endsWith(".0")) {
                textoResultado = textoResultado.substring(0, textoResultado.length() - 2);
            }
            return textoResultado.replace(".", ",");
        } catch (Exception e) {
            return "Error";
        }
    }

    // Calcula operaciones con prioridad (* y / antes que + y -)
    private double evaluar(String expr) throws Exception {
        expr = expr.replace(" ", "");
        if (expr.isEmpty()) return 0;

        List<Double> nums = new ArrayList<>();
        List<Character> ops = new ArrayList<>();

        int i = 0;
        while (i < expr.length()) {
            char c = expr.charAt(i);

            // Detecta operadores y números (con posible signo)
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

        // Primera pasada: resolver multiplicaciones y divisiones
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

        // Segunda pasada: resolver sumas y restas
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
