package com.job.nutriplayapp.activities;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.job.nutriplayapp.R;
import com.job.nutriplayapp.adapters.ViewAdapter;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private FloatingActionButton fab;
    private ConstraintLayout layoutMain;
    private ConstraintLayout layoutButtons;
    private ConstraintLayout layoutContent;
    private boolean isOpen = false;
    private TextView nombre, moneda, exp, textView5, textView6;
    private ImageView avatar;
    private String uid;
    private CardView expC, monedas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //Inicialización de la librería de fuentes de texto
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Overlock-Regular.ttf").setFontAttrId(R.attr.fontPath).build());


        layoutMain = (ConstraintLayout) findViewById(R.id.layoutMain);
        layoutButtons = (ConstraintLayout) findViewById(R.id.layoutButtons);
        layoutContent = (ConstraintLayout) findViewById(R.id.layoutContent);
        expC = (CardView) findViewById(R.id.cardExp);
        monedas = (CardView) findViewById(R.id.cardMonedas);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMenu();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.mainTab);
        tabLayout.addTab(tabLayout.newTab().setText("Módulos"));
        tabLayout.addTab(tabLayout.newTab().setText("Dato Curioso"));
        tabLayout.addTab(tabLayout.newTab().setText("Tip del Día"));
        tabLayout.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.mainViewer);
        final ViewAdapter adapter = new ViewAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Para la vista del usuario
        nombre = (TextView) findViewById(R.id.nombreText);
        moneda = (TextView) findViewById(R.id.monedasText);
        exp = (TextView) findViewById(R.id.expText);
        avatar = (ImageView) findViewById(R.id.avatarView);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        //uid = "uX9yWXRpKcaC1JnupQ1IoODzjBr2";

        DatabaseReference userData = FirebaseDatabase.getInstance().getReference("usuario").child(uid);
        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                moneda.setText(dataSnapshot.child("monedas").getValue().toString());
                exp.setText(dataSnapshot.child("exp").getValue().toString());
                Picasso.get().load(dataSnapshot.child("avatar").getValue().toString()).into(avatar);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void viewMenu() {
        if (!isOpen) {
            int x = layoutContent.getRight();
            int y = layoutContent.getBottom();

            int startRadius = 0;
            int endRadius = (int) Math.hypot(layoutMain.getWidth(), layoutMain.getHeight());

            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), android.R.color.white, null)));
            fab.setImageResource(R.drawable.ic_close);

            Animator animator = ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, endRadius);
            layoutButtons.setVisibility(View.VISIBLE);
            expC.setVisibility(View.INVISIBLE);
            monedas.setVisibility(View.INVISIBLE);
            textView5.setVisibility(View.INVISIBLE);
            textView6.setVisibility(View.INVISIBLE);
            animator.start();

            isOpen = true;
        } else {
            int x = layoutContent.getRight();
            int y = layoutContent.getBottom();

            int startRadius = Math.max(layoutContent.getWidth(), layoutContent.getHeight());
            int endRadius = 0;

            fab.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null)));
            fab.setImageResource(R.drawable.ic_add);

            Animator animator = ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, endRadius);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    layoutButtons.setVisibility(View.GONE);
                    expC.setVisibility(View.VISIBLE);
                    monedas.setVisibility(View.VISIBLE);
                    textView5.setVisibility(View.VISIBLE);
                    textView6.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animator.start();
            isOpen = false;
        }
    }

    public void goToRecetas(View view) {
        Intent intent = new Intent(this, RecetaActivity.class);
        startActivity(intent);
    }

    public void goToColeccion(View view) {
        Intent intent = new Intent(this, ListaAlimentosActivity.class);
        startActivity(intent);
        Log.d("Modulo", "Yendo a Coleccion");
    }

    public void goToJuego(View view) {
        Intent i1 = new Intent(this, JuegoActivity.class);
        startActivity(i1);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void callLogout(View view) {
        Log.d(TAG, "Sesion Cerrada");
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        finish();
    }
}