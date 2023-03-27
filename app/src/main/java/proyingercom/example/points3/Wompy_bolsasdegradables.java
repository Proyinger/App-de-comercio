package proyingercom.example.points3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;



public class Wompy_bolsasdegradables extends AppCompatActivity {

    private WebView Wompi_bolsas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wompy_bolsasdegradables);


        Intent i=getIntent();
        Bundle extras = i.getExtras();
        String url=(String)extras.get("valor");

        Wompi_bolsas= findViewById(R.id.Wompi_bolsas);
        Wompi_bolsas.getSettings().setJavaScriptEnabled(true);

        Wompi_bolsas.loadUrl(url);
    }
}