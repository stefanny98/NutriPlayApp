package com.tecsup.nutriplayapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecsup.nutriplayapp.models.Modulo;
import com.tecsup.nutriplayapp.adapters.ModulosAdapter;
import com.tecsup.nutriplayapp.R;

import java.util.ArrayList;
import java.util.List;

public class ModuloFragment extends Fragment {

    View view;
    private RecyclerView modulosList;
    private DatabaseReference mDatabase;
    private List<Modulo> modulos = new ArrayList<Modulo>();
    private String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("prueba", "entró");

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_modulo, container, false);
        modulosList = (RecyclerView) view.findViewById(R.id.modulos_list);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        //uid = "uX9yWXRpKcaC1JnupQ1IoODzjBr2";

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("modulo").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (final DataSnapshot dsp : dataSnapshot.getChildren()) {
                            mDatabase.child("coleccion_modulo").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                        String id_modulo = ds.getKey();
                                        if (dsp.getKey().equals(id_modulo)) {
                                            Modulo modulo = dsp.getValue(Modulo.class);
                                            modulo.setId(dsp.getKey());
                                            Log.d("asdsadsad: ", modulo.toString());
                                            modulos.add(modulo);
                                            ModulosAdapter adapter = new ModulosAdapter();
                                            adapter.setModulos(modulos);
                                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                            modulosList.setLayoutManager(layoutManager);
                                            modulosList.setAdapter(adapter);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

        return view;
    }
}
