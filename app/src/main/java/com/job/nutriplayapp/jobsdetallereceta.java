package com.job.nutriplayapp;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.job.nutriplayapp.adapters.ViewDetaAdapter;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class jobsdetallereceta extends AppCompatActivity {

    private ImageView imagenReceta;
    private TextView nombre_receta;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobsdetallereceta);

        imagenReceta = (ImageView)findViewById(R.id.imagen_alimento);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        nombre_receta = (TextView)findViewById(R.id.nombre);

        //Inicialización de la librería de fuentes de texto
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/PoiretOne-Regular.ttf").setFontAttrId(R.attr.fontPath).build());

        String titulo = getIntent().getExtras().getString("titulo");
        Picasso.get().load(getIntent().getExtras().getString("imagen")).into(imagenReceta);
        nombre_receta.setText(titulo);

        //Establecimiento de propiedades para el toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar.setTitle(" ");

        TabLayout detalleTab = (TabLayout) findViewById(R.id.tabDetalle);
        detalleTab.addTab(detalleTab.newTab().setText("Ingredientes"));
        detalleTab.addTab(detalleTab.newTab().setText("Preparación"));
        detalleTab.setTabGravity(detalleTab.GRAVITY_FILL);

        final ViewPager detallePager = (ViewPager) findViewById(R.id.pagerDetalle);
        Bundle bundleIngre = new Bundle();
        bundleIngre.putString("prueba", getIntent().getExtras().getString("ingredientes"));
        Bundle bundlePrepa = new Bundle();
        bundlePrepa.putString("prueba", getIntent().getExtras().getString("contenido"));
        final ViewDetaAdapter adapterDetalle = new ViewDetaAdapter(getSupportFragmentManager(), detalleTab.getTabCount(), bundleIngre, bundlePrepa);

        detallePager.setAdapter(adapterDetalle);
        detallePager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(detalleTab));
        detalleTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                detallePager.setCurrentItem(tab.getPosition());
                adapterDetalle.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    //Importante - Añadir este método es escencial para establecer el contexto base de la librería
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}









