package com.example.lds.dozvole;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {


    private EditText Txtemail;
    private EditText Txtpassword;
    private ProgressBar cargar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Txtemail = findViewById(R.id.Textemail);
        Txtpassword = findViewById(R.id.TextPassword);
        mAuth = FirebaseAuth.getInstance();
        cargar=findViewById(R.id.progress);


    }

    public void login_user_click(View v) {
        startSignIn();

    }

    private void startSignIn() {
        final String email = Txtemail.getText().toString().trim();
        String password = Txtpassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        cargar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Bienvenido a Dozvole", Toast.LENGTH_LONG).show();
                            Intent intencion = new Intent(getApplication(), contenidos.class);
                            startActivity(intencion);

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(Login.this, "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Login.this, "Usuario No válido", Toast.LENGTH_LONG).show();
                            }
                        }
                        cargar.setVisibility(View.INVISIBLE);

                    }
                });


    }
}






