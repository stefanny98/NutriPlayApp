package com.job.nutriplayapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.job.nutriplayapp.R;

import java.util.Arrays;
import java.util.List;


public class PreparacionFragment extends Fragment {

    View view;
    private TextView contenido;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_preparacion, container, false);
        contenido = (TextView)view.findViewById(R.id.conteText);
        List<String> contenidoList = Arrays.asList(getArguments().getString("prueba").split("-"));
        for( int i = 0; i < contenidoList.size(); i++){
            contenido.append(contenidoList.get(i)+"\n");
        }
        return view;
    }
}
