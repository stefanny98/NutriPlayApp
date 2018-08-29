package com.job.nutriplayapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.job.nutriplayapp.R;
import com.job.nutriplayapp.models.Usuario;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final String LGN = "LoginProcess";
    private ProgressBar progressBar;
    private View loginPanel;
    private boolean isNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        loginPanel = (View) findViewById(R.id.login_panel);

        initFirebaseAuth();

        initGoogleLogin();

        initFacebookLogin();

        initFirebaseAuthStateListener();
    }

    private FirebaseAuth mAuth;
    private EditText emailInput;
    private String correo, uid;

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
    }


    //Login Normal Con Google y Firebase
    private static final int GOOGLE_SIGNIN_REQUEST = 1000;
    private GoogleApiClient mGoogleApiClient;

    private void initGoogleLogin() {
        SignInButton mGoogleButton = (SignInButton) findViewById(R.id.googleButton);
        mGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LGN, "Iniciando con Google");
                loginPanel.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                Intent inicioSesion = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(inicioSesion, GOOGLE_SIGNIN_REQUEST);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("824738567319-s03bqm1mtltebv9jtqdlvhhjq9togckv.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d(LGN, " Falla de conexion : " + connectionResult);
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    //Login Normal con Facebook y Firebase
    private static final int FACEBOOK_SIGNIN_REQUEST = 64206;
    private CallbackManager mCallbackManager;

    public void initFacebookLogin() {
        Log.d(LGN, "Iniciando con Fb");
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton mFacebookButton = (LoginButton) findViewById(R.id.facebookButton);
        mFacebookButton.setReadPermissions("email", "public_profile");
        mFacebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(LGN, "Sesion Fb Correcta: " + loginResult.getAccessToken().getToken());
                loginPanel.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                try {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        Log.d(LGN, "Token Obtenido");
                        AuthCredential credential = FacebookAuthProvider.getCredential(AccessToken.getCurrentAccessToken().getToken());
                        mAuth.signInWithCredential(credential)
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Log.d(LGN, "Login con Fb correcta: " + task.isSuccessful());
                                        if (task.isSuccessful()) {
                                            isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                                            Log.d(LGN, "Antiguedad: " + (isNew ? "Nuevo" : "Antiguo"));
                                            if (isNew) {
                                                Log.d(LGN, "Es nuevo");
                                                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                                uid = currentUser.getUid();

                                                for (UserInfo profile : currentUser.getProviderData()) {
                                                    correo = profile.getEmail();
                                                }

                                                Usuario usuario = new Usuario();
                                                usuario.setNombre(currentUser.getDisplayName());
                                                usuario.setCorreo(correo);
                                                usuario.setExp(0);
                                                usuario.setMonedas(0);
                                                usuario.setAvatar(currentUser.getPhotoUrl() != null ? currentUser.getPhotoUrl().toString() : null);

                                                DatabaseReference usuarioData = FirebaseDatabase.getInstance().getReference("usuario");
                                                usuarioData.child(uid).setValue(usuario)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    createColecciones(uid);
                                                                    Log.d(LGN, "Usuario con Fb Creado");
                                                                    Toast.makeText(LoginActivity.this, "Usuario Creado", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Log.d(LGN, "Error en la creacion");
                                                                    Log.e(LGN, "onFailure", task.getException());
                                                                }
                                                            }
                                                        });
                                            } else {
                                                Log.d(LGN, "Es antiguo");
                                            }
                                        } else {
                                            loginPanel.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.GONE);
                                            Log.e(LGN, "Login con Fb incorrecta:", task.getException());
                                            Toast.makeText(LoginActivity.this, "Autenticacion Fallida.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        loginPanel.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Log.d(LGN, "Sin Token");
                        Log.e(LGN, "Sesion con Fb Errada!");
                    }
                } catch (Throwable t) {
                    try {
                        loginPanel.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                        if (getApplication() != null)
                            Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(LGN, "Error : " + error);
            }
        });
    }

    //Resultado de Google y Facebook
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(LGN, "Resultado de Solicitud");
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Log.d(LGN, "onActivityResult: " + requestCode);
            if (requestCode == GOOGLE_SIGNIN_REQUEST) {
                Log.d(LGN, "Respuesta de Google");
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                Log.d(LGN, result.getStatus() + "");
                Log.d(LGN, resultCode + "");
                Log.d(LGN, data + "");
                if (result.isSuccess()) {
                    Log.d(LGN, "Respuesta Buena");
                    GoogleSignInAccount user = result.getSignInAccount();
                    AuthCredential credential = GoogleAuthProvider.getCredential(user.getIdToken(), null);
                    mAuth.signInWithCredential(credential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(LGN, "Login con Google correcta: " + task.isSuccessful());
                                    if (task.isSuccessful()) {
                                        isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                                        Log.d(LGN, "Antiguedad: " + (isNew ? "Nuevo" : "Antiguo"));
                                        if (isNew) {
                                            Log.d(LGN, "Es nuevo");
                                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                            uid = currentUser.getUid();

                                            for (UserInfo profile : currentUser.getProviderData()) {
                                                correo = profile.getEmail();
                                            }

                                            Usuario usuario = new Usuario();
                                            usuario.setNombre(currentUser.getDisplayName());
                                            usuario.setCorreo(correo);
                                            usuario.setExp(0);
                                            usuario.setMonedas(0);
                                            usuario.setAvatar(currentUser.getPhotoUrl() != null ? currentUser.getPhotoUrl().toString() : null);

                                            DatabaseReference usuarioData = FirebaseDatabase.getInstance().getReference("usuario");
                                            usuarioData.child(currentUser.getUid()).setValue(usuario)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                createColecciones(uid);
                                                                Log.d(LGN, "Usuario con Google Creado");
                                                                Toast.makeText(LoginActivity.this, "Usuario Creado", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Log.d(LGN, "Error en la creacion");
                                                                Log.e(LGN, "onFailure", task.getException());
                                                            }
                                                        }
                                                    });

                                           /* Intent home = new Intent(LoginActivity.this, AvatarActivity.class);
                                            startActivity(home);
                                            finish();*/

                                        } else {
                                            Log.d(LGN, "Es antiguo");
                                        }
                                    } else {
                                        loginPanel.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);
                                        Log.e(LGN, "Login con Google incorrecta:", task.getException());
                                        Toast.makeText(LoginActivity.this, "Autenticacion Fallida.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    loginPanel.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Log.e(LGN, "Sesion con Google Errada!");
                }
            } else if (FACEBOOK_SIGNIN_REQUEST == requestCode) {
                Log.d(LGN, "Respuesta de Facebook");
                mCallbackManager.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Throwable t) {
            try {
                loginPanel.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                if (getApplication() != null)
                    Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG).show();
            } catch (Throwable x) {
            }
        }
    }


    //Login Normal de Firebase
    public void callLogin(View view) {
    /*
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Ingrese campos", Toast.LENGTH_SHORT).show();
            return;
        }

        loginPanel.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "Inicio sesion correcto: " + task.isSuccessful());
                        Log.d(TAG, "Inicio sesion correcto: " + task.isComplete());

                        if (!task.isSuccessful()) {
                            loginPanel.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Log.d(TAG, "Inicio sesion malo: " + task.getException());
                            Log.d(TAG, "Inicio sesion malo: " + task.getResult());
                            Toast.makeText(LoginActivity.this, "Email o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
    }

    public void callRegister(View view) {/*
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Ingrese campos", Toast.LENGTH_SHORT).show();
            return;
        }

        loginPanel.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "Creacion usuario correcto: " + task.isSuccessful());
                        Log.d(TAG, "Creacion usuario correcto: " + task.isComplete());

                        if (!task.isSuccessful()) {
                            loginPanel.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Log.d(TAG, "Creacion usuario malo: " + task.getException());
                            Log.d(TAG, "Creacion usuario malo: " + task.getResult());
                            Toast.makeText(LoginActivity.this, "Creacion Fallida", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
    }

    private FirebaseAuth.AuthStateListener mAuthListener;

    private void initFirebaseAuthStateListener() {
        Log.d(LGN, "Estado de Autenticacion de Fireabse");
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.d(LGN, isNew ? "NUEVONUEVONUEVO" : "ANTIGUOANTIGUOANTIGUO");
                if (user != null) {
                    Log.d(LGN, "Sesion iniciada: " + user.getUid());
                    Log.d(LGN, "Usuario: " + user.getDisplayName());
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    if (sp.getBoolean("tiene_avatar", false)) {
                        Log.d("sas", "Antiguo");
                        Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(home);
                    } else {
                        Log.d("sas", "nuevo");
                        Intent home = new Intent(LoginActivity.this, AvatarActivity.class);
                        startActivity(home);
                    }

                    finish();
                } else {
                    Log.d(LGN, "Sesion cerada");
                }
            }
        };
    }

    public void createColecciones(final String uidx) {

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("receta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String id_receta = ds.getKey();
                    Log.d("ids", id_receta);
                    DatabaseReference coleccionReceta = FirebaseDatabase.getInstance().getReference("coleccion_receta");
                    coleccionReceta.child(uidx).child(id_receta).setValue(false)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    mDatabase.child("juego").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                String id_juego = ds.getKey();
                                                Log.d("idsjuego: ", id_juego);
                                                DatabaseReference coleccionJuego = FirebaseDatabase.getInstance().getReference("coleccion_juego");
                                                coleccionJuego.child(uidx).child(id_juego).setValue(true)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                mDatabase.child("modulo").addValueEventListener(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                        for (DataSnapshot dsps : dataSnapshot.getChildren()) {
                                                                            String id_modulo = dsps.getKey();
                                                                            Log.d("idsmodulos: ", id_modulo);
                                                                            DatabaseReference coleccionModulo = FirebaseDatabase.getInstance().getReference("coleccion_modulo");
                                                                            coleccionModulo.child(uidx).child(id_modulo).setValue(false)
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            mDatabase.child(("alimento")).addValueEventListener(new ValueEventListener() {
                                                                                                @Override
                                                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                                    for (DataSnapshot daSnap : dataSnapshot.getChildren()) {
                                                                                                        String id_alimento = daSnap.getKey();
                                                                                                        DatabaseReference coleccionAlimento = FirebaseDatabase.getInstance().getReference("coleccion_alimento");
                                                                                                        coleccionAlimento.child(uidx).child(id_alimento).setValue(false);
                                                                                                    }

                                                                                                }

                                                                                                @Override
                                                                                                public void onCancelled(DatabaseError databaseError) {

                                                                                                }
                                                                                            });

                                                                                        }
                                                                                    });
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(DatabaseError databaseError) {
                                                                    }
                                                                });
                                                            }
                                                        });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}















