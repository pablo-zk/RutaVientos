package com.pablo_zuniga.rutavientos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteCallbackList;
import android.util.Log;

import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.fragments.LoginFragment;
import com.pablo_zuniga.rutavientos.models.Route;
import com.pablo_zuniga.rutavientos.models.User;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity implements LoginFragment.DataListener {
    ArrayList<User> users;
    Realm realm;
    RealmResults<User> realUsers;
    RealmList<Integer> listaRutas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        listaRutas = new RealmList<Integer>();

        realm = Realm.getDefaultInstance();

        users = new ArrayList<>();
        users.add(new User("root","1234","","",1,1,0,listaRutas));
        realm.beginTransaction();
        realm.deleteAll();
        realm.copyToRealm(users);
        realm.commitTransaction();
        realUsers = realm.where(User.class).findAll();
    }

    public void sendData(User user) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("username",user.getUsername());
        intent.putExtra("passwd",user.getPassword());
        intent.putExtra("nombre",user.getNombre());
        intent.putExtra("apellido",user.getApellido());
        intent.putExtra("telefono",user.getTelefono());
        intent.putExtra("fotoPerfil",user.getFotoPerfil());
        intent.putExtra("puntuacion",user.getPuntuacion());
        intent.putExtra("routesId",user.getFotoPerfil());
        startActivity(intent);
    }

}