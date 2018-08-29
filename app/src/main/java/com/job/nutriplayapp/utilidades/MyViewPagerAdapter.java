package com.job.nutriplayapp.utilidades;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.job.nutriplayapp.R;
import com.job.nutriplayapp.models.Modulo;
import com.squareup.picasso.Picasso;

public class MyViewPagerAdapter extends PagerAdapter {
    private int[] layouts;
    private Context contexto;
    private Modulo modulo;

    public MyViewPagerAdapter(){

    }

    public MyViewPagerAdapter(int[] layouts,Context contexto, Modulo modulo) {
        this.layouts = layouts;
        this.contexto = contexto;
        this.modulo = modulo;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int posicion) {
        LayoutInflater layoutInflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(layouts[posicion], container, false);
        container.addView(view);

        if(posicion == 0){
            TextView titulo = (TextView) view.findViewById(R.id.titulo_slide1);
            titulo.setText(modulo.getTitulo());
            TextView descPrincipal = (TextView) view.findViewById(R.id.contenido_slide1);
            descPrincipal.setText(modulo.getContenido().getTextoPrincipal());
            ImageView imagenPrincipal = (ImageView) view.findViewById(R.id.imagen_slide1);
            Picasso.get().load(modulo.getContenido().getImagenPrincipal()).into(imagenPrincipal);
        }

        if (posicion == 1){
            TextView subtitulo1 = (TextView) view.findViewById(R.id.subtitulo1);
            subtitulo1.setText(modulo.getContenido().getSubtitulo1());
            TextView subtitulo2 = (TextView) view.findViewById(R.id.subtitulo2);
            subtitulo2.setText(modulo.getContenido().getSubtitulo2());
            TextView texto1 = (TextView) view.findViewById(R.id.texto1);
            texto1.setText(modulo.getContenido().getTexto1());
            TextView texto2 = (TextView) view.findViewById(R.id.texto2);
            texto2.setText(modulo.getContenido().getTexto2());
            ImageView imagen1 = (ImageView) view.findViewById(R.id.imagen1);
            Picasso.get().load(modulo.getContenido().getImagen1()).into(imagen1);
            ImageView imagen2 = (ImageView) view.findViewById(R.id.imagen2);
            Picasso.get().load(modulo.getContenido().getImagen2()).into(imagen2);
        }
        if (posicion == 2){
            TextView subtitulo3 = (TextView) view.findViewById(R.id.subtitulo3);
            subtitulo3.setText(modulo.getContenido().getSubtitulo3());
            TextView subtitulo4 = (TextView) view.findViewById(R.id.subtitulo4);
            subtitulo4.setText(modulo.getContenido().getSubtitulo4());
            TextView texto3 = (TextView) view.findViewById(R.id.texto3);
            texto3.setText(modulo.getContenido().getTexto3());
            TextView texto4 = (TextView) view.findViewById(R.id.texto4);
            texto4.setText(modulo.getContenido().getTexto4());
            ImageView imagen3 = (ImageView) view.findViewById(R.id.imagen3);
            Picasso.get().load(modulo.getContenido().getImagen3()).into(imagen3);
            ImageView imagen4 = (ImageView) view.findViewById(R.id.imagen4);
            Picasso.get().load(modulo.getContenido().getImagen4()).into(imagen4);
        }
        return view;
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
