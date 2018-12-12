package com.tecsup.nutriplayapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tecsup.nutriplayapp.R;
import com.tecsup.nutriplayapp.models.Ranking;
import com.tecsup.nutriplayapp.models.Ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.lang.Math.E;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private List<Ranking> rankings;
    private Context context;

    public RankingAdapter(Context context,List<Ranking> rankings) {
        this.rankings = rankings;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtNombre, txtExperiencia;
        public ImageView imgCorona;

        public ViewHolder(View itemView) {
            super(itemView);
            txtExperiencia = (TextView) itemView.findViewById(R.id.twExp);
            txtNombre = (TextView) itemView.findViewById(R.id.twNombre);
            imgCorona = (ImageView) itemView.findViewById(R.id.iwCorona);
        }
    }

    @Override
    public RankingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_ranking, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Ranking ranking = this.rankings.get(position);
        Log.d("Rank333: ", this.rankings.toString());
        viewHolder.txtNombre.setText(ranking.getNombre());
        viewHolder.txtExperiencia.setText(ranking.getExp().toString());

        if (position < 3){
            viewHolder.imgCorona.setVisibility(VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return this.rankings.size();
    }

}
