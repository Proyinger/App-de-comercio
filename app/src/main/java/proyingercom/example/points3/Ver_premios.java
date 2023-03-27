package proyingercom.example.points3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Ver_premios extends AppCompatActivity {

    //Widgets

    private Button uploadBtn, showAllBtn;
    private ImageView imageView;
    private ProgressBar progressBar;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Imagen");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private static final int GALLEY_INTENT = 1;
    private Uri imageUri;
    private TextView premioED;
    private TextView puntosED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_premios);


        uploadBtn = findViewById(R.id.uploa_dbtn);
        showAllBtn = findViewById(R.id.showall_btn);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imagenpremio);
        premioED = findViewById(R.id.premiodescrip);//Nuevo código
        puntosED = findViewById(R.id.puntosneces);//Nuevo código

        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        showAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Ver_premios.this, ShowActivity.class));
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLEY_INTENT);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imageUri != null || premioED != null || puntosED != null) {

                    uploadToFirebase(imageUri);
                    uploadToFirebase2(premioED,puntosED);


                } else {

                    Toast.makeText(Ver_premios.this, "Por favor seleccione la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (GALLEY_INTENT == 1 && resultCode == RESULT_OK && data != null) {

            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
    }

    private void uploadToFirebase2(final TextView puntosED, final TextView premioED) {

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {


                    String premio = snapshot.child("Imagen").getValue().toString();
                    String puntos = snapshot.child("Imagen").getValue().toString();


                    puntosED.setText(new StringBuilder(premio).append(" ").append(puntos));
                    puntosED.setText(new StringBuilder(puntos));


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }

    //Método cargar imagen a Realtime Database en Firebase

    private void uploadToFirebase(Uri uri) {

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));

        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Mensaje_premios mensaje_premios = new Mensaje_premios(uri.toString());
                        String mensaje_premiosId = root.push().getKey();
                        root.child(mensaje_premiosId).setValue(mensaje_premios);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(Ver_premios.this, "La carga ha sido exitosa", Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(Ver_premios.this, "La carga ha fallado", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getFileExtension(Uri mUri) {

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }

}

