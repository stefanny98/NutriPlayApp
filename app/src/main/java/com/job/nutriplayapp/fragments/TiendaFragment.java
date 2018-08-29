package com.job.nutriplayapp.fragments;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.job.nutriplayapp.R;
import com.job.nutriplayapp.models.Receta;
import com.job.nutriplayapp.adapters.TiendaAdapter;
import com.job.nutriplayapp.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class TiendaFragment extends Fragment {

    View view;
    private RecyclerView misrecetasList;
    private DatabaseReference mDatabase;
    private TextView total_monedas;
    private List<Receta> recetas = new ArrayList<>();
    private String uid;
    private ProgressDialog progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tienda, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        //uid = "uX9yWXRpKcaC1JnupQ1IoODzjBr2";

        misrecetasList = (RecyclerView) view.findViewById(R.id.tiendaLista);
        total_monedas = (TextView)view.findViewById(R.id.total_monedas);

        progress = new ProgressDialog(getContext());
        progress.setTitle("Cargando");
        progress.setMessage("Por favor espere...");
        progress.setCancelable(false);
        progress.show();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("usuario").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                total_monedas.setText(""+usuario.getMonedas());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //mDatabase.child("usuario").child(uid)
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
                                        if (!estado) {
                                            String id_receta = dsp.getKey();
                                            if (ds.getKey().equals(id_receta)) {
                                                Log.d("Accion", "Añadiendo receta a la tienda..");
                                                Receta receta = ds.getValue(Receta.class);
                                                receta.setId(ds.getKey());
                                                recetas.add(receta);
                                                TiendaAdapter adapter = new TiendaAdapter(uid);
                                                adapter.setRecetas(recetas,getContext());
                                                misrecetasList.setAdapter(adapter);
                                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                                misrecetasList.setLayoutManager(layoutManager);
                                            }
                                        }
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

        //Receta receta = new Receta("Jugo de fresa", "dec3");

        return view;
    }


    public void back() {
        getActivity().onBackPressed();
    }
}
