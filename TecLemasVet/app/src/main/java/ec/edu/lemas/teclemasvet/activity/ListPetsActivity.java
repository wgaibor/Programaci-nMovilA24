package ec.edu.lemas.teclemasvet.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ec.edu.lemas.teclemasvet.R;
import ec.edu.lemas.teclemasvet.adapter.ListPetsAdapter;
import ec.edu.lemas.teclemasvet.entity.PetEntity;

public class ListPetsActivity extends AppCompatActivity {

    RecyclerView rvListadoMascota;
    ListPetsAdapter adapter;
    List<PetEntity> lstMascota;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pets);
        rvListadoMascota = findViewById(R.id.rv_listPets);
        lstMascota = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        consultarDatos();
    }

    private void consultarDatos() {
        db.collection("mascotas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            lstMascota = new ArrayList<>();
                            for (DocumentSnapshot documento : task.getResult()){
                                lstMascota.add(documento.toObject(PetEntity.class));
                            }
                            llenarListado();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private void llenarListado() {
        rvListadoMascota.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListPetsAdapter(this, lstMascota);
        rvListadoMascota.setHasFixedSize(true);
        rvListadoMascota.setAdapter(adapter);
    }
}