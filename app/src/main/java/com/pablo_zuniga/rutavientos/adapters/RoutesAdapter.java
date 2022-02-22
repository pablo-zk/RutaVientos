package com.pablo_zuniga.rutavientos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.models.Route;
import com.pablo_zuniga.rutavientos.models.User;

import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RoutesDataHolder> {

    private final List<Route> routesLst;
    private final OnItemClickListener itemListener;

    public RoutesAdapter(List<Route> routes,  OnItemClickListener itemListener) {
        this.routesLst = routes;
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
        holder.assignData(routesLst.get(position),itemListener);
    }

    @Override
    public int getItemCount() {
        return routesLst.size();
    }

    public static class RoutesDataHolder extends RecyclerView.ViewHolder {
        TextView trip;
        TextView hour;
        TextView seats;
        TextView driver;

        public RoutesDataHolder(@NonNull View itemView) {
            super(itemView);
            trip = (TextView) itemView.findViewById(R.id.cardTitle);
            hour = (TextView) itemView.findViewById(R.id.cardHour);
            seats = (TextView) itemView.findViewById(R.id.cardPlaces);
            driver = (TextView) itemView.findViewById(R.id.cardDriver);
        }

        public void assignData(Route ruta, OnItemClickListener itemListener){

            this.trip.setText(String.format("%s - %s", ruta.getOrigin(), ruta.getDestiny()));
            this.hour.setText(String.valueOf(ruta.getDateHour().getHours()) + ":" + String.valueOf(ruta.getDateHour().getMinutes()));
            this.seats.setText(String.format(Locale.getDefault(), "%d plazas", ruta.getFreeSeats()));

            Realm realm = Realm.getDefaultInstance();
            User user = realm.where(User.class).equalTo("id",ruta.getDriver()).findFirst();
            this.driver.setText( String.format("Publicado por %s", user.getUsername()));

            itemView.setOnClickListener(view -> itemListener.onItemClick(ruta,getAdapterPosition()));
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Route route, int position);
    }
}
