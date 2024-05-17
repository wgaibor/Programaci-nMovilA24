package ec.edu.lemas.teclemasvet.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ec.edu.lemas.teclemasvet.R;
import ec.edu.lemas.teclemasvet.entity.PetEntity;

public class AnimalRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nombreMascota;
    EditText edadMascota;
    EditText tipoMascota;
    Button btnGuardarMascota;

    FirebaseFirestore db;

    ProgressDialog dialogoProgreso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_register);

        nombreMascota = (EditText) findViewById(R.id.edtNamePet);
        edadMascota =(EditText) findViewById(R.id.edtAgePet);
        tipoMascota = (EditText) findViewById(R.id.edtTypePet);
        btnGuardarMascota = (Button) findViewById(R.id.btnSavePet);
        btnGuardarMascota.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();
        dialogoProgreso = new ProgressDialog(this);
        dialogoProgreso.setMessage("Grabando Información");
    }

    @Override
    public void onClick(View view) {
        String nombreM = nombreMascota.getText().toString();
        String edadM = edadMascota.getText().toString();
        String tipoM = tipoMascota.getText().toString();

        //guardarEnBaseUsandoMapas(nombreM, edadM, tipoM);

        guardarUsandoObjetos(nombreM, edadM, tipoM);
    }

    private void guardarUsandoObjetos(String nombreM, String edadM, String tipoM) {
        dialogoProgreso.show();
        PetEntity mascotaObjeto = new PetEntity();
        mascotaObjeto.setNombre(nombreM);
        mascotaObjeto.setEdad(edadM);
        mascotaObjeto.setTipo(tipoM);

        db.collection("mascotas")
                .add(mascotaObjeto)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        dialogoProgreso.dismiss();
                        mostrarMensaje("Se inserto el registro!!!!");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialogoProgreso.dismiss();
                        e.printStackTrace();
                    }
                });
    }

    private void guardarEnBaseUsandoMapas(String nombreM, String edadM, String tipoM) {
        //-------------------
        Map<String, Object> pets = new HashMap<>();
        pets.put("nombre", nombreM);
        pets.put("edad", edadM);
        pets.put("tipo", tipoM);
        //-------------------

        db.collection("mascotas")
                .add(pets)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        mostrarMensaje("Se inserto el registro!!!!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}