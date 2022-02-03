package com.pablo_zuniga.rutavientos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.models.Route;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity {
    ArrayList<Route> routes;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();

        routes = new ArrayList<>();
        //Busca calendar par la fecha de salida
        routes.add(new Route("Cuatro Vientos", "La morea", 3, new Date(2022, 2, 11, 10, 30),"Pablo" ));
        routes.add(new Route("Estella", "Cuatro Vientos", 2, new Date(2022, 2, 17, 6, 30),"Asier" ));
        routes.add(new Route("Cuatro Vientos", "Itaroa", 4, new Date(2022, 2, 11, 19, 30),"Gorka" ));
        //realm.deleteAll();
        realm.beginTransaction();
        realm.copyToRealm(routes);
        realm.commitTransaction();
    }


}