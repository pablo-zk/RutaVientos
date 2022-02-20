package com.pablo_zuniga.rutavientos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.models.Route;
import com.pablo_zuniga.rutavientos.models.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class RouteDetails extends AppCompatActivity {

    Realm realm;
    Route routeActual;
    User userOfRoute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        realm = Realm.getDefaultInstance();

        Bundle bundle = getIntent().getExtras();
        if(bundle.containsKey("id")){
            routeActual = realm.where(Route.class).equalTo("id",bundle.getInt("id")).findFirst();
            userOfRoute = realm.where(User.class).equalTo("id",routeActual.getDriver()).findFirst();
        }
        //TODO partir de aquí poner los datos en los txt o lo que haya en la view

    }
}