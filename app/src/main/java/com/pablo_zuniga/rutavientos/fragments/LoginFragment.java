package com.pablo_zuniga.rutavientos.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.models.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginFragment extends Fragment {

    private DataListener callback;
    private EditText user;
    private EditText passwd;
    private Button btnLog;
    private TextView prueba;
    private Realm realm;
    RealmResults<User> realUsers;
    public LoginFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            callback = (DataListener) context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + "should implement DataListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        this.user = (EditText) view.findViewById(R.id.etUser);
        this.passwd = (EditText) view.findViewById(R.id.etPasswd);
        this.btnLog = (Button) view.findViewById(R.id.btnLogin);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm = Realm.getDefaultInstance();
                realUsers = realm.where(User.class).equalTo("username", user.getText().toString()).equalTo("password", passwd.getText().toString()).findAll();
                if (realUsers.size() != 0){
                    callback.sendData(realUsers.get(0));
                }else{
                    user.setError("Usuario incorrecto");
                }
            }
        });


        return view;
    }

    public interface DataListener {
        public void sendData(User User);
    }

}