package com.job.nutriplayapp.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.job.nutriplayapp.adapters.PagerAdapter;
import com.job.nutriplayapp.R;
import com.job.nutriplayapp.fragments.MisRecetasFragment;
import com.job.nutriplayapp.fragments.TiendaFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RecetaActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private RelativeLayout relativeLayout;

    private MisRecetasFragment misRecetasFragment;
    private TiendaFragment tiendaFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);

        //Inicialización de la librería de fuentes de texto
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Overlock-Regular.ttf").setFontAttrId(R.attr.fontPath).build());


        navigationView =(BottomNavigationView)findViewById(R.id.bottomnavview);
        relativeLayout = (RelativeLayout)findViewById(R.id.relative_layout);

        misRecetasFragment = new MisRecetasFragment();
        tiendaFragment = new TiendaFragment();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_receta:
                        setFragment(misRecetasFragment);
                        return true;
                    case R.id.nav_tienda:
                        setFragment(tiendaFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });

        setFragment(misRecetasFragment);

    }


    private void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.relative_layout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
