package com.job.nutriplayapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.job.nutriplayapp.R;
import com.job.nutriplayapp.models.Receta;
import com.job.nutriplayapp.models.Usuario;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TiendaAdapter extends RecyclerView.Adapter<TiendaAdapter.ViewHolder> {

    private List<Receta> recetas;
    private DatabaseReference mDatabase;
    private TiendaAdapter adapter;
    private Context context;
    private String uid= "8JUACHkI08c3NCdOdAG2eWaVFj73";
    private String uid_obtenido;
    public TiendaAdapter(String uid_obtenido){
        this.recetas = new ArrayList<>();
        this.adapter = this;
        this.uid_obtenido = uid_obtenido;
    }
    public void setRecetas(List<Receta> recetas,Context context) {
        this.recetas = recetas;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView picture;
        public TextView titulo,cantidad_monedas;
        public CardView btnComprar;

        public ViewHolder(View itemView) {
            super(itemView);
            picture = (ImageView) itemView.findViewById(R.id.picture_image);
            titulo = (TextView) itemView.findViewById(R.id.titulo_text);
            cantidad_monedas = (TextView)itemView.findViewById(R.id.cantidad_monedas);
            btnComprar = (CardView) itemView.findViewById(R.id.btnComprar);
        }
    }
    @Override
    public TiendaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tienda, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TiendaAdapter.ViewHolder viewHolder, int position) {
        final Receta receta = this.recetas.get(position);
        viewHolder.titulo.setText(receta.getTitulo());
        viewHolder.cantidad_monedas.setText(String.valueOf(receta.getMoneda()));
        Picasso.get().load(receta.getImagen()).into(viewHolder.picture);

        viewHolder.btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Log.d("Tienda", "Comprando..");

                /*FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                final String uid = currentUser.getUid();*/
                mDatabase = FirebaseDatabase.getInstance().getReference();

                mDatabase.child("usuario").child(uid_obtenido).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Usuario usuario = dataSnapshot.getValue(Usuario.class);
                        int experiencia = usuario.getExp();
                        int monedas = usuario.getMonedas();
                        Log.d("TiendaAdapter","entra");
                        Log.d("TiendaAdapter",""+monedas);
                        int total_monedas = monedas - receta.getMoneda();

                        if (total_monedas>=0){
                            mDatabase.child("coleccion_receta").child(uid_obtenido).child(receta.getId()).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        ((Activity)view.getContext()).finish();
                                        view.getContext().startActivity(((Activity)view.getContext()).getIntent());
                                        Log.d("TiendaAdapter","Existoso");
                                    }else{
                                        Log.e("TiendaAdapter","Hubo fallos");
                                    }
                                }
                            });
                            mDatabase.child("usuario").child(uid_obtenido).child("monedas").setValue(total_monedas).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Log.d("ModuloDetalleActivity","Existoso");
                                    }else{
                                        Log.e("ModuloDetalleActivity","Hubo fallos");
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(context,"No tienes dinero suficiente para comprar más recetas",Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.recetas.size();
    }
}