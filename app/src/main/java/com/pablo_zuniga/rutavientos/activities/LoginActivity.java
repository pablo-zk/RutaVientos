package com.pablo_zuniga.rutavientos.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteCallbackList;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.adapters.LoginAdapter;
import com.pablo_zuniga.rutavientos.fragments.LoginFragment;
import com.pablo_zuniga.rutavientos.fragments.SignupFragment;
import com.pablo_zuniga.rutavientos.models.Route;
import com.pablo_zuniga.rutavientos.models.User;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity implements LoginFragment.DataListener, SignupFragment.DataListener {
    ArrayList<User> users;
    Realm realm;
    RealmResults<User> realUsers;
    RealmList<Integer> listaRutas;

    TabLayout tabLayout;
    ViewPager viewPager;
    LoginAdapter loginAdapter;
    FloatingActionButton google;
    ImageView imgLogo;
    float v=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        google = findViewById(R.id.fab_google);
        imgLogo = findViewById(R.id.imgLogo);
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        loginAdapter = new LoginAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(loginAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        google.setTranslationY(300);
        tabLayout.setTranslationX(800);
        imgLogo.setTranslationY(-300);

        google.setAlpha(v);
        tabLayout.setAlpha(v);
        imgLogo.setAlpha(v);

        google.animate().translationY(0).alpha(1).setDuration(700).setStartDelay(400).start();
        tabLayout.animate().translationX(0).alpha(1).setDuration(500).setStartDelay(300).start();
        imgLogo.animate().translationY(0).alpha(1).setDuration(700).setStartDelay(300).start();

        /*listaRutas = new RealmList<Integer>();

        realm = Realm.getDefaultInstance();

        users = new ArrayList<>();
        users.add(new User("root","1234","","",1,1,0,listaRutas));
        realm.beginTransaction();
        realm.deleteAll();
        realm.copyToRealm(users);
        realm.commitTransaction();*/
    }

    public void sendData(User user) {
        Intent intent = new Intent(this,MainActivity.class); finish();
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