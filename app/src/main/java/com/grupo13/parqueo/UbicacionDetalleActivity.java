package com.grupo13.parqueo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.grupo13.parqueo.comments.Comments;
import com.grupo13.parqueo.modelo.Imagen;
import com.grupo13.parqueo.modelo.Ubicacion;
import com.grupo13.parqueo.parqueos.ParqueoListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UbicacionDetalleActivity extends AppCompatActivity {
    @BindView(R.id.txtNombre)
    TextView txtNombre;
    @BindView(R.id.txtPunt)
    TextView txtPunt;
    @BindView(R.id.imgUbicacion)
    ImageView imgUbicacion;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.btnSubmitRating)
    Button btnSubmitRating;
    Ubicacion selected;
    ControlBD helper;
    boolean isIndicator = true;
    private GoogleSignInClient mGoogleSignInClient;
    String name;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_detalle);
        ButterKnife.bind(this);
        selected = (Ubicacion) getIntent().getExtras().get("UBICACION");
        txtNombre.setText(selected.nombre_ubicacion);
        helper = ControlBD.getInstance(this);
        getSupportActionBar().setTitle(selected.nombre_ubicacion);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        name = account.getDisplayName();
        email = account.getEmail();
        obtenerPuntuacion();

        Imagen imagen = helper.imagenDao().consultarImagenPorUbicacion(selected.id_ubicacion);
        if(imagen!=null)
            Glide.with(this).load(ControlWS.ASSETS_URL + imagen.filename).error(R.mipmap.ic_parking).into(imgUbicacion);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
    }

    public void mostrar(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.btnComentarios:{
                intent = new Intent(this, Comments.class);
                break;
            }
            case R.id.btnParqueos:{
                intent = new Intent(this, ParqueoListActivity.class);
                break;
            }
        }
        if(intent != null){
            intent.putExtra("UBICACION", selected);
            startActivity(intent);
        }
    }

    public void obtenerPuntuacion(){
        String puntuacion = String.format("Puntuacion: %.1f",helper.calificacionDao().obtenerPromedioPorUbicacion(selected.id_ubicacion));
        txtPunt.setText(puntuacion);
        ratingBar.setRating(helper.calificacionDao().obtenerPromedioPorUbicacion(selected.id_ubicacion));
        ratingBar.setIsIndicator(isIndicator);
    }
    public void puntuar(View v){
        if(isIndicator){
            isIndicator = false;
            ratingBar.setIsIndicator(false);
            Toast.makeText(this, "Puede editar la puntuacion ahora.",Toast.LENGTH_SHORT).show();
            btnSubmitRating.setText("Guardar cambios");
        }else{
            isIndicator = true;
            ControlWS.subirPuntuacion(this, String.valueOf(selected.id_ubicacion),String.valueOf(ratingBar.getRating()),email,UbicacionDetalleActivity.this);
            ratingBar.setIsIndicator(true);
            btnSubmitRating.setText("Puntuar ahora");
        }
    }

}