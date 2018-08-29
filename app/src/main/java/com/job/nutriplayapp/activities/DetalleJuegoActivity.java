package com.job.nutriplayapp.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.job.nutriplayapp.R;
import com.job.nutriplayapp.additional.ViewDialog;
import com.job.nutriplayapp.models.Usuario;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetalleJuegoActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private boolean estado;
    private TextView pregunta;
    private String descripcion, juego_id, uid;
    private int monedas;
    private CardView cardVerdad, cardFalso;
    private String acierto = "¡Acertaste!";
    private String fallo = "¡Fallaste!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_juego);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        //uid = "uX9yWXRpKcaC1JnupQ1IoODzjBr2";

        //Inicialización de la librería de fuentes de texto
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Overlock-Regular.ttf").setFontAttrId(R.attr.fontPath).build());


        final String id = getIntent().getExtras().getString("ID");

        cardVerdad = findViewById(R.id.cardVerdad);
        cardFalso = findViewById(R.id.cardFalso);
        pregunta = findViewById(R.id.preguntaText);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("juego").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (id.equals(ds.getKey())) {
                        juego_id = ds.getKey();
                        descripcion = ds.child("respuesta").getValue(String.class);
                        estado = ds.child("estado").getValue(Boolean.class);
                        monedas =ds.child("puntos").getValue(Integer.class);
                        pregunta.setText(ds.child("pregunta").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void verdadTapped(View view) {
        ViewDialog alert = new ViewDialog();
        if (estado) {
            Log.d("Respuesta:", "Respuesta correcta");
            cardVerdad.setCardBackgroundColor(Color.GREEN);
            anadirMonedas(monedas);
            alert.showDialog(DetalleJuegoActivity.this, descripcion, acierto);

        } else {
            Log.d("Respuesta:", "Respuesta incorrecta");
            cardVerdad.setCardBackgroundColor(Color.RED);
            alert.showDialog(DetalleJuegoActivity.this, descripcion, fallo);
        }
        borrarJuego();
    }

    public void falsoTapped(View view) {
        ViewDialog alert = new ViewDialog();
        if (!estado) {
            Log.d("Respuesta:", "Respuesta correcta");
            cardFalso.setCardBackgroundColor(Color.GREEN);
            anadirMonedas(monedas);
            alert.showDialog(DetalleJuegoActivity.this, descripcion, acierto);
        } else {
            Log.d("Respuesta:", "Respuesta incorrecta");
            cardFalso.setCardBackgroundColor(Color.RED);
            alert.showDialog(DetalleJuegoActivity.this, descripcion, fallo);
        }
        borrarJuego();
    }

    public void borrarJuego() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("coleccion_juego").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (dsp.getKey().equals(juego_id)) {
                        Log.d("Eliminar", "Eliminando juego " + dsp.getKey() + "de los mitos");
                        dsp.getRef().setValue(false);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Eliminar", "Hay errores");
            }
        });

    }
    public void anadirMonedas(final int moneditas) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("usuario").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                int experiencia = usuario.getExp();
                int monedas = usuario.getMonedas();

                int total_monedas = monedas + moneditas;

                Log.d("TiendaAdapter","entra");
                Log.d("TiendaAdapter",""+monedas);
                mDatabase.child("usuario").child(uid).child("monedas").setValue(total_monedas).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d("ModuloDetalleActivity","Existoso");
                        }else{
                            Log.e("ModuloDetalleActivity","Hubo fallos");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Eliminar", "Hay errores");
            }
        });

    }

    //Importante - Añadir este método es escencial para establecer el contexto base de la librería
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}