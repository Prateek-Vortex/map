package com.example.prateeksaxena.map;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
EditText et;
Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        et=findViewById(R.id.et);
        b=findViewById(R.id.b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geocoder g=new Geocoder(MapsActivity.this);
                try {
                    List<Address> location=g.getFromLocationName(et.getText().toString(),10);
                    Address a=location.get(0);
                    String title=a.getAddressLine(0)+","+a.getAddressLine(1)+","+a.getSubLocality()+","+a.getLocality();
                    LatLng l=new LatLng(a.getLatitude(),a.getLongitude());
                    CameraUpdate cu=CameraUpdateFactory.newLatLng(l);
                    mMap.moveCamera(cu);
                    MarkerOptions m=new MarkerOptions();
                    m.title(title);
                    m.position(l);
                    mMap.addMarker(m);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater m=getMenuInflater();
        m.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.m1)
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if(item.getItemId()==R.id.m2)
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        if(item.getItemId()==R.id.m3)
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        return super.onOptionsItemSelected(item);
    }
}
