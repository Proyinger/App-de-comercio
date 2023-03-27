package proyingercom.example.points3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);



        //Agregar Animaciones

       Animation animacion1=AnimationUtils.loadAnimation(this,R.anim.desplazamiento_arriba);
        Animation animacion2=AnimationUtils.loadAnimation(this,R.anim.desplazamiento_abajo);
        Animation animacion3=AnimationUtils.loadAnimation(this,R.anim.desplazamientomintic_arriba);

        ImageView logo =findViewById(R.id.logo);
        ImageView letra =findViewById(R.id.letra);
        ImageView mintic=findViewById(R.id.mintic);

        logo.setAnimation(animacion1);
        letra.setAnimation(animacion2);
        mintic.setAnimation(animacion3);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(MainActivity.this, AuthActivity.class);

                startActivity(i);

            }
        },4000);

    }

}

