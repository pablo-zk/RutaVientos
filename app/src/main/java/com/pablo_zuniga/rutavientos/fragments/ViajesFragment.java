package com.pablo_zuniga.rutavientos.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.adapters.RoutesAdapter;
import com.pablo_zuniga.rutavientos.models.Route;
import com.pablo_zuniga.rutavientos.models.User;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class ViajesFragment extends Fragment {

    RoutesFragment.DataListener callback;
    RecyclerView recyclerRoutes;
    Realm realm;
    public RealmResults<Route> realmResults;
    public RealmResults<User> realmUser;
    ArrayList<Route> listViajes = new ArrayList<Route>();

    public ViajesFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            callback = (RoutesFragment.DataListener) context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + "should implement DataListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes, container, false);

        this.recyclerRoutes = (RecyclerView) view.findViewById(R.id.recyclerRoutes);

        realm = Realm.getDefaultInstance();
        realmUser = realm.where(User.class).equalTo("isActive",true).findAll();
        realmResults = realm.where(Route.class).notEqualTo("driver",realmUser.get(0).getId()).findAll();
        for (Route route : realmResults) {
            for (int idR : realmUser.get(0).getRoutesId()) {
                if (route.getId() == idR && !listViajes.contains(route)){
                    listViajes.add(route);
                }
            }
        }

        this.recyclerRoutes.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        RoutesAdapter routesAdapter = new RoutesAdapter(listViajes, (ruta, position) -> {
            callback.sendData(ruta);
        });
        this.recyclerRoutes.setAdapter(routesAdapter);
        return view;
    }

    public interface DataListener {
        public void sendData(Route route);
    }
}