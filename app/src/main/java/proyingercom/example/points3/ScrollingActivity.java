package proyingercom.example.points3;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;

public class ScrollingActivity extends AppCompatActivity {

    private Object ImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        ImageView perro= (ImageView) findViewById(R.id.perro1);

        perro.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                Intent i = new Intent(ScrollingActivity.this, Productos.class);

                startActivity(i);

            }
        });


    ImageView comida1= (ImageView) findViewById(R.id.comida);

        comida1.setOnClickListener(new View.OnClickListener() {


        @Override
        public void onClick(View view) {


            Intent i = new Intent(ScrollingActivity.this, Premios.class);

            startActivity(i);
        }
    });

    }
}
