package com.pablo_zuniga.rutavientos.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
}