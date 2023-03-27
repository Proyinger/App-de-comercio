package proyingercom.example.points3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Opcion_Ingreso extends AppCompatActivity {

    private Button Soy_compardor;
    private Button Nuevo_vendedor;
    private Button Ya_vendo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcion__ingreso);

        Soy_compardor= (Button)findViewById(R.id.soy_comprador);
        Nuevo_vendedor= (Button)findViewById(R.id.nuevovendedor);
        Ya_vendo= (Button)findViewById(R.id.ya_vendo);





    } //Fin Oncreate

    public void comprador(View view) {

        Intent i = new Intent(Opcion_Ingreso.this, AuthActivity.class);

        startActivity(i);

    }

    public void ya_vende(View view) {

        Intent i = new Intent(Opcion_Ingreso.this, ScrollingPremios.class);

        startActivity(i);

    }

    public void nuevo_vendedor(View view) {

        Intent i = new Intent(Opcion_Ingreso.this, Registro_usuarios.class);

        startActivity(i);

    }
}