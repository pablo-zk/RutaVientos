package com.pablo_zuniga.rutavientos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pablo_zuniga.rutavientos.R;

public class RouteDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}