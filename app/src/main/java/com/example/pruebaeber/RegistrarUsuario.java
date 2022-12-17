package com.example.pruebaeber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuario extends AppCompatActivity {

    Button btnRegistrar;
    EditText Email, Contraseña;

    FirebaseAuth auth;
    DatabaseReference mdataBase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedinstanceState){
        super.onCreate(savedinstanceState);
        setContentView(R.layout.fragment_registrar_usuario);
        auth = FirebaseAuth.getInstance();
        mdataBase = FirebaseDatabase.getInstance().getReference();

        mdataBase.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( final DataSnapshot  snapshot1: snapshot.getChildren()){
                    mdataBase.child("Usuarios").child(snapshot1.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            usuarios user = snapshot1.getValue(usuarios.class);
                            String email = user.getEmail();
                            String contraseña = user.getContraseña();

                            Log.e("EmailUsuario:", ""+email);
                            Log.e("ContraseñaUsuario:", ""+contraseña);
                            

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Email = findViewById(R.id.registrarEmail);
        Contraseña = findViewById(R.id.registrarContraseña);
        btnRegistrar = findViewById(R.id.registrarNuevoUsuario);
        progressDialog = new ProgressDialog(RegistrarUsuario.this);
        progressDialog.setMessage("Registrando nuevo usuario... espere por favor");
        progressDialog.setCancelable(false);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = Email.getText().toString().trim();
                String contraseñaUser = Contraseña.getText().toString().trim();

                if (emailUser.isEmpty() && contraseñaUser.isEmpty()){
                    Toast.makeText(RegistrarUsuario.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                }else {
                    registrarUsuarios(emailUser, contraseñaUser);
                }
            }

        });


    }
    private void registrarUsuarios(String emailUser, String contraseñaUser) {

        progressDialog.show();

        auth.createUserWithEmailAndPassword(emailUser, contraseñaUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Map<String, Object> map = new HashMap<>();
                    map.put("Email", emailUser);
                    map.put("Contraseña", contraseñaUser);

                    String id = auth.getCurrentUser().getUid();
                    mdataBase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            progressDialog.show();
                            progressDialog.setCancelable(false);
                            if (task2.isSuccessful()){
                                progressDialog.dismiss();
                                startActivity(new Intent(RegistrarUsuario.this, Login.class));
                                Toast.makeText(RegistrarUsuario.this, "Usuario Registrado con exito!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(RegistrarUsuario.this, "No se crearon los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrarUsuario.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegistrarUsuario.this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }

}