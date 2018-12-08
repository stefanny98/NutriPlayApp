package com.tecsup.nutriplayapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.tecsup.nutriplayapp.R;
import com.tecsup.nutriplayapp.models.Modulo;
import com.tecsup.nutriplayapp.models.ModuloEstado;

public class ResultadosModuloActivity extends AppCompatActivity {
    private Modulo modulo;
    private ModuloEstado moduloEstado;
    private TextView m1,m2,m3, c1,c2,c3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_modulo);

        moduloEstado = getIntent().getExtras().getParcelable("ModuloEstado");
        modulo = getIntent().getExtras().getParcelable("Modulo");

        m1 = (TextView)findViewById(R.id.respuestaMarcada1);
        m2 = (TextView)findViewById(R.id.respuestaMarcada2);
        m3 = (TextView)findViewById(R.id.respuestaMarcada3);
        c1 = (TextView)findViewById(R.id.respuestaCorrecta1);
        c2 = (TextView)findViewById(R.id.respuestaCorrecta2);
        c3 = (TextView)findViewById(R.id.respuestaCorrecta3);

        Log.d("modulo: ", modulo.getPregunta1().toString());


        m1.setText(moduloEstado.getPregunta1().getAlternativaMarcada());
        c1.setText(moduloEstado.getPregunta1().getAlternativaCorrecta());

        m2.setText(moduloEstado.getPregunta2().getAlternativaMarcada());
        c2.setText(moduloEstado.getPregunta2().getAlternativaCorrecta());

        m3.setText(moduloEstado.getPregunta3().getAlternativaMarcada());
        c3.setText(moduloEstado.getPregunta3().getAlternativaCorrecta());

    }
}
