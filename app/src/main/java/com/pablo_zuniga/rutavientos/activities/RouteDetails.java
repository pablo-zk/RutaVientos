package com.pablo_zuniga.rutavientos.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.models.Route;
import com.pablo_zuniga.rutavientos.models.User;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RouteDetails extends AppCompatActivity {

    Realm realm;
    Route routeActual;
    User userOfRoute;
    User userCurrent;
    Button btnApuntarse;

    TextView txtName;
    TextView txtContacto;

    TextView txtOrigen;
    TextView txtOrigenC;
    TextView txtDestino;
    TextView txtDestinoC;

    @SuppressLint("ResourceType")
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
            userCurrent = realm.where(User.class).equalTo("isActive",true).findFirst();
       }
        btnApuntarse = (Button) findViewById(R.id.btnApuntarse);
        if (userCurrent.getId() == userOfRoute.getId()){
            btnApuntarse.setText("Eliminar");
            btnApuntarse.setBackground(getDrawable(R.drawable.button_del));
        } else {
            //TODO partir de aquí poner los datos en los txt o lo que haya en la view
            if(userCurrent.getRoutesId().contains(routeActual.getId())){
                btnApuntarse.setText("Desapuntarse");
            }else{
                btnApuntarse.setText("Apuntarse");
            }
        }


        btnApuntarse.setOnClickListener(view -> {
            if (btnApuntarse.getText().toString() == "Eliminar"){
                showInfoAlert("¿Seguro que quieres eliminar la ruta?");

            } else {
                if(userCurrent.getRoutesId().contains(routeActual.getId())){
                    showInfoAlert("¿Seguro que quieres desapuntarte de la ruta " + routeActual.getOrigin() + "-" + routeActual.getDestiny() + " a las " + routeActual.getDateHour() + "?");
                }else{
                    if(routeActual.getFreeSeats() == 0){
                        Toast.makeText(getBaseContext(),"ERROR: No quedan plazas disponibles", Toast.LENGTH_SHORT).show();
                    }else{
                        showInfoAlert("¿Seguro que quieres apuntarte de la ruta " + routeActual.getOrigin() + "-" + routeActual.getDestiny() + " a las " + routeActual.getDateHour() + "?");

                    }
                }
            }


        });

        //TODO Mostrar datos

        this.txtName = (TextView) findViewById(R.id.txtName);
        this.txtContacto = (TextView) findViewById(R.id.txtContact);

        txtName.setText(userOfRoute.getUsername().toString());
        txtContacto.setText("Contacta con " + userOfRoute.getUsername().toString());

        this.txtOrigen = (TextView) findViewById(R.id.idOrigen);
        this.txtOrigenC = (TextView) findViewById(R.id.idOrigenC);
        this.txtDestino = (TextView) findViewById(R.id.idDestino);
        this.txtDestinoC = (TextView) findViewById(R.id.idDestinoC);

        if (routeActual.getOrigin().contains("Cuatrovientos")){
            String[] destino = routeActual.getDestiny().split(",");
            this.txtDestinoC.setText(destino[0].toString());
            this.txtDestino.setText(destino[2].toString());
            this.txtOrigenC.setText(routeActual.getOrigin().toString());
            this.txtOrigen.setText(routeActual.getOrigin().toString());
        } else {
            String[] origen = routeActual.getOrigin().split(",");
            this.txtOrigenC.setText(origen[0].toString());
            this.txtOrigen.setText(origen[2].toString());
            this.txtDestinoC.setText(routeActual.getDestiny().toString());
            this.txtDestino.setText(routeActual.getDestiny().toString());
        }

    }

    private void showInfoAlert(String message){
        new AlertDialog.Builder(this)
                .setTitle("CONFIRMACIÓN")
                .setMessage(message)
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    if (message.contains("eliminar")){
                        realm.beginTransaction();
                        routeActual.deleteFromRealm();
                        realm.commitTransaction();
                        Toast.makeText(getBaseContext(),"Ruta eliminada con exito", Toast.LENGTH_SHORT).show();
                    } else {
                        realm.beginTransaction();
                        if(message.contains("desapuntarte")){
                            userCurrent.getRoutesId().remove(userCurrent.getRoutesId().indexOf(routeActual.getId()));
                            routeActual.setFreeSeats(routeActual.getFreeSeats() + 1);
                            btnApuntarse.setText("apuntarte");
                            Toast.makeText(getBaseContext(),"Te has desapuntado con exito", Toast.LENGTH_SHORT).show();
                        }else{
                            userCurrent.getRoutesId().add(routeActual.getId());
                            routeActual.setFreeSeats(routeActual.getFreeSeats() - 1);
                            btnApuntarse.setText("Desapuntarse");
                            Toast.makeText(getBaseContext(),"Te has apuntado con exito", Toast.LENGTH_SHORT).show();
                        }
                        realm.copyToRealmOrUpdate(userCurrent);
                        realm.commitTransaction();
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(routeActual);
                        realm.commitTransaction();
                    }
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel",null)
                .show();

    }
}