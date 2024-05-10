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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText usuario;
    EditText contrasenia;
    Button btnRegistrar;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usuario = findViewById(R.id.edtCorreo);
        contrasenia = findViewById(R.id.edtContrasena);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        String user = usuario.getText().toString();
        String pass = contrasenia.getText().toString();

        if(user.isEmpty()){
            Toast.makeText(this, "Debes ingresar un usuario", Toast.LENGTH_LONG).show();
        }
        else if (pass.isEmpty()) {
            Toast.makeText(this, "La contraseña no puede ser vacia", Toast.LENGTH_LONG).show();
        } else {
            auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registrado!!!!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "No se pudo registrar!!!!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}