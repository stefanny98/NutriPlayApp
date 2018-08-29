package com.job.nutriplayapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.job.nutriplayapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetalleRecetaActivity extends AppCompatActivity {

    TextView ingredientesText, contenidoText, tituloText;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_receta);

        String titulo = getIntent().getExtras().getString("titulo");
        String ingredientes = getIntent().getExtras().getString("ingredientes");
        String contenido = getIntent().getExtras().getString("contenido");
        String imagen = getIntent().getExtras().getString("imagen");

        ingredientesText = findViewById(R.id.ingredientes_text);
        contenidoText = findViewById(R.id.contenido_text);
        tituloText = findViewById(R.id.titulo_text);
        imageView = findViewById(R.id.receta_image);

        tituloText.setText(titulo);
        ingredientesText.setText("");
        contenidoText.setText("");
        List<String> ingredientesList = Arrays.asList(ingredientes.split(","));
        List<String> contenidoList = Arrays.asList(contenido.split(","));
        for( int i = 0; i < ingredientesList.size(); i++){
            ingredientesText.append(ingredientesList.get(i)+"\n");
        }
        for( int i = 0; i < contenidoList.size(); i++){
            contenidoText.append(contenidoList.get(i)+"\n");
        }
        Picasso.get().load(imagen).into(imageView);

    }
}
