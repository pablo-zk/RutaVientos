package com.pablo_zuniga.rutavientos.fragments;

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
import android.widget.EditText;
import android.widget.ImageButton;

import com.pablo_zuniga.rutavientos.R;

public class CreateRoutesFragment extends Fragment {

    EditText txtOrigen;
    EditText txtDestino;
    ImageButton btnChange;

    public CreateRoutesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_routes, container, false);
        checkGPS();
        txtDestino = (EditText) view.findViewById(R.id.txtDestino);
        txtOrigen = (EditText) view.findViewById(R.id.txtOrigen);
        btnChange = (ImageButton) view.findViewById(R.id.btnChange);

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
}