package com.example.pruebaeber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button btnRegistrar, btnAcceder;
    EditText email, contraseña;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Ingresando... espere por favor");
        progressDialog.setCancelable(false);

        email = findViewById(R.id.loginEmail);
        contraseña = findViewById(R.id.loginContraseña);
        btnAcceder = findViewById(R.id.acceder);
        btnRegistrar = findViewById(R.id.registrarse);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(Login.this, RegistrarUsuario.class));
            }
        });

        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = email.getText().toString().trim();
                String passUser = contraseña.getText().toString().trim();

                if (emailUser.equals("") || passUser.equals("")){
                    Toast.makeText(Login.this, "Por favor completar todos los campos", Toast.LENGTH_SHORT).show();
                }
                else {
                    //validaciones
                    if (!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()){
                        email.setError("Email invalido");
                        email.setFocusable(true);
                    }
                    else if (passUser.length() <= 6){
                        contraseña.setError("Contraseña debe ser mayor o igual a 6");
                        contraseña.setFocusable(true);
                    }
                    else {
                        loginUser(emailUser, passUser);
                    }
                }
            }
        });


    }

    private void  loginUser(String emailUser, String passUser){
        progressDialog.show();
        progressDialog.setCancelable(false);
        auth.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    finish();
                    startActivity(new Intent(Login.this, MenuActivity.class));
                    Toast.makeText(Login.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}