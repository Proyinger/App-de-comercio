package proyingercom.example.points3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Productos extends AppCompatActivity {

    private Spinner spinner1, spinner2;
    private final static String Wompi_url_bolsas="https://checkout.wompi.co/l/t1hBav";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);


        //-----------Spinner-------------------

        spinner1 = findViewById(R.id.spinner);

        String[] opciones_tipo_caja = {"Selecciona la caja que deseas", "Caja x 60 bolsas $26.000", "Caja x 150 bolsas $57.500", "Caja x 300 bolsas $115.000", "Caja x 480 bolsas $173.000",};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_points, opciones_tipo_caja);

        spinner1.setAdapter(adapter);


        spinner2 = findViewById(R.id.spinner2);

        String[] opciones_cantidad = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item_points, opciones_cantidad);

        spinner2.setAdapter(adapter2);

        }

//---------Ingresar a la psarela de pagos WOMPI------------
    public void Wompi (View view){


        Intent i=new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(Wompi_url_bolsas));
        startActivity(i);

    }


//-------------Muestra el valor de la compra de acuerdo con el tamaño de caja  y cantidad de cajas seleccionadas--------------

    public void Caja_Seleccionada(View view) {

        int precio1, precio2, precio3, precio4;
        int puntos;

        String $ = "El precio de tu pedido es de $ ";

        TextView precio = (TextView) findViewById(R.id.precio);
        TextView puntosposibles = (TextView) findViewById(R.id.puntos);

        String seleccion = spinner1.getSelectedItem().toString();

        int seleccion2 = Integer.parseInt(spinner2.getSelectedItem().toString());


        if (seleccion.equals("Selecciona la caja que deseas")) {


            String pago = "";

            precio.setText(pago);

        } else if (seleccion.equals("Caja x 60 bolsas $26.000")) {


            precio1 = 26000;
            puntos=1000;

            int compra = precio1 * seleccion2;
            int puntosacumulados=compra/puntos;

            String pago = $ + compra;
            String puntos1="Con esta compra obtendrías "+ puntosacumulados + " puntos";

            precio.setText(pago);
            puntosposibles.setText(puntos1);

        } else if (seleccion.equals("Caja x 150 bolsas $57.500")) {

            precio2 = 57500;
            puntos=1000;

            int compra = precio2 * seleccion2;
            int puntosacumulados=compra/puntos;

            String pago = $ + compra;
            String puntos2="Con esta compra obtendrías "+ puntosacumulados + " puntos";

            precio.setText(pago);
            puntosposibles.setText(puntos2);

        } else if (seleccion.equals("Caja x 300 bolsas $115.000")) {

            precio3 = 115000;
            puntos=1000;

            int compra = precio3 * seleccion2;
            int puntosacumulados=compra/puntos;;

            String pago = $ + compra;
            String puntos3="Con esta compra obtendrías  "+ puntosacumulados + " puntos";

            precio.setText(pago);
            puntosposibles.setText(puntos3);

        } else if (seleccion.equals("Caja x 480 bolsas $173.000")) {

            precio4 = 173000;
            puntos=1000;

            int compra = precio4 * seleccion2;
            int puntosacumulados=compra/puntos;;

            String pago = $ + compra;
            String puntos4="Con esta compra obtendrías "+ puntosacumulados + " puntos";

            precio.setText(pago);
            puntosposibles.setText(puntos4);

        } else {

        }

    } //fin caja seleccionada



        public void cerrarsesion(View view) {
           AuthUI.getInstance().signOut(Productos.this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Intent i = new Intent(Productos.this, AuthActivity.class);
                startActivity(i);
                Toast.makeText(Productos.this, " Sesión cerrada", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
         

        }

}