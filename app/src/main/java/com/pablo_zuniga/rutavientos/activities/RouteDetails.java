package com.pablo_zuniga.rutavientos.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.fragments.CreateRoutesFragment;
import com.pablo_zuniga.rutavientos.fragments.RoutesFragment;
import com.pablo_zuniga.rutavientos.fragments.UserFragment;
import com.pablo_zuniga.rutavientos.fragments.ViajesFragment;
import com.pablo_zuniga.rutavientos.models.Route;
import com.pablo_zuniga.rutavientos.models.User;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

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

    TextView txtDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        txtContacto = (TextView) findViewById(R.id.txtContact);
        txtContacto.setText("Contacta con " + userOfRoute.getUsername().toString());
        btnApuntarse = (Button) findViewById(R.id.btnApuntarse);
        if (userCurrent.getId() == userOfRoute.getId()){
            btnApuntarse.setText("Eliminar");
            btnApuntarse.setBackground(getDrawable(R.drawable.button_del));
        } else {
            //TODO partir de aquí poner los datos en los txt o lo que haya en la view
            if(userCurrent.getRoutesId().contains(routeActual.getId())){
                btnApuntarse.setText("Desapuntarse");
                txtContacto.setEnabled(true);
            }else{
                btnApuntarse.setText("Apuntarse");
                txtContacto.setEnabled(false);
            }
        }


        btnApuntarse.setOnClickListener(view -> {
            if (btnApuntarse.getText().toString() == "Eliminar"){
                showInfoAlert("¿Seguro que quieres eliminar la ruta?");
            } else {
                String origen = routeActual.getOrigin().contains("Cuatrovientos") ? routeActual.getOrigin() : routeActual.getOrigin().split(",")[0];
                String destino = routeActual.getDestiny().contains("Cuatrovientos") ? routeActual.getDestiny() : routeActual.getDestiny().split(",")[0];
                if(userCurrent.getRoutesId().contains(routeActual.getId())){
                    showInfoAlert("¿Seguro que quieres desapuntarte de la ruta " + origen + "-" + destino + " a las " + routeActual.getDateHour().getHours() + ":" + routeActual.getDateHour().getMinutes() + "?");
                }else{
                    if(routeActual.getFreeSeats() == 0){
                        Toast.makeText(getBaseContext(),"ERROR: No quedan plazas disponibles", Toast.LENGTH_SHORT).show();
                    }else{
                        showInfoAlert("¿Seguro que quieres apuntarte de la ruta " + origen + "-" + destino + " a las " + routeActual.getDateHour().getHours() + ":" + routeActual.getDateHour().getMinutes() + "?");
                    }
                }
            }


        });

        txtContacto.setOnClickListener(view -> {
            if (txtContacto.isClickable()){
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+String.valueOf(userOfRoute.getTelefono())));
                startActivity(intent);
            }
        });

        //TODO Mostrar datos

        this.txtName = (TextView) findViewById(R.id.txtName);
        txtName.setText(userOfRoute.getUsername().toString());

        this.txtOrigen = (TextView) findViewById(R.id.idOrigen);
        this.txtOrigenC = (TextView) findViewById(R.id.idOrigenC);
        this.txtDestino = (TextView) findViewById(R.id.idDestino);
        this.txtDestinoC = (TextView) findViewById(R.id.idDestinoC);

        if (routeActual.getOrigin().contains("Cuatrovientos")){
            String[] destino = routeActual.getDestiny().split(",");
            this.txtDestinoC.setText(destino[0].toString() + " " + destino[1].toString());
            this.txtDestino.setText(destino[2].toString());
            this.txtOrigenC.setText(routeActual.getOrigin().toString());
            this.txtOrigen.setText(routeActual.getOrigin().toString());
        } else {
            String[] origen = routeActual.getOrigin().split(",");
            this.txtOrigenC.setText(origen[0].toString() + " " + origen[1].toString());
            this.txtOrigen.setText(origen[2].toString());
            this.txtDestinoC.setText(routeActual.getDestiny().toString());
            this.txtDestino.setText(routeActual.getDestiny().toString());
        }

        this.txtDate = (TextView) findViewById(R.id.txtDate);
        String date = getDay(routeActual.getDateHour().getDay()) + " " + String.valueOf(routeActual.getDateHour().getDay()) + " " + getMonth(routeActual.getDateHour().getMonth());
        String time = String.valueOf(routeActual.getDateHour().getHours()) + ":" + String.valueOf(routeActual.getDateHour().getMinutes());
        this.txtDate.setText(date+ " / " + time);

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


    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getMonth(int mes){
        Locale locale = new Locale("es", "ES");
        Month mMonth=Month.of(mes);
        String monthName=mMonth.getDisplayName(TextStyle.FULL,locale);
        return monthName;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getDay(int day){

        if (day == 0){
            day = 6;
        } else if (day == 1){
            day = 7;
        } else {
            day -= 1;
        }

        Locale locale = new Locale("es", "ES");
        DayOfWeek dDay = DayOfWeek.of(day);
        String dayName=dDay.getDisplayName(TextStyle.FULL,locale);
        return dayName;
    }
}