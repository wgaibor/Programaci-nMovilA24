package ec.edu.lemas.teclemasvet;
/**https://hackernoon.com/user-authentication-in-android-using-firebase-java*/
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText correo;
    EditText password;
    Button btnIngresar;
    TextView txtRegistrarte;

    FirebaseAuth autenticacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        correo = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        btnIngresar = findViewById(R.id.signup_button);
        btnIngresar.setOnClickListener(this);
        txtRegistrarte = findViewById(R.id.loginRedirectText);
        txtRegistrarte.setOnClickListener(this);

        autenticacion = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signup_button) {
            llamarALaSiguienteActividad();
            validarIncioSession();
        }
        if (view.getId() == R.id.loginRedirectText){
            registrarUsuario();
        }

    }

    private void registrarUsuario() {
        Intent variableDeIntento = new Intent(this, RegisterActivity.class);
        startActivity(variableDeIntento);
    }

    private void validarIncioSession() {
        String user = correo.getText().toString();
        String pass = password.getText().toString();

        if(user.isEmpty()){
            Toast.makeText(this, "Debes ingresar un usuario", Toast.LENGTH_LONG).show();
        }
        else if (pass.isEmpty()) {
            Toast.makeText(this, "La contraseña no puede ser vacia", Toast.LENGTH_LONG).show();
        } else {
            autenticacion.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Bienvenido!!!!", Toast.LENGTH_LONG).show();
                        llamarALaSiguienteActividad();
                    } else {
                        Toast.makeText(MainActivity.this, "No existes!!!!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void llamarALaSiguienteActividad() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }


}