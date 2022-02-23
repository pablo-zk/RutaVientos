package com.pablo_zuniga.rutavientos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.dialog.DatePickerFragment;
import com.pablo_zuniga.rutavientos.dialog.TimePickerFragment;
import com.pablo_zuniga.rutavientos.models.Route;
import com.pablo_zuniga.rutavientos.models.User;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class CreateRoutesActivity extends AppCompatActivity {

    EditText txtOrigen;
    EditText txtDestino;
    EditText txtPlazas;
    ImageButton btnChange;
    Button create;
    Realm realm;
    RealmResults<User> realmUser;
    EditText etPlannedDate;
    EditText etPlannedTime;
    Date fechaUtilizada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routes);

        fechaUtilizada = new Date(0,0,0,0,0,0);
        Bundle bundle = getIntent().getExtras();

        txtDestino = (EditText) findViewById(R.id.txtDestino2);
        txtOrigen = (EditText) findViewById(R.id.txtOrigen);
        txtPlazas = (EditText) findViewById(R.id.txtPlazas);
        btnChange = (ImageButton) findViewById(R.id.btnChange);
        create = (Button) findViewById(R.id.btnCreate);
        etPlannedDate = (EditText) findViewById(R.id.etPlannedDate);
        etPlannedTime = (EditText) findViewById(R.id.etPlannedTime);

        txtOrigen.setText(bundle.getString("origen"));
        txtDestino.setText(bundle.getString("destino"));
        txtDestino.setEnabled(false);
        txtOrigen.setEnabled(false);

        btnChange.setOnClickListener(view -> {
            String textoDestino = String.valueOf(txtDestino.getText());
            txtDestino.setText(txtOrigen.getText());
            txtOrigen.setText(textoDestino);
        });
        etPlannedDate.setOnClickListener(view -> {
            if (view.getId() == R.id.etPlannedDate) {
                showDatePickerDialog();
            }
        });
        etPlannedTime.setOnClickListener(view -> {
            if (view.getId() == R.id.etPlannedTime) {
                showTimePickerDialog();
            }
        });
        create.setOnClickListener(v -> {
            int plazasLibres = txtPlazas.getText().toString().equals("")? -1: Integer.parseInt(txtPlazas.getText().toString());
            if(txtOrigen.getText().toString().equals("") || txtDestino.getText().toString().equals("") || fechaUtilizada.getYear() == 0 || txtPlazas.getText().toString().equals("")){
                Toast.makeText(CreateRoutesActivity.this,"Rellena todos los campos.",Toast.LENGTH_SHORT).show();
                return;
            }
            if(plazasLibres < 1 ){
                txtPlazas.setError("Valor incorrecto");
                return;
            }
            realm = Realm.getDefaultInstance();
            realmUser = realm.where(User.class).equalTo("isActive",true).findAll();

            Route route = new Route(txtOrigen.getText().toString(), txtDestino.getText().toString(), plazasLibres, fechaUtilizada,realmUser.get(0).getId());
            realm.beginTransaction();
            realm.copyToRealm(route);
            realm.commitTransaction();
            Intent intent = new Intent(CreateRoutesActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }


    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            // +1 because January is zero
            final String selectedDate = day + " / " + (month+1) + " / " + year;
            etPlannedDate.setText(selectedDate);
            fechaUtilizada.setDate(day);
            fechaUtilizada.setMonth(month+1);
            fechaUtilizada.setYear(year);
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }
    private void showTimePickerDialog() {
        TimePickerFragment newFragment = TimePickerFragment.newInstance((view, hourOfDay, minute) -> {
            final String selectedDate = hourOfDay + ":" + minute;
            etPlannedTime.setText(selectedDate);
            fechaUtilizada.setHours(hourOfDay);
            fechaUtilizada.setMinutes(minute);
        });

        newFragment.show(this.getSupportFragmentManager(), "timePicker");
    }
}