package com.grupo13.parqueo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grupo13.parqueo.modelo.Ubicacion;
import com.grupo13.parqueo.utilidades.GPSTracker;
import com.grupo13.parqueo.utilidades.PermisoService;
import com.grupo13.parqueo.utilidades.ReconocimientoVoz;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, SearchView.OnQueryTextListener {

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
    SupportMapFragment mapFragment;
    ControlBD helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermisoService.verificarPermiso(this);
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

        Snackbar.make(mapFragment.getView(), getString(R.string.saludo) + " " + account.getDisplayName(), Snackbar.LENGTH_SHORT).show();

        locationManager = (LocationManager)
                this.getSystemService(Context.LOCATION_SERVICE);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Aqui hacemos la magia del swipe refresh xd
                ControlWS.traerDatos(getApplicationContext());
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //Esto activa el gps
        mMap.setMyLocationEnabled(true);
        GPSTracker tracker = new GPSTracker(MainActivity.this);
        LatLng ll = new LatLng(tracker.getLatitude(), tracker.getLongitude());

        if (ll.longitude == 0 && ll.longitude == 0) {
            ll = new LatLng(13.6929403, -89.2181911);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 9));
        }else
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 17));

        Log.d("LOCALIZACION", ll.toString());

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
        cargarUbicaciones();
        //Esta parte es para mover los botones xxd
        @SuppressLint("ResourceType") View zoomControls = mapFragment.getView().findViewById(0x1);

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

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_desplegable, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.busqueda);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

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
    public void cargarUbicaciones(){
        List<Ubicacion> ubicaciones = helper.ubicacionDao().obtenerUbicaciones();
        for(Ubicacion pivote: ubicaciones){
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(pivote.latitud,pivote.longitud))
                    .title(pivote.nombre_ubicacion));
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, query,Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
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
        }
    }
}