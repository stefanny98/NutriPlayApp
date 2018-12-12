package com.tecsup.nutriplayapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecsup.nutriplayapp.R;
import com.tecsup.nutriplayapp.adapters.RankingAdapter;
import com.tecsup.nutriplayapp.models.Ranking;
import com.tecsup.nutriplayapp.models.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RankingActivity extends AppCompatActivity {

    private RecyclerView rankingList;
    private DatabaseReference mDatabaseReference;
    private List<Ranking> rankings;
    private RankingAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Overlock-Regular.ttf").setFontAttrId(R.attr.fontPath).build());

        rankingList = (RecyclerView) findViewById(R.id.rv_Ranking);

        rankings = new ArrayList<>();
        adapter = new RankingAdapter(this, rankings);
        Log.d("USUARIOS:", rankings.toString());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rankingList.setLayoutManager(layoutManager);
        rankingList.setAdapter(adapter);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mDatabaseReference.child("usuario").orderByChild("exp").limitToLast(5).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //rankings.removeAll(rankings);
                adapter.notifyDataSetChanged();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Ranking ranking = ds.getValue(Ranking.class);
                    ranking.setId(ds.getKey());
                    rankings.add(ranking);
                }
                Collections.reverse(rankings);
                Log.d("RANKING", rankings.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
