package com.job.nutriplayapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.job.nutriplayapp.R;
import com.job.nutriplayapp.jobsdetallereceta;
import com.job.nutriplayapp.models.Receta;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.ViewHolder> {

    private List<Receta> recetas;
    private Context context;
    public RecetaAdapter(){
        this.recetas = new ArrayList<>();
    }
    public void setRecetas(List<Receta> recetas, Context context) {
        this.recetas = recetas;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView titulo;
        public TextView descripcion;
        public ImageView picture;

        public ViewHolder(View itemView) {
            super(itemView);
            picture = (ImageView) itemView.findViewById(R.id.picture_image);
            titulo = (TextView) itemView.findViewById(R.id.titulo_text);
            descripcion = (TextView) itemView.findViewById(R.id.desc_text);
        }
    }

    @Override
    public RecetaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receta, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Receta receta = this.recetas.get(position);
        viewHolder.titulo.setText(receta.getTitulo());
        //viewHolder.descripcion.setText(receta.getDescripcion());

        Picasso.get().load(receta.getImagen()).into(viewHolder.picture);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(view.getContext(), jobsdetallereceta.class);
                i1.putExtra("imagen", receta.getImagen());
                i1.putExtra("titulo", receta.getTitulo());
                i1.putExtra("ingredientes", receta.getIngredientes());
                i1.putExtra("contenido", receta.getContenido());
                view.getContext().startActivity(i1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.recetas.size();
    }

}