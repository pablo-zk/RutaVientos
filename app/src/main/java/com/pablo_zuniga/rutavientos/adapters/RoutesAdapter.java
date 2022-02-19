package com.pablo_zuniga.rutavientos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.models.Route;

import java.util.List;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RoutesDataHolder> {

    private List<Route> rutasLst;
    private OnItemClickListener itemListener;

    public RoutesAdapter(List<Route> rutas,  OnItemClickListener itemListener) {
        this.rutasLst = rutas;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public RoutesDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_item, parent, false);
        return new RoutesDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesDataHolder holder, int position) {
        holder.assignData(rutasLst.get(position),itemListener);
    }

    @Override
    public int getItemCount() {
        return rutasLst.size();
    }

    public class RoutesDataHolder extends RecyclerView.ViewHolder {
        TextView origenDestino;
        TextView hora;
        TextView plazas;
        TextView conductor;
        public RoutesDataHolder(@NonNull View itemView) {
            super(itemView);
            origenDestino = (TextView) itemView.findViewById(R.id.cardTitle);
            hora = (TextView) itemView.findViewById(R.id.cardHour);
            plazas = (TextView) itemView.findViewById(R.id.cardPlaces);
            conductor = (TextView) itemView.findViewById(R.id.cardDriver);
        }
        public void assignData(Route ruta, OnItemClickListener itemListener){
            this.origenDestino.setText("Origen - Destino");
            this.hora.setText("hora");
            this.plazas.setText("plazas");
            this.conductor.setText("Publicado por ");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemListener.onItemClick(ruta,getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Route ruta, int position);
    }
}
