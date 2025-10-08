package com.example.animacionconbotones;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.linear);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    protected void onStart(){
new View.OnClickListener(){
            @Override        super.onStart();

                                      Button boton1 = findViewById(R.id.boton1);
                                      Button boton2 = findViewById(R.id.boton2);
                                      Button boton3 = findViewById(R.id.boton3);
                                      TextView texto = findViewById(R.id.texto);
                                      Animation Translate = AnimationUtils.loadAnimation(this,R.anim.animacion);
                                      Animation Rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);




        boton1.setOnClickListener(new View.OnClickListener(){
                                          @Override
                                          public void onClick(View view){                texto.startAnimation(Translate);
                                          }
                                      }
        );

        boton2.setOnClickListener(new View.OnClickListener(){
                                          @Override
                                          public void onClick(View view){
                                              texto.startAnimation(Rotate);
                                          }
                                      }
        );

        boton3.setOnClickListener(
            public void onClick(View view){
                texto.clearAnimation();
            }
        }
        );
    }
}