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
import android.widget.Switch;
import android.widget.TextView;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.adapters.RoutesAdapter;
import com.pablo_zuniga.rutavientos.models.Route;
import com.pablo_zuniga.rutavientos.models.User;

import java.lang.reflect.Array;
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
    Switch switchRoutes;
    RecyclerView recyclerRutas;
    User userActive;
    RealmResults<Route> realmRutas;
    ArrayList<Route> arrayRoute;
    RealmResults<Route> realmRutasA;
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
        switchRoutes = (Switch) view.findViewById(R.id.switchRutasUser);

        realm = Realm.getDefaultInstance();
        //realmUser = realm.where(User.class).equalTo("isActive",Boolean.TRUE).findAll();
        realmUser = realm.where(User.class).findAll();
        for (User user : realmUser) {
            if (user.isActive()){
                userActive = user;
            }
        }


        String aux = userActive.getNombre() + " " + userActive.getApellido();
        txtNombreCompleto.setText(aux);
        txtTelefono.setText(String.valueOf(userActive.getTelefono()));
        aux = "Miembro desde el " + userActive.getFechaCreacion();
        txtFechaCreacion.setText(aux);
        aux = userActive.getPuntuacion() + " votos";
        txtPuntos.setText(aux);
        aux = "Reducidos " + userActive.getRoutesId().size()*10 + " puntos de CO2 en " + userActive.getRoutesId().size()+" viajes";
        txtCo2.setText(aux);

        //txtTelefono.setText(userActive.getTelefono());

        if(switchRoutes.isChecked()){
            //Obtengo todas las rutas del usuario
            realmRutas = realm.where(Route.class).equalTo("driver",userActive.getId()).findAll();
            showRoutes(view);
        }else{
            //Obtener todas las rutas NO creadas por el usuario y buscar cuales coinciden con su getIdRoutes().
            realmRutas = realm.where(Route.class).notEqualTo("driver",userActive.getId()).findAll();
            showRoutes(view);
        }

        showRoutes(view);
        //Calcular en base a las rutas que ha creado el nivel de miembro: Member/Pro/Expert
        if(realmRutas.size() < 5){
            txtMember.setText("RutaVientos member");
        }else if(realmRutas.size() < 10){
            txtMember.setText("RutaVientos Pro member");
        }else{
            txtMember.setText("RutaVientos Expert member");
        }

        //TODO Añadido nuevo, la cosa sería pasar al recycler adpater una lista u otra
        switchRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmResults<Route> routesUser;
                if(switchRoutes.isChecked()){
                    //Obtengo todas las rutas del usuario
                    realmRutas = realm.where(Route.class).equalTo("driver",userActive.getId()).findAll();
                    showRoutes(view);
                }else{
                    //Obtener todas las rutas NO creadas por el usuario y buscar cuales coinciden con su getIdRoutes().
                    realmRutas = realm.where(Route.class).notEqualTo("driver",userActive.getId()).findAll();
                    for ( Route r: realmRutas ) {

                    }
                    showRoutes(view);
                }
            }
        });


        return view;
    }

    public void showRoutes(View view){
        recyclerRutas.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        RoutesAdapter routesAdapter = new RoutesAdapter(realmRutas, (ruta, position) -> callback.sendData(ruta));
        recyclerRutas.setAdapter(routesAdapter);
    }
    public interface DataListener {
        public void sendData(Route route);
    }
}