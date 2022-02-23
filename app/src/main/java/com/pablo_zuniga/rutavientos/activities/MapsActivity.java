package com.pablo_zuniga.rutavientos.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pablo_zuniga.rutavientos.R;
import com.pablo_zuniga.rutavientos.databinding.ActivityMapsBinding;
import com.pablo_zuniga.rutavientos.fragments.CreateRoutesFragment;
import com.pablo_zuniga.rutavientos.models.Route;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CancellationException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private MarkerOptions marker;
    private Boolean markerCreated;
    private Geocoder geoCoder;
    Button btnGuardar;
    String addressText;
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        addressText = "";

        /*txtTitle = (TextView) findViewById(R.id.txtTitleMap);*/
        Bundle bundle = getIntent().getExtras();
        /*if(bundle.getString("destino").equals("")){
            txtTitle.setText("Elige el destino");
        }else{
            txtTitle.setText("Elige el origen");
        }*/

        btnGuardar = (Button) findViewById(R.id.btnGuardarUbi);
        btnGuardar.setOnClickListener(view -> {
            Intent intent = new Intent(MapsActivity.this, CreateRoutesActivity.class);
            if(addressText.equals("")){
                Toast.makeText(MapsActivity.this,"Clica sobre el mapa y elige una ubicación", Toast.LENGTH_SHORT).show();
                return;
            }
            if(bundle.getString("destino").equals("")){
                intent.putExtra("destino",addressText);
                intent.putExtra("origen",bundle.getString("origen"));
            }else{
                intent.putExtra("destino",bundle.getString("destino"));
                intent.putExtra("origen",addressText);
            }
            startActivity(intent);
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        markerCreated = false;
        geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
        mMap = googleMap;
        mMap.setMinZoomPreference(12);

        //DEFAULT VIEW
        LatLng cuatrovientos = new LatLng(42.8242834,-1.659874);
        mMap.addMarker(new MarkerOptions().position(cuatrovientos).title("Cuatrovientos"));
        CameraPosition camera = new CameraPosition.Builder().target(cuatrovientos).zoom(18).tilt(15).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(camera));
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MapsActivity.this, R.raw.style_json));

        //EVENTS
        mMap.setOnMapClickListener(latLng -> {
            if(markerCreated){
                showAlert();
                return;
            }
            markerCreated = true;

            List<Address> matches = null;
            try {
                matches = geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(matches == null){
                return;
            }
            Address address = matches.get(0);
            addressText = String.format("%s", address.getAddressLine(0));
            Toast.makeText(MapsActivity.this,addressText, Toast.LENGTH_SHORT).show();

            marker = new MarkerOptions();
            marker.position(latLng);
            marker.title(addressText.split(",")[0]);
            marker.draggable(true);
            mMap.addMarker(marker);

            //Mostrar la linea
//                Polyline line = mMap.addPolyline(new PolylineOptions()
//                        .add(new LatLng(latLng.latitude,latLng.longitude), new LatLng(42.8242834, -1.659874))
//                        .width(5)
//                        .color(Color.RED));
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                marker.hideInfoWindow();
            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker2) {
                marker2.showInfoWindow();
                Toast.makeText(MapsActivity.this,"Marker Dragged to \n"+
                        "Lat: "+marker2.getPosition().latitude+"\n"+
                        "Lng: "+marker2.getPosition().longitude, Toast.LENGTH_SHORT).show();
                List<Address> matches = null;
                try {
                    matches = geoCoder.getFromLocation(marker2.getPosition().latitude, marker2.getPosition().longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(matches == null){
                    return;
                }
                Address address = matches.get(0);
                addressText = String.format("%s", address.getAddressLine(0));
                marker2.setTitle(addressText.split(",")[0]);
                marker2.hideInfoWindow();
            }
        });
    }

    private void showAlert(){
        new AlertDialog.Builder(this)
                .setTitle("ALERTA")
                .setMessage("Ya has seleccionado una ubicación. Si quieres cambiar de ubicacíon mantenga pulsado el marcador y muévalo a donde quiera.")
                .setPositiveButton("OK",null)
                .show();
    }
}