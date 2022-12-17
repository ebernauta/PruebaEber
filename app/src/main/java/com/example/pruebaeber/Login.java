package com.example.pruebaeber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.loginEmail);
        contraseña = findViewById(R.id.loginContraseña);
        btnAcceder = findViewById(R.id.acceder);
        btnRegistrar = findViewById(R.id.registrarse);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(Login.this, RegistrarUsuario.class));
                Toast.makeText(Login.this, "Por favor rellene los campos para registrar el nuevo usuario", Toast.LENGTH_SHORT).show();
            }
        });

        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = email.getText().toString().trim();
                String passUser = contraseña.getText().toString().trim();
                if (emailUser.isEmpty() && passUser.isEmpty()){
                    Toast.makeText(Login.this, "Porfavor rellene los campos", Toast.LENGTH_SHORT).show();
                }else {
                    loginUser(emailUser, passUser);
                }
            }
        });

    }

    private void  loginUser(String emailUser, String passUser){
        auth.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(Login.this, MenuActivity.class));
                    Toast.makeText(Login.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "Error al inicar sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}