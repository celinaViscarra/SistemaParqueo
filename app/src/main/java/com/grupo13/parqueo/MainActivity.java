package com.grupo13.parqueo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.grupo13.parqueo.utilidades.GPSTracker;
import com.grupo13.parqueo.utilidades.PermisoService;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private GoogleSignInClient mGoogleSignInClient;
    Location localizacion;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermisoService.verificarPermiso(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*// Usuario actualmente logueado
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        Snackbar.make(mapFragment.getView(), getString(R.string.saludo) + " " + account.getDisplayName(), Snackbar.LENGTH_SHORT).show();
        */
        locationManager = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(13.6929403, -89.2181911);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in San Salvador"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //Esto activa el gps
        mMap.setMyLocationEnabled(true);
        GPSTracker tracker = new GPSTracker(MainActivity.this);
        //Log.v("coordenadas",String.format("Latitud %.2f Longitud %.2f",tracker.getLatitude(),tracker.getLongitude()));
        LatLng ll = new LatLng(tracker.getLatitude(), tracker.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 17));
        mMap.setOnMarkerClickListener(this);

        //Dejo esto por si queremos cambiar automaticamente la localizacion.
        /*mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                CameraUpdate center=CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(11);
                mMap.moveCamera(center);
                mMap.animateCamera(zoom);

            }
        });*/
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_desplegable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idItem = item.getItemId();

        switch (idItem) {
            case R.id.logout:
                cerrarSesion();
                break;
        }
        return true;
    }
    @Override
    public boolean onMarkerClick(final Marker marker) {
        Toast.makeText(this,marker.getTitle(),Toast.LENGTH_SHORT).show();
        return true;
    }

    public void cerrarSesion() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });
    }

}