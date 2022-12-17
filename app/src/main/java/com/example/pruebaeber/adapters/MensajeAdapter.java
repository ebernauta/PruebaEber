package com.example.pruebaeber.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pruebaeber.R;
import com.example.pruebaeber.models.Mensaje;

import java.util.ArrayList;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.ViewHolder>{

    private int resource;
    private ArrayList<Mensaje> mensajesList;

    public  MensajeAdapter(ArrayList<Mensaje> mensajesList, int resource){
        this.mensajesList = mensajesList;
        this.resource = resource;
    }

    @NonNull
    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @androidx.annotation.NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @androidx.annotation.NonNull ViewHolder viewHolder, int index) {

        Mensaje mensaje = mensajesList.get(index);
        viewHolder.textViewMensaje.setText(mensaje.getEmail());

    }

    @Override
    public int getItemCount() {
        return mensajesList.size();
    }

    public  class  ViewHolder extends  RecyclerView.ViewHolder{
        private TextView textViewMensaje;

        public  View view;

        public  ViewHolder(View view){
            super(view);

            this.view = view;
            this.textViewMensaje = (TextView) view.findViewById(R.id.textViewMensaje);
        }
    }
}
