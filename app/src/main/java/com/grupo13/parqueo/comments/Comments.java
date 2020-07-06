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
import android.widget.EditText;
import android.widget.Toast;

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
import com.grupo13.parqueo.ControlBD;
import com.grupo13.parqueo.ControlWS;
import com.grupo13.parqueo.R;
import com.grupo13.parqueo.modelo.Comentario;
import com.grupo13.parqueo.modelo.Ubicacion;
import com.grupo13.parqueo.utilidades.GPSTracker;
import com.grupo13.parqueo.utilidades.ReconocimientoVoz;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Comments extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText texto;
    //vars
    private ArrayList<String> nNames;
    private ArrayList<String> nImagesUrls;
    private ArrayList<String> nComments;
    private GoogleSignInClient mGoogleSignInClient;
    String name;
    String email;
    String image = "";
    ControlBD helper = ControlBD.getInstance(this);

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
        initData();
        initGallery();
    }

    public void initData(){
        nNames = new ArrayList<>();
        nComments = new ArrayList<>();
        nImagesUrls = new ArrayList<>();

        //for add the images
        int ubication = ubicacion.id_ubicacion;
        List<Comentario> comments = helper.comentarioDao().obtenerComentariosPorUbicacion(ubication);
        for(Comentario comentarioActual: comments){
            nComments.add(comentarioActual.texto);
            nNames.add(comentarioActual.usuario);
            nImagesUrls.add("https://pm1.narvii.com/7093/84196c693eba950461690eb36ad46bf1e7cb1ae1r1-332-363v2_uhq.jpg");
        }

        //calling the recyclerview
        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        texto = (EditText) findViewById(R.id.comment);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, nNames, nImagesUrls, nComments);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initGallery(){
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
        String comentario = texto.getText().toString();
        String ubication = String.valueOf(ubicacion.id_ubicacion);

        if (comentario.equals(null) || comentario.equals("")) {
            String mensaje = "Error al insertar comentario: campo vacío.";
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
        } else {
            ControlWS.subirComentario(getApplicationContext(), ubication, email, comentario, Comments.this);
        }
    }

}
