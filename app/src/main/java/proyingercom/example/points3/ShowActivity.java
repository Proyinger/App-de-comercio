package proyingercom.example.points3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList <Mensaje_premios> list;
    private MyAdapter adapter;
    private DatabaseReference database;

    //private DatabaseReference root =FirebaseDatabase.getInstance().getReference("Imagen");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database =FirebaseDatabase.getInstance().getReference();
        list =new ArrayList<>();
        adapter = new MyAdapter(ShowActivity.this, list);
        recyclerView.setAdapter(adapter);

        //root.addValueEventListener(new ValueEventListener() {
             database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Mensaje_premios mensaje_premios = dataSnapshot.getValue(Mensaje_premios.class);
                    list.add(mensaje_premios);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}