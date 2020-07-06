package com.grupo13.parqueo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grupo13.parqueo.comments.Comments;
import com.grupo13.parqueo.modelo.Ubicacion;
import com.grupo13.parqueo.utilidades.GPSTracker;
import com.grupo13.parqueo.utilidades.PermisoService;
import com.grupo13.parqueo.utilidades.ReconocimientoVoz;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, SearchView.OnQueryTextListener, Serializable {

    private TextToSpeech textToSpeech;
    private GoogleMap mMap;
    private MenuItem searchMenuItem;
    private SearchView searchView;
    RequestQueue requestQueue;
    private GoogleSignInClient mGoogleSignInClient;
    Location localizacion;
    LocationManager locationManager;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    MenuItem item;
    SupportMapFragment mapFragment;
    ControlBD helper;
    UbicacionListaFragment current;
    @BindView(R.id.fragmento)
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ButterKnife.bind(this);
        helper = ControlBD.getInstance(this);
        // Usuario actualmente logueado
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        String name = account.getDisplayName();
        String email = account.getEmail();

        Snackbar.make(mapFragment.getView(), getString(R.string.saludo) + " " + name, Snackbar.LENGTH_SHORT).show();
        locationManager = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Aqui hacemos la magia del swipe refresh xd
                ControlWS.traerDatos(getApplicationContext(), MainActivity.this);
                cargarUbicaciones();
                swipeRefresh.setRefreshing(false);
            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int ttsLang = textToSpeech.setLanguage(Locale.getDefault());
                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                            || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "The Language is not supported!");
                    } else {
                        Log.i("TTS", "Language Supported.");
                    }
                    Log.i("TTS", "Initialization success.");
                }
                else {
                    Log.e("TTS", "Initialization fail.");
                }
            }
        });

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //Esto activa el gps
        mMap.setMyLocationEnabled(true);
        gpsTrack();

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
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
        cargarUbicaciones();
        //Esta parte es para mover los botones xxd
        /*@SuppressLint("ResourceType") View zoomControls = mapFragment.getView().findViewById(0x1);

        if (zoomControls != null && zoomControls.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            // ZoomControl is inside of RelativeLayout
            RelativeLayout.LayoutParams params_zoom = (RelativeLayout.LayoutParams) zoomControls.getLayoutParams();

            // Align it to - parent top|left
            params_zoom.addRule(RelativeLayout.ALIGN_PARENT_END, 0);
            params_zoom.addRule(RelativeLayout.ALIGN_END, 0);
            params_zoom.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params_zoom.setMargins(40, 0, 20, 40);

            // Update margins, set to 10dp
            final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30,
                    getResources().getDisplayMetrics());
            params_zoom.setMargins(margin, margin, margin, margin);

        }*/

    }
    public void localizar(View v){
        gpsTrack();
    }
    public void gpsTrack(){
        GPSTracker tracker = new GPSTracker(MainActivity.this);
        LatLng ll = new LatLng(tracker.getLatitude(), tracker.getLongitude());

        if (ll.longitude == 0 && ll.longitude == 0) {
            ll = new LatLng(13.6929403, -89.2181911);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 9));
        }else
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 17));

        Log.d("LOCALIZACION", ll.toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_desplegable, menu);
        item = menu.findItem(R.id.action_search);
        searchView = (SearchView) item.getActionView();
        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                FragmentTransaction txn = getSupportFragmentManager().beginTransaction();
                ArrayList<Ubicacion> ubicaciones = (ArrayList<Ubicacion>) helper.ubicacionDao().obtenerUbicaciones();
                current = UbicacionListaFragment.newInstance(ubicaciones, MainActivity.this);
                txn.replace(R.id.fragmento, current);
                txn.commit();
                frameLayout.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                FragmentTransaction txn;
                txn = getSupportFragmentManager().beginTransaction();
                txn.remove(current);
                txn.commit();
                frameLayout.setVisibility(View.INVISIBLE);
                return true;
            }
        });
        searchView.setOnQueryTextListener(this);
        return true;
    }
    //TODO: Cambiar por cambiar fragment
    @Override
    public boolean onQueryTextSubmit(String query) {
        //Toast.makeText(this, query,Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<Ubicacion> elementosQuery = new ArrayList<>();
        ArrayList<Ubicacion> ubicaciones = (ArrayList<Ubicacion>) helper.ubicacionDao().obtenerUbicaciones();
        for(Ubicacion pivote: ubicaciones)
            if(pivote.nombre_ubicacion.contains(newText))
                elementosQuery.add(pivote);

        FragmentTransaction txn;
        txn = getSupportFragmentManager().beginTransaction();
        current = UbicacionListaFragment.newInstance(elementosQuery, MainActivity.this);
        txn.replace(R.id.fragmento, current);
        txn.commit();

        return false;
    }

    public void seleccionarUbi(Ubicacion ubi){
        item.collapseActionView();
        Toast.makeText(this,"Se ha seleccionado: "+ubi.nombre_ubicacion,Toast.LENGTH_SHORT).show();
        LatLng ll = new LatLng(ubi.latitud, ubi.longitud);

        if (ll.longitude == 0 && ll.longitude == 0) {
            ll = new LatLng(13.6929403, -89.2181911);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ll, 9));
        }else
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ll, 17));

        Log.d("LOCALIZACION", ll.toString());

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
        Ubicacion ubicacion = (Ubicacion) marker.getTag();
        Intent intent = new Intent(this, UbicacionDetalleActivity.class);
        intent.putExtra("UBICACION", ubicacion);
        startActivity(intent);
        return true;
    }
    public void cargarUbicaciones(){
        mMap.clear();
        List<Ubicacion> ubicaciones = helper.ubicacionDao().obtenerUbicaciones();
        for(Ubicacion pivote: ubicaciones){
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_parking))
                    .position(new LatLng(pivote.latitud,pivote.longitud))
                    .title(pivote.nombre_ubicacion));
            marker.setTag(pivote);
            //
            Log.v("Agregado: ",pivote.nombre_ubicacion);
        }
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
    // Permitir escuchar por el microfono
    private static  final int RECOGNIZE_SPEACH_CODE = 100;

    public void getVoice(View view){

        Intent recognizeSpeach = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizeSpeach.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizeSpeach.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        recognizeSpeach.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.di_algo));
        try {
            startActivityForResult(recognizeSpeach, RECOGNIZE_SPEACH_CODE);
        } catch (Exception e){
            Snackbar.make(view, getString(R.string.listening_error), Snackbar.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RECOGNIZE_SPEACH_CODE:
                if (resultCode == RESULT_OK && data != null){
                    List<String> palabras = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    ReconocimientoVoz voz = new ReconocimientoVoz(palabras.get(0).toLowerCase());
                    String respuesta = null;

                    if (voz.esComandoValido()){
                        GPSTracker tracker = new GPSTracker(getApplicationContext());
                        if (tracker.canGetLocation()){

                            LatLng miUbicacion = new LatLng(tracker.getLatitude(), tracker.getLongitude());
                            List<Ubicacion> ubicaciones = helper.ubicacionDao().obtenerUbicaciones();

                            Ubicacion masCercana = voz.getRespuesta(miUbicacion, ubicaciones);

                            if(masCercana != null){
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( new LatLng(masCercana.latitud, masCercana.longitud), 17));
                                respuesta = getString(R.string.distancia) + voz.getDistanciaMetros(miUbicacion, masCercana) + getString(R.string.metros);
                            } else{
                                respuesta = getString(R.string.problema);
                            }
                        }else
                            respuesta = getString(R.string.ubicacion_apagada);
                    }else {
                        respuesta = getString(R.string.comando_invalido);
                    }

                    int speechStatus = textToSpeech.speak(respuesta, TextToSpeech.QUEUE_FLUSH, null);

                    if (speechStatus == TextToSpeech.ERROR) {
                        Log.e("TTS", "Error in converting Text to Speech!");
                    }
            }
                break;
        }
    }
}