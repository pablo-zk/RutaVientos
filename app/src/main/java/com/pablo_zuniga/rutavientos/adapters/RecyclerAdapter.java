package com.pablo_zuniga.rutavientos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.models.Route;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerDataHolder> {

    List<Route> rutas;

    public RecyclerAdapter(List<Route> rutas) {
        this.rutas = rutas;
    }

    public class RecyclerDataHolder extends RecyclerView.ViewHolder {

    }

    @NonNull
    @Override
    public RecyclerAdapter.RecyclerDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_item, null, false);
        return new RecyclerDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.RecyclerDataHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
