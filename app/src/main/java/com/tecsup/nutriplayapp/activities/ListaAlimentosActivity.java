package com.tecsup.nutriplayapp.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifierResult;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.tecsup.nutriplayapp.R;
import com.tecsup.nutriplayapp.adapters.AlimentosAdapter;
import com.tecsup.nutriplayapp.models.Alimento;
import com.tecsup.nutriplayapp.models.CategoriaValor;
import com.tecsup.nutriplayapp.models.Usuario;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.dimorinny.showcasecard.ShowCaseView;
import ru.dimorinny.showcasecard.position.ViewPosition;
import ru.dimorinny.showcasecard.radius.Radius;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ListaAlimentosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAlimento;
    private List<Alimento> alimentos;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,databaseReference2,databaseReference3,databaseReference4,databaseReference5;
    private AlimentosAdapter adapter;
    private Map<String,Boolean> coleccion_alimentos;
    private Map<String,String> id_alimentos;
    private VisualRecognition service;
    private CameraHelper helper;
    private ProgressDialog progressDialog;
    private CoordinatorLayout coordinatorLayout;
    private Dialog popupDescubierto,popupNoEsAlimento,popupYaDescubierto;
    private Button botonAceptar,botonAceptar2,botonAceptar3;
    private TextView ganaste_cantidad;
    private ImageView alimento_popup,moneda;
    private Alimento alimento_seleccionado;
    private CategoriaValor categoriaValor;

    //PRUEBA
    private String uid_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alimentos);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid_usuario = user.getUid();
        //uid_usuario = "uX9yWXRpKcaC1JnupQ1IoODzjBr2";

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        recyclerViewAlimento = (RecyclerView)findViewById(R.id.recyclerViewAlimentos);

        //Inicialización de la librería de fuentes de texto
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Overlock-Regular.ttf").setFontAttrId(R.attr.fontPath).build());

        alimentos = new ArrayList<>();
        coleccion_alimentos = new HashMap<String,Boolean>();
        id_alimentos = new HashMap<String, String>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("alimento");
        databaseReference2 = firebaseDatabase.getReference("coleccion_alimento");
        databaseReference3 = firebaseDatabase.getReference("categoria_valor");
        databaseReference4 = firebaseDatabase.getReference("usuario");
        //Inicialización del PopUp
        popupDescubierto = new Dialog(this);
        popupDescubierto.setCanceledOnTouchOutside(false);
        popupNoEsAlimento = new Dialog(this);
        popupNoEsAlimento.setCanceledOnTouchOutside(false);
        popupYaDescubierto = new Dialog(this);
        popupYaDescubierto.setCanceledOnTouchOutside(false);
        //Inicialización de IBM
        service = new VisualRecognition(getString(R.string.version));
        service.setEndPoint("https://gateway.watsonplatform.net/visual-recognition/api");

        IamOptions options = new IamOptions.Builder().apiKey(getString(R.string.api_key)).build();
        service.setIamCredentials(options);

        // Inicialización de camera helper
        helper = new CameraHelper(this);

        adapter = new AlimentosAdapter(alimentos,this,coleccion_alimentos);
        recyclerViewAlimento.setAdapter(adapter);
        recyclerViewAlimento.setHasFixedSize(true);
        // Pasando el padding de cada card
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.cards_padding);
        recyclerViewAlimento.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerViewAlimento.setLayoutManager(new GridLayoutManager(this,3));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                alimentos.removeAll(alimentos);
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    final Alimento alimento= snapshot.getValue(Alimento.class);
                    alimento.setId(snapshot.getKey());
                    Log.d("ListaAlimentosActivity","El valor: "+snapshot.getValue());
                    alimentos.add(alimento);
                    id_alimentos.put(alimento.getId(),alimento.getNombre());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference2.child(uid_usuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()){
                    boolean estado = snapshot.getValue(Boolean.class);
                    coleccion_alimentos.put(snapshot.getKey(),estado);
                }
                adapter.notifyDataSetChanged();
                Log.d("ListaAlimentos",coleccion_alimentos.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //aa
        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    new ShowCaseView.Builder(ListaAlimentosActivity.this)
                            .withTypedPosition(new ViewPosition(ListaAlimentosActivity.this.findViewById(R.id.tomar_foto)))
                            .withTypedRadius(new Radius(360F))
                            .withContent("Ahora puedes coleccionar alimentos, presiona el botón y búscalos!")
                            .withDismissListener(new ShowCaseView.DismissListener() {
                                @Override
                                public void onDismiss() {
                                    //PreferencesManager.getInstance().set(PreferencesManager.PREF_TOURVIEWED, "1");
                                }
                            })
                            .build()
                            .show(ListaAlimentosActivity.this);
                }catch (Throwable t){}
            }
        }, 1000);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }
        return false;
    }

    public void back(View view) {
        finishMyActivity();
    }

    @Override
    public void onBackPressed() {
        finishMyActivity();
    }

    public void finishMyActivity() {
        finish();
        overridePendingTransition(R.anim.back_in, R.anim.back_out);
    }

    private void mostrarSnackbar(){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Hay algunos problemas, verifica la conexión a Internet.", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void tomarFoto(View view) {
        //showDialog();
        if(isOnline()){
            helper.dispatchTakePictureIntent();
        }else{
            mostrarSnackbar();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
                progressDialog = ProgressDialog.show(this, "Cargando", "El registro demorará unos segundos...", true);
                //you usually don't want the user to stop the current process, and this will make sure of that
                final File photoFile = helper.getFile(resultCode);
                Log.e("Capturado", photoFile + "");
                if (photoFile == null){
                    progressDialog.dismiss();
                    return;
                }

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ClassifyOptions classifyOptions = null;
                            classifyOptions = new ClassifyOptions.Builder()
                                    .imagesFile(photoFile)
                                    .imagesFilename(photoFile.getName())
                                    .threshold((float) 0.6)
                                    .acceptLanguage("es")
                                    .owners(Arrays.asList("me"))
                                    .classifierIds(Arrays.asList("Alimento_396195486"))
                                    .build();
                            final ClassifiedImages result = service.classify(classifyOptions).execute();
                            Log.e("ListaAlimentosAc", result.toString());

                            ClassifierResult classifier = result.getImages().get(0).getClassifiers().get(0);
                            final List<String> alimentos_detectados = new ArrayList<String>();
                            //final StringBuffer output = new StringBuffer();
                            for (ClassResult object : classifier.getClasses()) {
                                if (object.getScore() > 0.7f)
                                    alimentos_detectados.add(object.getClassName());
                            }
                            Log.e("ListaAlimentosAc", alimentos_detectados.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (alimentos_detectados.isEmpty()) {
                                        Log.e("ListaAlimentosAc","El elemento detectado no es un alimento");
                                        //PopUp
                                        MostrarPopUpNoEsAlimento();
                                        progressDialog.dismiss();
                                    }else{
                                        Log.e("ListaAlimentosAc","El alimento es: " + alimentos_detectados.get(0));
                                        if(id_alimentos.containsValue(alimentos_detectados.get(0))){
                                            //Existe en la bd
                                            Log.e("ListaAlimentosAc","Alimento: "+alimentos_detectados.get(0)+" existe en la bd");
                                            getKeyFromValue(coleccion_alimentos, alimentos_detectados.get(0));
                                            if (coleccion_alimentos.get(""+getKeyFromValue(id_alimentos,alimentos_detectados.get(0)))){
                                                Log.e("ListaAlimentosAc","El alimento ya ha sido descubierto");
                                                //PopUp
                                                MostrarPopUpYaDescubierto();
                                            }else{
                                                Log.e("ListaAlimentosAc","El alimento no ha sido descubierto");
                                                for (Alimento alimento:alimentos
                                                        ) {
                                                    if (alimento.getNombre().equals(alimentos_detectados.get(0))){
                                                        alimento_seleccionado = alimento;
                                                    }
                                                }
                                                databaseReference3.child(alimento_seleccionado.getCategoria_valor()).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        categoriaValor = dataSnapshot.getValue(CategoriaValor.class);
                                                        Log.e("ListaAlimentosAc",alimento_seleccionado.getImagen());
                                                        //PopUp
                                                        MostrarPopUpDescubierto(alimento_seleccionado,categoriaValor);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                                //Establecimiento de valor true del elemento encontrado
                                                databaseReference2.child(uid_usuario).child(alimento_seleccionado.getId()).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            Log.d("ListaAlimentosAc","Existoso");
                                                        }else{
                                                            Log.e("ListaAlimentosAc","Hubo fallos");
                                                        }
                                                    }
                                                });
                                            }
                                        }else{
                                            //No existe en la bd
                                            Log.e("ListaAlimentosAc","Alimento: "+alimentos_detectados.get(0)+" no existe en la bd");
                                        }
                                        progressDialog.dismiss();
                                    }
                                }
                            });

                        } catch (Throwable t){
                            t.printStackTrace();
                            Log.e("MainActivty", t.getMessage(), t);
                            Toast.makeText(getApplicationContext(),"¡Ups!, Ocurrieron algunos errores, inténtalo de nuevo.",Toast.LENGTH_LONG).show();
                        }finally {
                            progressDialog.dismiss();
                        }

                    }
                });
            }

        }catch (Throwable e){
            progressDialog.dismiss();
            mostrarSnackbar();
            Log.e("ListaAlimentosAc", e.getMessage(), e);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
    public void MostrarPopUpDescubierto(Alimento alimento, final CategoriaValor categoriaValor){
        popupDescubierto.setContentView(R.layout.descubiertopopup);
        botonAceptar = (Button)popupDescubierto.findViewById(R.id.botonAceptar);
        ganaste_cantidad = (TextView)popupDescubierto.findViewById(R.id.ganaste_cantidad);
        alimento_popup = (ImageView)popupDescubierto.findViewById(R.id.alimento_popup);

        Picasso.get().load(alimento.getImagen()).into(alimento_popup);

        databaseReference4.child(uid_usuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                int experiencia = usuario.getExp();
                int monedas = usuario.getMonedas();
                Log.d("TiendaAdapter","entra");
                Log.d("TiendaAdapter",""+monedas);
                int total_monedas = monedas + categoriaValor.getMoneda();
                int total_experiencia = experiencia+80;

                    databaseReference4.child(uid_usuario).child("monedas").setValue(total_monedas).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d("ModuloDetalleActivity","Existoso");
                            }else{
                                Log.e("ModuloDetalleActivity","Hubo fallos");
                            }
                        }
                    });
                    databaseReference4.child(uid_usuario).child("exp").setValue(total_experiencia).addOnCompleteListener(new OnCompleteListener<Void>() {
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

            }
        });

        ganaste_cantidad.setText("¡Felicidades!, ganaste: "+String.valueOf(categoriaValor.getMoneda()));
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDescubierto.dismiss();
            }
        });
        popupDescubierto.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupDescubierto.show();
    }

    public void MostrarPopUpNoEsAlimento(){
        popupNoEsAlimento.setContentView(R.layout.noesalimentopopup);
        botonAceptar2 = (Button)popupNoEsAlimento.findViewById(R.id.botonAceptar);
        botonAceptar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupNoEsAlimento.dismiss();
            }
        });
        popupNoEsAlimento.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupNoEsAlimento.show();
    }

    public void MostrarPopUpYaDescubierto(){
        popupYaDescubierto.setContentView(R.layout.alimentoyadescubiertopopup);
        botonAceptar3 = (Button)popupYaDescubierto.findViewById(R.id.botonAceptar);
        botonAceptar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupYaDescubierto.dismiss();
            }
        });
        popupYaDescubierto.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupYaDescubierto.show();
    }
    public static Object getKeyFromValue(Map hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    //Importante - Añadir este método es escencial para establecer el contexto base de la librería
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
