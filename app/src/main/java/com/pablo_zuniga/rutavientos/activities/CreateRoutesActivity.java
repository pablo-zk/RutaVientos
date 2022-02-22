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
    ImageButton btnChange;
    Button create;
    Realm realm;
    RealmResults<User> realmUser;
    EditText etPlannedDate;
    EditText etPlannedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routes);

        Bundle bundle = getIntent().getExtras();

        txtDestino = (EditText) findViewById(R.id.txtDestino2);
        txtOrigen = (EditText) findViewById(R.id.txtOrigen);
        btnChange = (ImageButton) findViewById(R.id.btnChange);
        create = (Button) findViewById(R.id.btnCreate);
        etPlannedDate = (EditText) findViewById(R.id.etPlannedDate);
        etPlannedTime = (EditText) findViewById(R.id.etPlannedTime);

        txtOrigen.setText(bundle.getString("origen"));
        txtDestino.setText(bundle.getString("destino"));
        txtDestino.setEnabled(false);
        txtOrigen.setEnabled(false);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoDestino = String.valueOf(txtDestino.getText());
                txtDestino.setText(txtOrigen.getText());
                txtOrigen.setText(textoDestino);
            }
        });
        etPlannedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.etPlannedDate:
                        showDatePickerDialog();
                        break;
                }
            }
        });
        etPlannedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.etPlannedTime:
                        showTimePickerDialog();
                        break;
                }
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm = Realm.getDefaultInstance();
                realmUser = realm.where(User.class).equalTo("isActive",true).findAll();

                Route route = new Route(txtOrigen.getText().toString(), txtDestino.getText().toString(), 3, new Date(2022, 2, 11, 10, 30),realmUser.get(0).getId());
                realm.beginTransaction();
                realm.copyToRealm(route);
                realm.commitTransaction();
            }
        });

    }


    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etPlannedDate.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "datePicker");
    }
    private void showTimePickerDialog() {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                final String selectedDate = hourOfDay + ":" + minute;
                etPlannedTime.setText(selectedDate);
            }
        });

        newFragment.show(this.getSupportFragmentManager(), "timePicker");
    }
}