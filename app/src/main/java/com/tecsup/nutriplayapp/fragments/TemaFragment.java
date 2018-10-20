package com.tecsup.nutriplayapp.fragments;

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
import com.tecsup.nutriplayapp.R;


public class TemaFragment extends Fragment {

    View view;
    private DatabaseReference mDatabase;
    private TextView tiptext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tema, container, false);
        tiptext = (TextView) view.findViewById(R.id.text_tip);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("tip").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String a = ds.getKey();
                    Log.d("ids", a);
                    Log.d("ids", ds.child("mensaje").getValue().toString());
                    tiptext.setText(ds.child("mensaje").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }
}