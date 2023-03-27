package proyingercom.example.points3;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.viewmodel.RequestCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import static androidx.core.view.ViewCompat.animate;


public class AuthActivity extends AppCompatActivity {


    Bundle bundle;
    FirebaseAuth mfirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;

    public static final int REQUEST_CODE = 54654;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            //new AuthUI.IdpConfig.PhoneBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().build());


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);



       //Analitycs Event

       mAuth = FirebaseAuth.getInstance();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, null);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, null);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        //Acceso con Facebook y google


        mfirebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //Política de datos

                AlertDialog.Builder alerta = new AlertDialog.Builder(AuthActivity.this);

                alerta.setMessage(R.string.dialog_message)
                        .setTitle(R.string.dialog_title).setCancelable(false).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(AuthActivity.this, MenuInicial.class);
                        startActivity(i);
                        finish();
                        dialog.cancel();
                    }
                })
                        .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            public void onClick(DialogInterface dialog, int id) {
                                cerrarsesion((null));
                                Toast.makeText(AuthActivity.this, "Debes aceptar la política de datos para poder continuar", Toast.LENGTH_SHORT).show();
                                finishAndRemoveTask();
                            }
                        });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Política de tratamiento de datos");
                titulo.show();


                final FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {


                } else {


                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setIsSmartLockEnabled(false).
                            build(), REQUEST_CODE);

                }


            }

        };

    }//Fin del Oncreate


    public void cerrarsesion(View view) {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent i = new Intent(AuthActivity.this, MainActivity.class);
               startActivity(i);
                Toast.makeText(AuthActivity.this, " Inténtalo de nuevo", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
    }



    @Override
    protected void onResume() {
        super.onResume();

        mfirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onPause() {
        super.onPause();

       mfirebaseAuth.removeAuthStateListener((mAuthListener));
    }


}

