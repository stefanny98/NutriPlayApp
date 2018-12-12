package com.tecsup.nutriplayapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecsup.nutriplayapp.R;
import com.tecsup.nutriplayapp.adapters.JuegoAdapter;
import com.tecsup.nutriplayapp.models.Juego;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class JuegoActivity extends AppCompatActivity {

    ListView listView;
    private RecyclerView juegosList;
    private DatabaseReference mDatabase;
    private String pregunta_id;
    private List<Juego> juegos;
    private int n = 0;
    private TextView txt_vacio;
    private ImageView img_vacio;
    ProgressDialog progress;
    private String uid;
    private JuegoAdapter adapter;
    private Map<String,Boolean> coleccion_juego;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        juegosList = (RecyclerView) findViewById(R.id.juegos_list);
        txt_vacio = (TextView) findViewById(R.id.textView7);
        img_vacio = (ImageView) findViewById(R.id.imageView12);

        //Hilo de preguntas
        setPreguntas();
        setHilo();

        juegos = new ArrayList<>();
        coleccion_juego = new HashMap<String,Boolean>();

        adapter = new JuegoAdapter(this, juegos);

        juegosList.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
        juegosList.setAdapter(adapter);

        progress = new ProgressDialog(this);
        progress.setTitle("Cargando");
        progress.setMessage("Por favor espere...");
        progress.setCancelable(false);
        progress.show();

        //Inicialización de la librería de fuentes de texto
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Overlock-Regular.ttf").setFontAttrId(R.attr.fontPath).build());

    }

    public void setPreguntas() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        //uid = "uX9yWXRpKcaC1JnupQ1IoODzjBr2";

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("coleccion_juego").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int con = 0;
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    boolean estado = snapshot.getValue(Boolean.class);
                    if (con < 5) {
                        coleccion_juego.put(snapshot.getKey(), estado);
                    }
                    con++;
                }
                adapter.notifyDataSetChanged();
                progress.dismiss();

                Log.d("ListaColecciónJuegos",coleccion_juego.toString());
                mDatabase.child("juego").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        juegos.removeAll(juegos);
                        for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                            Juego juego = ds.getValue(Juego.class);
                            juego.setId(ds.getKey());

                            for (Map.Entry<String,Boolean> juego_es: coleccion_juego.entrySet()) {
                                String id = juego_es.getKey();
                                Boolean estado = juego_es.getValue();
                                if (juego.getId().equals(id)) {
                                    if (estado){
                                        juegos.add(juego);
                                    }
                                }
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void setHilo() {
        final Handler Myhandler = new Handler();
        Myhandler.postDelayed(new Runnable() {
            private long time = 0;
            @Override
            public void run() {
                time+=1000;
                setPreguntas();
                Myhandler.postDelayed(this, 86400);
            }
        }, 86400);
    }


    //Importante - Añadir este método es escencial para establecer el contexto base de la librería
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}