package ec.edu.lemas.teclemasvet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ec.edu.lemas.teclemasvet.R;
import ec.edu.lemas.teclemasvet.adapter.ListPetsAdapter;
import ec.edu.lemas.teclemasvet.entity.PetEntity;

public class ListPetsActivity extends AppCompatActivity implements ListPetsAdapter.onItemSelectedListener {

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
        //consultarDatos();
        consultarDatosEnTiempoReal();


    }

    private void consultarDatosEnTiempoReal() {
        db.collection("mascotas")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot valor, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            e.printStackTrace();
                        }

                        if (valor != null) {
                            lstMascota = new ArrayList<>();
                            for (QueryDocumentSnapshot documento : valor){
                                lstMascota.add(documento.toObject(PetEntity.class));
                            }
                            llenarListado();
                        } else {
                            Log.d(">>>>>>>", "Current data: null");
                        }
                    }
                });
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
        adapter.setListener(this);
    }

    @Override
    public void onItemSelected(int position) {
        Intent intento = new Intent(this, DetailsItemsActivity.class);
        PetEntity objMascota = lstMascota.get(position);
        intento.putExtra("imgUrl", objMascota.getUrl());
        intento.putExtra("nombreMascota", objMascota.getNombre());
        intento.putExtra("tipoMascota", objMascota.getTipo());
        startActivity(intento);
    }
}