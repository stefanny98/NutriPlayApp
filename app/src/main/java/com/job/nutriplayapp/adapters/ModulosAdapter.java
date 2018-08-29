package com.job.nutriplayapp.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.job.nutriplayapp.R;
import com.job.nutriplayapp.activities.ModuloDetalleActivity;
import com.job.nutriplayapp.models.Modulo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ModulosAdapter extends RecyclerView.Adapter<ModulosAdapter.ViewHolder> {

    private List<Modulo> modulos;

    public ModulosAdapter() {
        this.modulos = new ArrayList<>();
    }

    public void setModulos(List<Modulo> modulos) {
        this.modulos = modulos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView picture;
        public TextView titulo;
        public TextView descripcion;
        public ImageButton desplegable;
        public Button next;

        public ViewHolder(View itemView) {
            super(itemView);
            picture = (ImageView) itemView.findViewById(R.id.picture_image);
            titulo = (TextView) itemView.findViewById(R.id.titulo_text);
            descripcion = (TextView) itemView.findViewById(R.id.desc_text);
            desplegable = (ImageButton) itemView.findViewById(R.id.desplegableButton);
            next = (Button) itemView.findViewById(R.id.nextButton);
        }
    }

    @Override
    public ModulosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_modulo, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final Modulo modulo = this.modulos.get(position);
        viewHolder.titulo.setText(modulo.getTitulo());
        viewHolder.descripcion.setText(modulo.getDescripcion());

        Picasso.get().load(modulo.getPicture()).into(viewHolder.picture);

        viewHolder.desplegable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewHolder.descripcion.getVisibility() == view.VISIBLE){
                    viewHolder.desplegable.setImageResource(R.drawable.ic_expand_less_black_36dp);
                    viewHolder.descripcion.setVisibility(View.GONE);
                }else{
                    viewHolder.desplegable.setImageResource(R.drawable.ic_expand_more_black_36dp);
                    viewHolder.descripcion.setVisibility(View.VISIBLE);
                }
            }
        });

        viewHolder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detalles = new Intent(view.getContext(), ModuloDetalleActivity.class);
                detalles.putExtra("Modulo",modulo);
                view.getContext().startActivity(detalles);

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.modulos.size();
    }

}