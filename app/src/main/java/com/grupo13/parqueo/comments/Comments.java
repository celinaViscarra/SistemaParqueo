package com.grupo13.parqueo.comments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.grupo13.parqueo.ControlWS;
import com.grupo13.parqueo.R;
import com.grupo13.parqueo.modelo.Ubicacion;
import com.grupo13.parqueo.utilidades.GPSTracker;
import com.grupo13.parqueo.utilidades.ReconocimientoVoz;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Comments extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //vars
    private ArrayList<String> nNames = new ArrayList<>();
    private ArrayList<String> nImagesUrls = new ArrayList<>();
    private ArrayList<String> nComments = new ArrayList<>();
    private GoogleSignInClient mGoogleSignInClient;
    String name;
    String email;
    String image = "";

    Ubicacion ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_tool);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        name = account.getDisplayName();
        email = account.getEmail();

        ubicacion = (Ubicacion)getIntent().getExtras().getSerializable("UBICACION");

        initImageBitmas();
    }

    private void initImageBitmas(){
        //for add the images

        nComments.add("FEO");
        nImagesUrls.add("https://pm1.narvii.com/7093/84196c693eba950461690eb36ad46bf1e7cb1ae1r1-332-363v2_uhq.jpg");
        nNames.add("LUCIA ZAMORANO");

        nComments.add("HERMOSO");
        nImagesUrls.add("https://st-listas.20minutos.es/images/2015-03/394837/4670373_640px.jpg");
        nNames.add("MARIA DE LOS ANGELES");

        nComments.add("HORRIBLE");
        nImagesUrls.add("https://pm1.narvii.com/6354/1ab557c7209ee79663a32dc26cd888f9e67e6fa9_hq.jpg");
        nNames.add("CASIMIRA DEL SOL");

        nComments.add("PRECIOSOOOOOOOO");
        nImagesUrls.add("https://pm1.narvii.com/6354/5019d49dc977fbadaf59164597b590014ee882b0_hq.jpg");
        nNames.add("CATALINA VEGANA");

        nComments.add("NO, JAMAS REGRESARE");
        nImagesUrls.add("https://cellularnews.com/wp-content/uploads/2020/04/spongebob-and-patrick-yellow-hearts-325x485.jpg");
        nNames.add("PATRICIO ESTRELLA");

        nComments.add("BIEN");
        nImagesUrls.add("https://www.thqnordic.com/sites/default/files/games/gallery/SpongeBob_BfBB_00.jpg");
        nNames.add("TORONJA NARANJA");
        //calling the recyclerview
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, nNames, nImagesUrls, nComments);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView galeria = findViewById(R.id.galeria);
        RecyclerAdapterGallery adapterGaleria = new RecyclerAdapterGallery(this, ubicacion);
        galeria.setAdapter(adapterGaleria);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        galeria.setLayoutManager(manager);
    }

    private static final int REQUEST_IMAGE_CAPTURE = 200;
    public void photo(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK && data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    ControlWS.subirFoto(getApplicationContext(), encoded, String.valueOf(ubicacion.id_ubicacion));
                }
                break;
        }
    }

    public void agregarComentario(View v){

    }

}
