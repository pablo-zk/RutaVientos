package com.pablo_zuniga.rutavientos.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.adapters.MyPagerAdapter;
import com.pablo_zuniga.rutavientos.fragments.LoginFragment;
import com.pablo_zuniga.rutavientos.fragments.RoutesFragment;
import com.pablo_zuniga.rutavientos.models.Route;
import com.pablo_zuniga.rutavientos.models.User;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;


public class MainActivity extends AppCompatActivity implements RoutesFragment.DataListener {
    ArrayList<Route> routes;
    Realm realm;

    TabLayout tabLayout;
    ViewPager viewPager;
    MyPagerAdapter pagerAdapter;

    User userGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = new Intent(this, LoginActivity.class); startActivity(intent); finish();
        realm = Realm.getDefaultInstance();

        routes = new ArrayList<>();
        routes.add(new Route("Cuatrovientos", "La morea", 3, new Date(2022, 2, 11, 10, 30),"Pablo" ));
        routes.add(new Route("Estella", "Cuatrovientos", 2, new Date(2022, 2, 17, 6, 30),"Asier" ));
        routes.add(new Route("Cuatrovientos", "Itaroa", 4, new Date(2022, 2, 11, 19, 30),"Gorka" ));
        //realm.deleteAll();
        realm.beginTransaction();
        realm.copyToRealm(routes);
        realm.commitTransaction();

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("ROUTES"));
        tabLayout.addTab(tabLayout.newTab().setText("CREATE"));
        tabLayout.addTab(tabLayout.newTab().setText("USER"));

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    public void sendData(Route route) {
        Intent intent = new Intent(this, RouteDetails.class);
        intent.putExtra("origin", route.getOrigin());
        intent.putExtra("destiny", route.getDestiny());

        startActivity(intent);

        //Intent intent = new Intent(this,MainActivity.class); finish();//Intent a DetailRouteActivity
//        intent.putExtra("username",user.getUsername());
//        intent.putExtra("passwd",user.getPassword());
//        intent.putExtra("nombre",user.getNombre());
//        intent.putExtra("apellido",user.getApellido());
//        intent.putExtra("telefono",user.getTelefono());
//        intent.putExtra("fotoPerfil",user.getFotoPerfil());
//        intent.putExtra("puntuacion",user.getPuntuacion());
//        intent.putExtra("routesId",user.getFotoPerfil());
        //startActivity(intent);
    }

}