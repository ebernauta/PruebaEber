package com.example.pruebaeber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.example.pruebaeber.adapters.MensajeAdapter;
import com.example.pruebaeber.models.Mensaje;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private  EditText mEditTextMensaje;
    private Button mBtnCrearDatos;

    private DatabaseReference mDatabase;

    private MensajeAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Mensaje> mMensajesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMensajes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getMensajesFromFirebase();


    }

    private void getMensajesFromFirebase(){
        mDatabase.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    for(DataSnapshot ds: snapshot.getChildren()){
                        String Email = ds.child("Email").getValue().toString();
                        mMensajesList.add(new Mensaje(Email));
                    }

                    mAdapter = new MensajeAdapter(mMensajesList, R.layout.mensaje_view);
                    mRecyclerView.setAdapter(mAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}