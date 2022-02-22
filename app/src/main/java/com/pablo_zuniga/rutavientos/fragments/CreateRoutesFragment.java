package com.pablo_zuniga.rutavientos.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
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
import com.pablo_zuniga.rutavientos.activities.CreateRoutesActivity;
import com.pablo_zuniga.rutavientos.activities.MapsActivity;
import com.pablo_zuniga.rutavientos.dialog.DatePickerFragment;
import com.pablo_zuniga.rutavientos.dialog.TimePickerFragment;
import com.pablo_zuniga.rutavientos.models.Route;
import com.pablo_zuniga.rutavientos.models.User;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class CreateRoutesFragment extends Fragment {

    CardView cardDestination4v;
    CardView cardDestinationOther;
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

        create = (Button) view.findViewById(R.id.btnCreate);
        cardDestination4v = (CardView) view.findViewById(R.id.cardDestination4v);
        cardDestinationOther = (CardView) view.findViewById(R.id.cardDestinationOther);

        cardDestination4v.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), MapsActivity.class);
            intent.putExtra("destino","Cuatrovientos");
            intent.putExtra("origen","");
            startActivity(intent);
        });
        cardDestinationOther.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), MapsActivity.class);
            intent.putExtra("destino","");
            intent.putExtra("origen","Cuatrovientos");
            startActivity(intent);
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
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel",null)
                .show();
    }
}