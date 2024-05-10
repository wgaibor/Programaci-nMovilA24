package ec.edu.lemas.teclemasvet;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AnimalRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nombreMascota;
    EditText edadMascota;
    EditText tipoMascota;
    Button btnGuardarMascota;

    FirebaseFirestore db;
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
    }

    @Override
    public void onClick(View view) {
        String nombreM = nombreMascota.getText().toString();
        String edadM = edadMascota.getText().toString();
        String tipoM = tipoMascota.getText().toString();
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