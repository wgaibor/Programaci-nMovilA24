package ec.edu.lemas.teclemasvet.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import ec.edu.lemas.teclemasvet.R;
import ec.edu.lemas.teclemasvet.entity.PetEntity;

public class AnimalRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nombreMascota;
    EditText edadMascota;
    EditText tipoMascota;
    Button btnGuardarMascota;

    ImageView imagenMascota;
    FirebaseFirestore db;

    ProgressDialog dialogoProgreso;

    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_register);

        nombreMascota = (EditText) findViewById(R.id.edtNamePet);
        edadMascota =(EditText) findViewById(R.id.edtAgePet);
        tipoMascota = (EditText) findViewById(R.id.edtTypePet);
        btnGuardarMascota = (Button) findViewById(R.id.btnSavePet);
        btnGuardarMascota.setOnClickListener(this);
        imagenMascota = (ImageView) findViewById(R.id.imgPet);
        imagenMascota.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        dialogoProgreso = new ProgressDialog(this);
        dialogoProgreso.setMessage("Grabando Información");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.imgPet) {
            loaderImage();
        } else if (view.getId() == R.id.btnSavePet) {
            subirArchivo();
        }

    }



    private void loaderImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    private void guardarUsandoObjetos(String nombreM, String edadM, String tipoM, String path) {
        dialogoProgreso.show();
        PetEntity mascotaObjeto = new PetEntity();
        mascotaObjeto.setNombre(nombreM);
        mascotaObjeto.setEdad(edadM);
        mascotaObjeto.setTipo(tipoM);
        mascotaObjeto.setUrl(path);

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

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imagenMascota.setImageBitmap(bitmap);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(250, 250);
                layoutParams.setMargins(0, 0, 0, 30);
                imagenMascota.setLayoutParams(layoutParams);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void subirArchivo() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Subiendo foto...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(AnimalRegisterActivity.this,
                                                    "Imagen subida!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isSuccessful()){
                                        if (uriTask.isSuccessful()){
                                            uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    siguientePaso(uri.toString());
                                                }
                                            });
                                        }
                                    }

                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(AnimalRegisterActivity.this,
                                            "Fallo la subida " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();

                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }

    private void siguientePaso(String path) {
        String nombreM = nombreMascota.getText().toString();
        String edadM = edadMascota.getText().toString();
        String tipoM = tipoMascota.getText().toString();

        guardarUsandoObjetos(nombreM, edadM, tipoM, path);
    }
}