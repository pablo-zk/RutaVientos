package com.pablo_zuniga.rutavientos.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.activities.MainActivity;
import com.pablo_zuniga.rutavientos.activities.RouteDetails;
import com.pablo_zuniga.rutavientos.adapters.RoutesAdapter;
import com.pablo_zuniga.rutavientos.models.Route;
import com.pablo_zuniga.rutavientos.models.User;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class RoutesFragment extends Fragment {

    DataListener callback;
    RecyclerView recyclerRoutes;
    Realm realm;
    public RealmResults<Route> realmResults;

    public RoutesFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            callback = (DataListener) context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + "should implement DataListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes, container, false);

        Toast.makeText(view.getContext(),"Este es el fragment Routes", Toast.LENGTH_SHORT).show();
        this.recyclerRoutes = (RecyclerView) view.findViewById(R.id.recyclerRoutes);

        realm = Realm.getDefaultInstance();
        realmResults = realm.where(Route.class).findAll();

        this.recyclerRoutes.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        RoutesAdapter routesAdapter = new RoutesAdapter(realmResults, (ruta, position) -> {
            //¿sendData? - enviar la informacion de la ruta a una vista detalles para apuntarse.
        });
        this.recyclerRoutes.setAdapter(routesAdapter);
        return view;
    }

    public interface DataListener {
        public void sendData(Route route);
    }
}