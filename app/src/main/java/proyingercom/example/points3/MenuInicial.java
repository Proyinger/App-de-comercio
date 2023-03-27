package proyingercom.example.points3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuInicial extends AppCompatActivity {


    Button continuar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);



    }

    //Método para pasar al activity de puntos

    public void VerPuntos(View view){

        Intent consultapuntos = new Intent(this,Ver_premios.class);

        startActivity(consultapuntos);

    }


    public void Vermapa(View view){

        Intent consultavendedor = new Intent(this,Registro_usuarios.class);

        startActivity(consultavendedor);


    }


    public void Verproductos(View view){

        Intent consultavendedor = new Intent(this,ScrollingActivity.class);

        startActivity(consultavendedor);

    }
}