package com.grupo13.parqueo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    Ubicacion selected;
    ControlBD helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_detalle);
        ButterKnife.bind(this);
        selected = (Ubicacion) getIntent().getExtras().get("UBICACION");
        txtNombre.setText(selected.nombre_ubicacion);
        helper = ControlBD.getInstance(this);
        String puntuacion = String.format("Puntuacion: %.1f",helper.calificacionDao().obtenerPromedioPorUbicacion(selected.id_ubicacion));
        txtPunt.setText(puntuacion);
        getSupportActionBar().setTitle(selected.nombre_ubicacion);
        Imagen imagen = helper.imagenDao().consultarImagenPorUbicacion(selected.id_ubicacion);
        //Glide.with(this).load(ControlWS.ASSETS_URL + imagen.filename).into(imgUbicacion);
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

}