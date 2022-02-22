package com.pablo_zuniga.rutavientos.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.models.User;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SignupFragment extends Fragment {

    private DataListener callback;
    User user;
    private EditText username;
    private EditText passwd;
    private EditText name;
    private EditText lasname;
    private EditText phone;
    RealmList<Integer> listaRutas;
    float v=0;
    private Button btnLog;
    int numeroTelefono;

    private Realm realm;

    public SignupFragment() {}
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

        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        this.username = (EditText) view.findViewById(R.id.username);
        this.passwd = (EditText) view.findViewById(R.id.passwd);
        this.name = (EditText) view.findViewById(R.id.name);
        this.lasname = (EditText) view.findViewById(R.id.lastname);
        this.phone = (EditText) view.findViewById(R.id.phone);
        this.btnLog = (Button) view.findViewById(R.id.btnLog);

        username.setTranslationX(800);
        passwd.setTranslationX(800);

        username.setAlpha(v);
        passwd.setAlpha(v);

        btnLog.setOnClickListener(view1 -> {
            if (name.getTranslationX() == 0){
                if(name.getText().toString().equals("")){
                    name.setError("Name is required");
                    return;
                }else if(lasname.getText().toString().equals("")){
                    lasname.setError("Last name is required");
                    return;
                }else if(phone.getText().toString().equals("") || phone.getText().toString().length() != 9){
                    phone.setError("Phone is required");
                    return;
                }
                try {
                    numeroTelefono = Integer.parseInt(phone.getText().toString().trim());
                } catch (NumberFormatException e){
                    phone.setError("Incorrect phone number");
                    return;
                }
                name.animate().translationX(-1800).alpha(0).setDuration(900).setStartDelay(200).start();
                lasname.animate().translationX(-1800).alpha(0).setDuration(900).setStartDelay(250).start();
                phone.animate().translationX(-1800).alpha(0).setDuration(900).setStartDelay(300).start();

                username.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(350).start();
                passwd.animate().translationX(0).alpha(1).setDuration(700).setStartDelay(400).start();
                //Añadido
                btnLog.setText("SIGNUP");
            } else {
                if(username.getText().toString().equals("")){
                    username.setError("Username is required");
                    return;
                }else if(passwd.getText().toString().equals("")){
                    passwd.setError("Password is required");
                    return;
                }

                realm = Realm.getDefaultInstance();
                RealmResults<User> users = realm.where(User.class).equalTo("username",username.getText().toString()).findAll();
                if(users.size() != 0){
                    username.setError("Username already exists");
                    return;
                }


                listaRutas = new RealmList<Integer>();
                realm = Realm.getDefaultInstance();
                user = new User(username.getText().toString(), passwd.getText().toString(), name.getText().toString(), lasname.getText().toString(),numeroTelefono,1,1,listaRutas, true);
                realm.beginTransaction();
                realm.copyToRealm(user);
                realm.commitTransaction();
                callback.sendData(user);
            }

        });

        return view;
    }
    public interface DataListener {
        public void sendData(User User);
    }
}