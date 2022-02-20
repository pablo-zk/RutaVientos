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
import android.widget.ImageView;
import android.widget.TextView;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.adapters.RoutesAdapter;
import com.pablo_zuniga.rutavientos.models.Route;
import com.pablo_zuniga.rutavientos.models.User;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class  UserFragment extends Fragment {

    Realm realm;
    RealmResults<User> realmUser;
    DataListener callback;
    TextView txtNombreCompleto;
    TextView txtFechaCreacion;
    ImageView imgPerfil;
    TextView txtTelefono;
    TextView txtPuntos;
    TextView txtCo2;
    TextView txtMember;
    RecyclerView recyclerRutas;

    public UserFragment() {
        // Required empty public constructor
    }

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        txtNombreCompleto = (TextView) view.findViewById(R.id.txtCompleteName);
        txtFechaCreacion = (TextView) view.findViewById(R.id.txtFechaCreacion);
        txtPuntos = (TextView) view.findViewById(R.id.txtVotos);
        txtCo2 = (TextView) view.findViewById(R.id.txtCo2);
        txtTelefono = (TextView) view.findViewById(R.id.txtTelefono);
        txtMember = (TextView) view.findViewById(R.id.txtMember);
        imgPerfil = (ImageView) view.findViewById(R.id.imgProfile);
        recyclerRutas = (RecyclerView) view.findViewById(R.id.recylclerRutas);

        realm = Realm.getDefaultInstance();
        realmUser = realm.where(User.class).equalTo("isActive",Boolean.TRUE).findAll();
        User userActive = realmUser.first();

        String aux = userActive.getNombre() + " " + userActive.getApellido();
        txtNombreCompleto.setText(aux);
        aux = "Miembro desde el " + userActive.getFechaCreacion();
        txtFechaCreacion.setText(aux);
        aux = userActive.getPuntuacion() + " votos";
        txtPuntos.setText(aux);
        aux = "Reducidos " + userActive.getRoutesId().size()*10 + " puntos de CO2 en " + userActive.getRoutesId().size()+" viajes";
        txtCo2.setText(aux);

        //txtTelefono.setText(userActive.getTelefono());



        //METER PARA PRUEBA UNA RUTA PARA ESTE USUARIO - CREATE RUTA TODAVIA NO HECHO
        realm = Realm.getDefaultInstance();
        Route ruta = new Route("Cuatro Vientos", "La morea", 3, new Date(2022, 2, 28, 10, 30,00),userActive.getId());
        realm.beginTransaction();
        realm.copyToRealm(ruta);
        realm.commitTransaction();
        realm = Realm.getDefaultInstance();

        RealmResults<Route> realmRutas = realm.where(Route.class).equalTo("driver",userActive.getId()).findAll();

        //Calcular en base a las rutas que ha creado el nivel de miembro: Member/Pro/Expert
        if(realmRutas.size() < 5){
            txtMember.setText("RutaVientos member");
        }else if(realmRutas.size() < 10){
            txtMember.setText("RutaVientos Pro member");
        }else{
            txtMember.setText("RutaVientos Expert member");
        }

        recyclerRutas.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        RoutesAdapter routesAdapter = new RoutesAdapter(realmRutas, new RoutesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Route ruta, int position) {
                callback.sendData(ruta);
            }
        });
        recyclerRutas.setAdapter(routesAdapter);
        return view;
    }

    public interface DataListener {
        public void sendData(Route route);
    }
}