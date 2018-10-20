package com.tecsup.nutriplayapp.fragments;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecsup.nutriplayapp.R;
import com.tecsup.nutriplayapp.models.Receta;
import com.tecsup.nutriplayapp.adapters.RecetaAdapter;

import java.util.ArrayList;
import java.util.List;

public class MisRecetasFragment extends Fragment {


    View view;
    private RecyclerView misrecetasList;
    private TextView textVacio;
    private ImageView imgVacio;
    private CardView btnVacio;
    private DatabaseReference mDatabase;
    private List<Receta> recetas = new ArrayList<>();
    private ProgressDialog progress;

    private String uid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mis_recetas, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        //uid = "uX9yWXRpKcaC1JnupQ1IoODzjBr2";

        misrecetasList = (RecyclerView) view.findViewById(R.id.recetasLista);
        textVacio = view.findViewById(R.id.mensaje_repvacio);
        imgVacio =  view.findViewById(R.id.img_ningunareceta);
        btnVacio = view.findViewById(R.id.btn_monedas);

        progress = new ProgressDialog(getContext());
        progress.setTitle("Cargando");
        progress.setMessage("Por favor espere...");
        progress.setCancelable(false);
        progress.show();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("receta").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        recetas.removeAll(recetas);
                        for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                            mDatabase.child("coleccion_receta").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                        boolean estado = dsp.getValue(Boolean.class);

                                        if (estado){
                                            String id_receta = dsp.getKey();
                                            if (ds.getKey().equals(id_receta)) {
                                                Receta receta = ds.getValue(Receta.class);
                                                receta.setId(ds.getKey());
                                                recetas.add(receta);
                                                RecetaAdapter adapter = new RecetaAdapter();
                                                adapter.setRecetas(recetas, getContext());
                                                misrecetasList.setAdapter(adapter);
                                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                                misrecetasList.setLayoutManager(layoutManager);
                                            }
                                        }
                                    }
                                    if(recetas.size() == 0){
                                        misrecetasList.setVisibility(View.GONE);
                                        textVacio.setVisibility(View.VISIBLE);
                                        imgVacio.setVisibility(View.VISIBLE);
                                        btnVacio.setVisibility(View.VISIBLE);
                                    }else{
                                        misrecetasList.setVisibility(View.VISIBLE);
                                        textVacio.setVisibility(View.GONE);
                                        imgVacio.setVisibility(View.GONE);
                                        btnVacio.setVisibility(View.GONE);
                                    }
                                    progress.dismiss();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            //  Log.d("id_re", ds.getKey());

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        return view;
    }

}
