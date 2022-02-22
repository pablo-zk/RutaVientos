package com.pablo_zuniga.rutavientos.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.activities.MapsActivity;
import com.pablo_zuniga.rutavientos.dialog.DatePickerFragment;
import com.pablo_zuniga.rutavientos.dialog.TimePickerFragment;
import com.pablo_zuniga.rutavientos.models.Route;
import com.pablo_zuniga.rutavientos.models.User;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class CreateRoutesFragment extends Fragment {

    EditText txtOrigen;
    EditText txtDestino;
    ImageButton btnChange;
    Button create;
    Realm realm;
    RealmResults<User> realmUser;
    EditText etPlannedDate;
    EditText etPlannedTime;
    public CreateRoutesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_routes, container, false);
        checkGPS();
        txtDestino = (EditText) view.findViewById(R.id.txtDestino2);
        txtOrigen = (EditText) view.findViewById(R.id.txtOrigen);
        btnChange = (ImageButton) view.findViewById(R.id.btnChange);
        create = (Button) view.findViewById(R.id.btnCreate);
        etPlannedDate = (EditText) view.findViewById(R.id.etPlannedDate);
        etPlannedTime = (EditText) view.findViewById(R.id.etPlannedTime);

        txtOrigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

        //txtDestino.setInputType(InputType.TYPE_NULL);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtDestino.isEnabled()){
                    txtDestino.setEnabled(false);
                    txtOrigen.setEnabled(true);
                }else{
                    txtDestino.setEnabled(true);
                    txtOrigen.setEnabled(false);
                }
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


        return view;
    }

    private void checkGPS(){
        try {
            int gpsSignal = Settings.Secure.getInt(getContext().getContentResolver(),Settings.Secure.LOCATION_MODE);
            if (gpsSignal==0){
                showInfoAlert();
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();

        }
    }

    private void showInfoAlert(){
        new AlertDialog.Builder(getContext())
                .setTitle("GPS Alert")
                .setMessage("You don´t have the GPS signal enabled, Would you like to enable the GPS signal now?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel",null)
                .show();
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

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
    private void showTimePickerDialog() {
        TimePickerFragment newFragment = TimePickerFragment.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                final String selectedDate = hourOfDay + ":" + minute;
                etPlannedTime.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }
}