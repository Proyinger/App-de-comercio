package proyingercom.example.points3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.core.SnapshotHolder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SnapshotMetadata;
import com.google.firestore.v1.Document;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Puntos_acumulados<BasicEventsCallback> extends AppCompatActivity {


    private TextView Datausuario;
    private TextView correo;
    private Button consulta_puntos;
    private FirebaseFirestore db;
    private Spinner spinnercomercios;
    private Object View;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos_acumulados);

        Datausuario = (TextView) findViewById(R.id.vData);
        consulta_puntos = (Button) findViewById(R.id.consulta);
        correo = (TextView) findViewById(R.id.email2);
        db = FirebaseFirestore.getInstance();
        spinnercomercios = (Spinner) findViewById(R.id.establecimientos);

        //Lista desplegable de los comercios que aparecen en la base de datos
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.combo_comercios, android.R.layout.simple_spinner_item);
        spinnercomercios.setAdapter(adapter1);

        //Consultar puntos acumulados según tienda seleccionada y correo del usuario

        consulta_puntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerdatos();
            }
        });


        obtenerdatos();

    }

    //Método para pasar a la activity Premios para visualizar los premios por tienda

    public void VerPremios(View view){

        Intent consultapremios = new Intent(this,ShowActivity.class);

        startActivity(consultapremios);

    }

    //Método para pasar ver los puntos acumulados por usuario ingresando el correo electrónico (según base de datos Firebase)

    private void obtenerdatos() {

        final String puntos_usuario = correo.getText().toString();


            if (!puntos_usuario.isEmpty()) {

                String seleccion = spinnercomercios.getSelectedItem().toString();

                db.collection(seleccion).document(puntos_usuario).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            String nombre = documentSnapshot.getString("nombre_comprador");
                            int puntos = documentSnapshot.getLong("puntos_comprador").intValue();

                            Datausuario.setText("Hola " + nombre + " en este comercio has acumulado hasta ahora " + puntos + " puntos " + " " +
                                    "los cuales podrás redimir de acuerdo al catálogo de premios de la tienda" + "\n" + "\n" +

                                    "Contacta al vendedor para canjear el producto en su establecimiento físico o a domicilio si es tienda virtual");



                        } else {


                            Toast.makeText(Puntos_acumulados.this, "Si eres usuario nuevo y solo has realizado una compra, tus puntos solo se reflejarán 24 horas después de tu primer pago", Toast.LENGTH_LONG).show();
                        }


                    }
                });



            } else {
                Toast.makeText(Puntos_acumulados.this, "Ingrese datos de usuario", Toast.LENGTH_LONG).show();
            }




    }



}