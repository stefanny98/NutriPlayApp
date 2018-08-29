package com.job.nutriplayapp.activities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.job.nutriplayapp.models.Modulo;
import com.job.nutriplayapp.models.Usuario;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.reflect.Modifier;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ModuloCuestionarioActivity extends AppCompatActivity {

    private Modulo modulo;
    private TextView pregunta1,pregunta2,pregunta3,ganaste_cantidad,tipo_logro;
    private RadioGroup primera_pregunta,segunda_pregunta,tercera_pregunta;
    private RadioButton primera_opcion_p1,segunda_opcion_p1,tercera_opcion_p1,primera_opcion_p2,segunda_opcion_p2,tercera_opcion_p2,primera_opcion_p3,segunda_opcion_p3,tercera_opcion_p3;
    private ImageView moneda;
    private Dialog popupModuloCulminado;
    private Button botonAceptar;
    private String uid;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo_cuestionario);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        //uid = "uX9yWXRpKcaC1JnupQ1IoODzjBr2";

        //Inicialización de la librería de fuentes de texto
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Overlock-Regular.ttf").setFontAttrId(R.attr.fontPath).build());

        modulo = getIntent().getExtras().getParcelable("Modulo");

        pregunta1 = (TextView) findViewById(R.id.pregunta_1);
        pregunta2 = (TextView) findViewById(R.id.pregunta_2);
        pregunta3 = (TextView) findViewById(R.id.pregunta_3);

        primera_pregunta = (RadioGroup)findViewById(R.id.primera_pregunta);
        segunda_pregunta = (RadioGroup)findViewById(R.id.segunda_pregunta);
        tercera_pregunta = (RadioGroup)findViewById(R.id.tercera_pregunta);

        primera_opcion_p1 = (RadioButton)findViewById(R.id.primera_opcion_p1);
        segunda_opcion_p1 = (RadioButton)findViewById(R.id.segunda_opcion_p1);
        tercera_opcion_p1 = (RadioButton)findViewById(R.id.tercera_opcion_p1);
        primera_opcion_p2 = (RadioButton)findViewById(R.id.primera_opcion_p2);
        segunda_opcion_p2 = (RadioButton)findViewById(R.id.segunda_opcion_p2);
        tercera_opcion_p2 = (RadioButton)findViewById(R.id.tercera_opcion_p2);
        primera_opcion_p3 = (RadioButton)findViewById(R.id.primera_opcion_p3);
        segunda_opcion_p3 = (RadioButton)findViewById(R.id.segunda_opcion_p3);
        tercera_opcion_p3 = (RadioButton)findViewById(R.id.tercera_opcion_p3);

        pregunta1.setText(modulo.getPregunta1().getPregunta());
        pregunta2.setText(modulo.getPregunta2().getPregunta());
        pregunta3.setText(modulo.getPregunta3().getPregunta());

        primera_opcion_p1.setText(modulo.getPregunta1().getAlternativa1().getNombre());
        segunda_opcion_p1.setText(modulo.getPregunta1().getAlternativa2().getNombre());
        tercera_opcion_p1.setText(modulo.getPregunta1().getAlternativa3().getNombre());
        primera_opcion_p2.setText(modulo.getPregunta2().getAlternativa1().getNombre());
        segunda_opcion_p2.setText(modulo.getPregunta2().getAlternativa2().getNombre());
        tercera_opcion_p2.setText(modulo.getPregunta2().getAlternativa3().getNombre());
        primera_opcion_p3.setText(modulo.getPregunta3().getAlternativa1().getNombre());
        segunda_opcion_p3.setText(modulo.getPregunta3().getAlternativa2().getNombre());
        tercera_opcion_p3.setText(modulo.getPregunta3().getAlternativa3().getNombre());



        //Inicialización del PopUp
        popupModuloCulminado = new Dialog(this);
        popupModuloCulminado.setCanceledOnTouchOutside(false);

        popupModuloCulminado.setContentView(R.layout.modulo_culminado_popup);
        botonAceptar = (Button)popupModuloCulminado.findViewById(R.id.botonAceptar);
        ganaste_cantidad = (TextView)popupModuloCulminado.findViewById(R.id.ganaste_cantidad);
        tipo_logro = (TextView)popupModuloCulminado.findViewById(R.id.tipo_logro);
        moneda = (ImageView)popupModuloCulminado.findViewById(R.id.moneda);

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void moduloTerminado(View view) {
        if (primera_pregunta.getCheckedRadioButtonId() == -1 || segunda_pregunta.getCheckedRadioButtonId() == -1 || tercera_pregunta.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(this,"Por favor marque todos las alternativas, no se preocupe, no perderá puntos :) ",Toast.LENGTH_LONG).show();
        }
        else
        {
            int puntos_acumulados=0;
            //Evaluación
            switch(primera_pregunta.getCheckedRadioButtonId()) {
                case R.id.primera_opcion_p1:
                    if (modulo.getPregunta1().getAlternativa1().getEstado())
                        puntos_acumulados+=modulo.getPregunta1().getPuntos();
                        break;
                case R.id.segunda_opcion_p1:
                    if (modulo.getPregunta1().getAlternativa2().getEstado())
                        puntos_acumulados+=modulo.getPregunta1().getPuntos();
                        break;
                case R.id.tercera_opcion_p1:
                    if (modulo.getPregunta1().getAlternativa3().getEstado())
                        puntos_acumulados+=modulo.getPregunta1().getPuntos();
                        break;
            }

            switch(segunda_pregunta.getCheckedRadioButtonId()) {
                case R.id.primera_opcion_p2:
                    if (modulo.getPregunta2().getAlternativa1().getEstado())
                        puntos_acumulados+=modulo.getPregunta2().getPuntos();
                    break;
                case R.id.segunda_opcion_p2:
                    if (modulo.getPregunta2().getAlternativa2().getEstado())
                        puntos_acumulados+=modulo.getPregunta2().getPuntos();
                    break;
                case R.id.tercera_opcion_p2:
                    if (modulo.getPregunta2().getAlternativa3().getEstado())
                        puntos_acumulados+=modulo.getPregunta2().getPuntos();
                    break;
            }

            switch(tercera_pregunta.getCheckedRadioButtonId()) {
                case R.id.primera_opcion_p3:
                    if (modulo.getPregunta3().getAlternativa1().getEstado())
                        puntos_acumulados+=modulo.getPregunta3().getPuntos();
                    break;
                case R.id.segunda_opcion_p3:
                    if (modulo.getPregunta3().getAlternativa2().getEstado())
                        puntos_acumulados+=modulo.getPregunta3().getPuntos();
                    break;
                case R.id.tercera_opcion_p3:
                    if (modulo.getPregunta3().getAlternativa3().getEstado())
                        puntos_acumulados+=modulo.getPregunta3().getPuntos();
                    break;
            }
            //Toast.makeText(this,"Sacaste un total de: " + puntos_acumulados,Toast.LENGTH_LONG).show();
            MostrarPopUpDescubierto(puntos_acumulados);
        }
    }

    public void MostrarPopUpDescubierto(final int cantidad){


        //Una solo vez
        mDatabase.child("usuario").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                int experiencia = usuario.getExp();
                int monedas = usuario.getMonedas();

                int total_monedas = monedas + cantidad;
                Log.d("ModuloDetalleActivity","Ganó: "+total_monedas);

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
                if (cantidad==0){
                    tipo_logro.setText("Fallaste");
                    ganaste_cantidad.setText("Lo siento para la próxima te irá mejor");
                    moneda.setVisibility(View.GONE);
                    botonAceptar.setBackgroundResource(R.drawable.boton_verde_redondo);
                }else {
                    ganaste_cantidad.setText("¡Felicidades!, ganaste: " + String.valueOf(cantidad));
                }
                int total_experiencia = experiencia + 250;

                mDatabase.child("usuario").child(uid).child("exp").setValue(total_experiencia).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d("ModuloDetalleActivity","Existoso");
                        }else{
                            Log.e("ModuloDetalleActivity","Hubo fallos");
                        }
                    }
                });

                mDatabase.child("coleccion_modulo").child(uid).child(modulo.getId()).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d("ModuloDetalleActivity","Existoso");
                        }else{
                            Log.e("ModuloDetalleActivity","Hubo fallos");
                        }
                    }
                });


                Log.d("ModuloDetalleActivity",dataSnapshot.getValue().toString());
                Log.d("ModuloDetalleActivity",usuario.getNombre());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        popupModuloCulminado.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupModuloCulminado.show();

        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupModuloCulminado.dismiss();
                finish();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
