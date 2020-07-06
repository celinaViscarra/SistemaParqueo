package com.grupo13.parqueo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.grupo13.parqueo.comments.Comments;
import com.grupo13.parqueo.modelo.Ubicacion;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UbicacionDetalleActivity extends AppCompatActivity {
    @BindView(R.id.txtNombre)
    TextView txtNombre;
    @BindView(R.id.txtLat)
    TextView txtLat;
    @BindView(R.id.txtLong)
    TextView txtLong;
    Ubicacion selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_detalle);
        ButterKnife.bind(this);
        selected = (Ubicacion) getIntent().getExtras().get("UBICACION");
        txtNombre.setText("Nombre: "+selected.nombre_ubicacion);
        txtLat.setText(String.format("Latitud: %.2f",selected.latitud));
        txtLong.setText(String.format("Longitud: %.2f",selected.longitud));
    }

    public void mostrar(View v){
        Intent intent = null;
        switch (v.getId()){
            case R.id.btnComentarios:{
                intent = new Intent(this, Comments.class);
                break;
            }
            case R.id.btnParqueos:{
                //TODO: Mostrar parqueos disponibles
                break;
            }
        }
        if(intent != null){
            intent.putExtra("UBICACION", selected);
            startActivity(intent);
        }
    }

}