package com.job.nutriplayapp.activities;

import android.animation.Animator;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.job.nutriplayapp.R;
import com.job.nutriplayapp.utilidades.SesionPreference;
import com.squareup.picasso.Picasso;

public class AvatarActivity extends AppCompatActivity {

    private static final String TAG = AvatarActivity.class.getSimpleName();

    private ConstraintLayout layoutMain, layoutButtons, layoutContent, lay2;
    private boolean isOpen = false;

    private ImageView a1, a2;

    private Button acept, cancel;

    private ImageView avatarView;

    private SesionPreference sesionPreference;

    private String uid;
    private String url1 = "https://firebasestorage.googleapis.com/v0/b/nutriplayapp.appspot.com/o/avatares%2FManzana.png?alt=media&token=1a8e4cf3-8daa-453c-ac85-7a83168576cf";
    private String url2 = "https://firebasestorage.googleapis.com/v0/b/nutriplayapp.appspot.com/o/avatares%2FPl%C3%A1tano.png?alt=media&token=ec7a0890-ba45-4459-b7bb-f54f50fd4ebd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        layoutMain = (ConstraintLayout) findViewById(R.id.layoutMain);
        layoutButtons = (ConstraintLayout) findViewById(R.id.layoutButtons);
        layoutContent = (ConstraintLayout) findViewById(R.id.layoutContent);
        lay2 = (ConstraintLayout) findViewById(R.id.layout2);
        acept = (Button) findViewById(R.id.aceptButton);
        cancel = (Button) findViewById(R.id.cancelButton);

        a1 = (ImageView) findViewById(R.id.av1);
        a2 = (ImageView) findViewById(R.id.av2);


        Picasso.get().load(url1).priority(Picasso.Priority.HIGH).into(a1);
        Picasso.get().load(url2).priority(Picasso.Priority.HIGH).into(a2);

        avatarView = (ImageView) findViewById(R.id.avView);

    }

    public void updateAvatar1(View view) {
        float centreX = view.getX() + view.getWidth() / 2;
        float centreY = view.getY() + view.getHeight() / 2;
        update(url1);
        getXY((int) centreX, (int) centreY);
        viewAvatar((int) centreX, (int) centreY, url1);
    }

    public void updateAvatar2(View view) {
        float centreX = view.getX() + view.getWidth() / 2;
        float centreY = view.getY() + view.getHeight() / 2;
        update(url2);
        getXY((int) centreX, (int) centreY);
        viewAvatar((int) centreX, (int) centreY, url2);
    }


    public void getXY(final Integer x, final Integer y) {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAvatar(x, y, url1);
            }
        });
    }

    private void update(String url) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        //uid = "uX9yWXRpKcaC1JnupQ1IoODzjBr2";
        DatabaseReference usuarioData = FirebaseDatabase.getInstance().getReference("usuario");
        usuarioData.child(uid).child("avatar").setValue(url);

    }

    private void viewAvatar(Integer x, Integer y, String url) {
        if (!isOpen) {
            int startRadius = 0;
            int endRadius = (int) Math.hypot(layoutMain.getWidth(), layoutMain.getHeight());
            Animator animator = ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, endRadius);
            layoutButtons.setVisibility(View.VISIBLE);
            lay2.setVisibility(View.GONE);
            animator.start();
            Picasso.get().load(url).into(avatarView);
            isOpen = true;
        } else {
            int startRadius = Math.max(layoutContent.getWidth(), layoutContent.getHeight());
            int endRadius = 0;
            Animator animator = ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, endRadius);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    layoutButtons.setVisibility(View.GONE);
                    lay2.setVisibility(View.VISIBLE);
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

    public void goHome(View view) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("tiene_avatar", true).commit();
        Intent home = new Intent(this, HomeActivity.class);
        startActivity(home);
        finish();
    }

}