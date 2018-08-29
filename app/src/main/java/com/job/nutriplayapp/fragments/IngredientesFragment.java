package com.job.nutriplayapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.job.nutriplayapp.R;
import com.job.nutriplayapp.models.Receta;

import java.util.Arrays;
import java.util.List;


public class IngredientesFragment extends Fragment {

    View view;
    private DatabaseReference mDatabase;
    private TextView ingredientes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ingredientes, container, false);
        ingredientes = (TextView)view.findViewById(R.id.ingreText);

        List<String> ingredientesList = Arrays.asList(getArguments().getString("prueba").split("-"));
        for( int i = 0; i < ingredientesList.size(); i++){
            ingredientes.append(ingredientesList.get(i)+System.getProperty("line.separator"));
        }
        return view;
    }
}
