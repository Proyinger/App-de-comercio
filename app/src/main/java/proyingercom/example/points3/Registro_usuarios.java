package proyingercom.example.points3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.grpc.Compressor;

public class Registro_usuarios extends AppCompatActivity {

    private Button registro;
    private ImageView subir_foto;
    private TextView nombreED;
    private TextView emailED;
    private TextView direccionED;
    private TextView apelllidosED;
    private FirebaseFirestore db;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private StorageReference mStorage;
    private ImageView logo;
    private ProgressDialog mProgressdialog;
    private static final int GALLEY_INTENT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        mStorage= FirebaseStorage.getInstance().getReference();


        db = FirebaseFirestore.getInstance();

        registro=(Button)  findViewById(R.id.datos_registrados);
        logo=(ImageView) findViewById(R.id.logo_comercio);
        subir_foto= (ImageView) findViewById(R.id.subir_foto);
        mProgressdialog =new ProgressDialog(this);
        database = FirebaseDatabase.getInstance();
        myRef =database.getReference("usuarios_1");

        subir_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Seleccionar imagenes del dispositivo para cargarlas

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLEY_INTENT);


            }
        });



    }//Fin del onCreate


   // Crear los datos del comercio y subirlos a Firebase

    protected void onActivityResult(int requestCode, int resultCode, Intent data)  {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLEY_INTENT && resultCode == RESULT_OK) {

            //Mostrar progreso de carga de la foto

            mProgressdialog.setTitle("Subiendo...");
            mProgressdialog.setMessage("Subiendo foto de tu negocio");
            mProgressdialog.setCancelable(false);
            mProgressdialog.show();

            //Obtener el Uri de la foto subida

            final Uri filepath = data.getData();

            //Subir la foto al Storage

            if (filepath != null) {

                final StorageReference fotoref = mStorage.child("fotos").child("usuarios_1").child(filepath.getLastPathSegment());

                fotoref.putFile(filepath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw new Exception();
                        }
                        return fotoref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull final Task<Uri> task) {

                        mProgressdialog.dismiss();

                        //Cargar la foto desdde el dispositivo y subirla al caché

                        Uri downloaduri = task.getResult();
                        Glide.with(Registro_usuarios.this)
                                .load(downloaduri)
                                .fitCenter()
                                .centerCrop()
                                .centerInside()
                                .into(logo);

                        Toast.makeText(Registro_usuarios.this, "Se subió correctamente", Toast.LENGTH_SHORT).show();

                        //Crear los datos del comercio en firebase y confirmarlos  con el botón registro

                        registro.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                nombreED = findViewById(R.id.nombre2);
                                apelllidosED = findViewById(R.id.apellidos2);
                                direccionED = findViewById(R.id.direccion2);
                                emailED = findViewById(R.id.email2);

                                final String nombre2 = nombreED.getText().toString();
                                final String apellidos2 = apelllidosED.getText().toString();
                                final String direccion2 = direccionED.getText().toString();
                                final String email2 = emailED.getText().toString();

                                if (nombre2.isEmpty()) {

                                    nombreED.setError("Este Campo no debe quedar vacio");
                                    Toast.makeText(Registro_usuarios.this, "Debes diligenciar todos los campos", Toast.LENGTH_SHORT).show();
                                    return;

                                } else if (apellidos2.isEmpty()) {

                                    apelllidosED.setError("Este Campo no debe quedar vacio");

                                    Toast.makeText(Registro_usuarios.this, "Debes diligenciar todos los campos", Toast.LENGTH_SHORT).show();
                                    return;

                                } else if (direccion2.isEmpty()) {

                                    direccionED.setError("Este Campo no debe quedar vacio");
                                    Toast.makeText(Registro_usuarios.this, "Debes diligenciar todos los campos", Toast.LENGTH_SHORT).show();
                                    return;
                                }  else if (!email2.contains("@") || !email2.contains(".")) {
                                    emailED.setError("Email no válido");

                                    return;
                                } else if (email2.isEmpty()) {
                                    emailED.setError("Este Campo no debe quedar vacio");

                                    Toast.makeText(Registro_usuarios.this, "Debes diligenciar todos los campos", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {

                                    Map<String, Object> map = new HashMap<>();
                                    map.put("nombre_negocio", nombre2);
                                    map.put("nombre_propietario", apellidos2);
                                    map.put("direccion", direccion2);
                                    map.put("email", email2);
                                    map.put("imagen", filepath.toString());

                                    db.collection("comercios").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {

                                            Toast.makeText(Registro_usuarios.this, "Has realizado correctamente el registro", Toast.LENGTH_SHORT).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Registro_usuarios.this, "No realizó correctamente el registro", Toast.LENGTH_SHORT).show();
                                            //String errorMessage = e.getMessage();
                                            Log.i("info", "No se puede autenticar", e);

                                        }
                                    });

                                }

                            }
                        });

                    }
                });

            }


        }

    } //Fin del OnActivityResult


} //Fin de la clase Registro_Usuarios





















